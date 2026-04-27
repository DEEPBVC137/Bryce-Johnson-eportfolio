package com.example.digitalplannertime

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalplannertime.model.Event
import java.util.Calendar

@Composable
fun EventDashboardScreen() {

    val ctx = LocalContext.current
    val viewModel: EventViewModel = viewModel()

    var showEditor by rememberSaveable { mutableStateOf(false) }
    var editing by remember { mutableStateOf<Event?>(null) }
    var saving by remember { mutableStateOf(false) }
    var pendingDelete by remember { mutableStateOf<Event?>(null) }

    val events by viewModel.events.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadEvents()
    }

    Column(Modifier.fillMaxSize()) {

        Text(
            text = "Your Events",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    editing = null
                    showEditor = true
                }
            ) {
                Text("Add New Event")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (events.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No events yet. Tap 'Add New Event' to begin.")
            }
        } else {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(events, key = { it.id }) { e ->

                    ElevatedCard {
                        Column(Modifier.padding(12.dp)) {

                            Text(
                                e.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(
                                text = android.text.format.DateFormat
                                    .format("yyyy-MM-dd HH:mm", e.eventDateMillis)
                                    .toString(),
                                style = MaterialTheme.typography.bodySmall
                            )

                            e.notes?.takeIf { it.isNotBlank() }?.let {
                                Spacer(Modifier.height(6.dp))
                                Text(it, maxLines = 3)
                            }

                            Spacer(Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                TextButton(onClick = {
                                    editing = e
                                    showEditor = true
                                }) {
                                    Text("Edit")
                                }

                                TextButton(onClick = {
                                    pendingDelete = e
                                }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showEditor) {
        EventEditorDialog(
            initial = editing,
            isBusy = saving,
            onDismiss = {
                showEditor = false
                editing = null
            },
            onSave = { saved ->

                if (saving) return@EventEditorDialog
                saving = true

                if (editing == null) {
                    viewModel.addEvent(saved)
                } else {
                    viewModel.updateEvent(saved)
                }

                editing = null
                showEditor = false
                saving = false
            }
        )
    }

    if (pendingDelete != null) {
        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            title = { Text("Delete Event") },
            text = {
                Text("Are you sure you want to delete \"${pendingDelete?.title}\"?")
            },
            confirmButton = {
                Button(onClick = {
                    pendingDelete?.let { event ->
                        viewModel.deleteEvent(event.id)
                    }
                    pendingDelete = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun EventEditorDialog(
    initial: Event?,
    isBusy: Boolean,
    onDismiss: () -> Unit,
    onSave: (Event) -> Unit
) {
    val ctx = LocalContext.current

    var title by remember { mutableStateOf(initial?.title ?: "") }
    var selectedMillis by remember {
        mutableStateOf(initial?.eventDateMillis ?: (System.currentTimeMillis() + 60 * 60 * 1000L))
    }
    var notes by remember { mutableStateOf(initial?.notes ?: "") }

    fun pickDateTime() {
        val cal = Calendar.getInstance().apply { timeInMillis = selectedMillis }

        DatePickerDialog(
            ctx,
            { _, y, m, d ->
                TimePickerDialog(
                    ctx,
                    { _, h, min ->
                        val newCal = Calendar.getInstance().apply {
                            set(Calendar.YEAR, y)
                            set(Calendar.MONTH, m)
                            set(Calendar.DAY_OF_MONTH, d)
                            set(Calendar.HOUR_OF_DAY, h)
                            set(Calendar.MINUTE, min)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        selectedMillis = newCal.timeInMillis
                    },
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
                ).show()
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial == null) "Add Event" else "Edit Event") },
        text = {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Event Title") },
                    singleLine = true
                )

                val preview = android.text.format.DateFormat
                    .format("yyyy-MM-dd HH:mm", selectedMillis)
                    .toString()

                Text("Scheduled: $preview")

                OutlinedButton(onClick = { pickDateTime() }) {
                    Text("Select Date and Time")
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (Optional)") }
                )
            }
        },

        confirmButton = {
            Button(
                enabled = !isBusy,
                onClick = {

                    if (title.trim().isBlank()) {
                        Toast.makeText(ctx, "Event title is required", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (selectedMillis < System.currentTimeMillis()) {
                        Toast.makeText(ctx, "Event must be in the future", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val e = (initial ?: Event()).copy(
                        title = title.trim(),
                        eventDateMillis = selectedMillis,
                        notes = notes.trim().ifBlank { null }
                    )

                    onSave(e)
                }
            ) {
                Text(if (initial == null) "Save Event" else "Update Event")
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}