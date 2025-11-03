package com.rz.democoncurrency.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ConcurrencyScreenRoot(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: ConcurrencyViewModel = viewModel(),
) {
    val logs by viewModel.logs.collectAsStateWithLifecycle()


    ConcurrencyScreen(
        modifier = modifier,
        logs =  logs,
        onNoConcurrencyClick = {
            viewModel.onNoConcurrencyClick()
        },
        onWithConcurrencyClick = {
            viewModel.onWithConcurrencyClick()
        },
        onConcurrencyWithCancellationClick = {
            viewModel.onConcurrencyWithCancellationClick()
        },
        onConcurrencyWithExceptionClick = {
            viewModel.onConcurrencyWithExceptionClick()
        },
        onConcurrencyWithAsync = {
            viewModel.onConcurrencyWithAsync()
        },
    )
}

@Composable
private fun ConcurrencyScreen(
    modifier: Modifier = Modifier,
    onNoConcurrencyClick: () -> Unit,
    onWithConcurrencyClick: () -> Unit,
    onConcurrencyWithCancellationClick: () -> Unit,
    onConcurrencyWithExceptionClick: () -> Unit,
    onConcurrencyWithAsync: () -> Unit,
    logs: List<String> = listOf("asd", "dfg")
) {
    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = onNoConcurrencyClick) {
            Text("No concurrency")
        }
        Button(onClick = onWithConcurrencyClick) {
            Text(text = "With concurrency")
        }
        Button(onClick = onConcurrencyWithCancellationClick) {
            Text(text = "Concurrency with cancellation")
        }
        Button(onClick = onConcurrencyWithExceptionClick) {
            Text(text = "Concurrency with exception")
        }
        Button(onClick = onConcurrencyWithAsync) {
            Text(text = "Concurrency with async")
        }

        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.inversePrimary,
                    shape = RoundedCornerShape(8.dp),
                )
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                logs.forEach {
                    Text(it)
                }
            }
        }
    }
}