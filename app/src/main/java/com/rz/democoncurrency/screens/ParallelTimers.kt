package com.rz.democoncurrency.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ParallelTimersScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: ParallelTimersViewModel = viewModel()
) {
    val timers = viewModel.timers.collectAsState()
    Column(modifier = modifier.padding(16.dp)) {
        Button(onClick = { viewModel.addNewTimer() }) {
            Text("Add Timer")
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(items = timers.value) {
                SingleTimer(
                    text = formatMillisToTime(it.currentValue),
                    onDelete = { viewModel.deleteTimer(it.timeStart) })
            }
        }
    }
}

@Composable
fun SingleTimer(
    text: String,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text, modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            )

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Timer",
                )
            }
        }
    }
}
