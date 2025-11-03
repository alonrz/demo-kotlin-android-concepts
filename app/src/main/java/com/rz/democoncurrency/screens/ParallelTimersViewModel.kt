package com.rz.democoncurrency.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ParallelTimersViewModel : ViewModel() {
    private val _timers = MutableStateFlow<List<TimerState>>(emptyList())
    val timers = _timers.asStateFlow()
    val coroutineScope = CoroutineScope(context = SupervisorJob() + viewModelScope.coroutineContext)

    init {
        _timers.onEach {
            while (it.isNotEmpty()) {
                delay(50)
                _timers.update { currentList ->
                    currentList.map { timerState ->
                        timerState.copy(currentValue = (System.currentTimeMillis() - timerState.timeStart))
                    }
                }
            }
        }.launchIn(coroutineScope)
    }

    override fun onCleared() {
        super.onCleared()
        // Clean up any resources here
    }

    fun addNewTimer() {
        _timers.update { currentList ->
            currentList + TimerState()
        }
    }

    fun deleteTimer(timeStart: Long) {
        _timers.update { currentList ->
            currentList.filter { it.timeStart != timeStart }
        }
    }
}

data class TimerState(
    val isRunning: Boolean,
    val timeStart: Long, // this will be used as ID
    val currentValue: Long,
) {
    constructor() : this(true, System.currentTimeMillis(), 0L)
}

fun formatMillisToTime(millis: Long): String {
    val totalSeconds = millis / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    val millis = millis % 1000

    return if (hours > 0) {
        String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis)
    } else {
        String.format("%02d:%02d.%03d", minutes, seconds, millis)
    }
}
