package com.rz.democoncurrency.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class ConcurrencyViewModel : ViewModel() {

    private val _logs = MutableStateFlow<List<String>>(emptyList())
    val logs = _logs.asStateFlow()


    init {
        viewModelScope.launch {
            Factory.logs.collect { log ->
                _logs.update { currentLogs ->
                    currentLogs + log
                }
            }
        }
    }

    fun onNoConcurrencyClick() {
        viewModelScope.launch {
            _logs.update { emptyList() }
            Factory.orderBricks()
            Factory.orderDoors()
            Factory.orderWindows()
        }
    }

    fun onWithConcurrencyClick() {
        _logs.update { emptyList() }
        viewModelScope.launch {
            viewModelScope.launch {
                Factory.orderBricks()
            }
            viewModelScope.launch {
                Factory.orderDoors()
            }
            viewModelScope.launch {
                Factory.orderWindows()
            }
        }
    }

    fun onConcurrencyWithCancellationClick() {
        _logs.update { emptyList() }
        viewModelScope.launch {
            viewModelScope.launch {
                Factory.orderBricks()
            }
            val doorsJob = viewModelScope.launch {
                Factory.orderDoors()
            }
            viewModelScope.launch {
                Factory.orderWindows()
            }
            delay(200)
            _logs.update { it + "We already have doors - cancel this job" }
            doorsJob.cancel("We already have doors - cancel this job")
        }
    }

    fun onConcurrencyWithExceptionClick() {
        _logs.update { emptyList() }

        val orderException = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("AAAAAAA", "Crash --> ${throwable.message ?: "message is null"}")
        }

        viewModelScope.launch {
            supervisorScope {
                launch(orderException) {
                    Factory.orderBricks()
                }
                launch(orderException) {
                    Factory.orderDoors(true)
                }
                launch(orderException) {
                    Factory.orderWindows()
                }
            }
        }
    }

    fun onConcurrencyWithAsync() {
        _logs.update { emptyList() }
        viewModelScope.launch {
            supervisorScope {
                val brickOK = async {
                    Factory.orderBricks()
                }
                val doorsOK = async {
                    Factory.orderDoors(Random.nextBoolean())
                }
                val windowsOK = async {
                    Factory.orderWindows()
                }
                val results = listOf(
                    runCatching {
                        brickOK.await()
                    },
                    runCatching {
                        doorsOK.await()
                    }.onFailure { e ->
                        _logs.update { it + "Doors failed: ${e.message}" }
                    },
                    runCatching { windowsOK.await() },
                )
                if (results.all { it.isSuccess })
                    _logs.update { it + "House is ready!" }
                else
                    _logs.update { it + "One of the operations failed!" }
            }
        }
    }

    fun onUseMutex(useMutex:Boolean) {
        _logs.update { emptyList() }
        var counter = 0
        val mutex = Mutex()
        viewModelScope.launch(Dispatchers.Default) {
            val jobs = List(100) {
                launch {
                    repeat(100) {
                        if(useMutex) {
                            mutex.withLock {
                                counter++
                            }
                        } else {
                            counter++
                        }
                    }
                }
            }

            jobs.forEach { it.join() }
            _logs.update { it + "Counter: $counter" }
        }
    }
}


class Factory() {
    companion object {
        private val _logs = MutableSharedFlow<String>(replay = 3, extraBufferCapacity = 3)
        val logs = _logs.asSharedFlow()

        suspend fun orderBricks() {
            _logs.emit("Bricks ordered")
            delay(1500)
            _logs.emit("Bricks arrived")
            delay(100)
        }

        suspend fun orderDoors(throwException: Boolean = false) {
            _logs.emit("Doors ordered")
            if (throwException) {
                delay(200)
                throw Exception("No more doors")
            }

            delay(1000)
            _logs.emit("Doors arrived")
            delay(100)
        }

        suspend fun orderWindows() {
            _logs.emit("Windows ordered")
            delay(700)
            _logs.emit("Windows arrived")
            delay(100)
        }
    }
}
