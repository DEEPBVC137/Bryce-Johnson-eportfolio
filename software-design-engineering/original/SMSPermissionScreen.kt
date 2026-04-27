package com.example.digitalplannertime

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import com.example.digitalplannertime.data.Repository
import com.example.digitalplannertime.model.Event
import java.util.Calendar
import androidx.compose.ui.unit.sp

// Shows the event grid and the add or edit dialog
@Composable
fun EventDashboardScreen(
    onAskSmsPermission: () -> Unit
) {
    val ctx = LocalContext.current
    val repo = remember { Repository(ctx) }

    // Screen state
    var showEditor by rememberSaveable { mutableStateOf(false) } // stays false on load
    var editing by remember { mutableStateOf<Event?>(null) }
    var saving by remember { mutableStateOf(false) }

    // Data
    val events = remember { mutableStateListOf<Event>() }
    fun refresh() {
        events.clear()
        events.addAll(repo.getAllEvents())
    }
    LaunchedEffect(Unit) { refresh() }

    // SMS permission state
    var smsGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(ctx, Manifest.permission.SEND_SMS) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }
    val requestSms = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        smsGranted = granted
        Toast.makeText(
            ctx,
            if (granted) "SMS permission granted" else "SMS permission denied",
            Toast.LENGTH_SHORT
        ).show()
    }

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = { editing = null; showEditor = true }) { Text("Add event",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp))
            }

            OutlinedButton(onClick = {
                if (smsGranted) {
                    repo.sendTestSmsIfPossible()
                } else {
                    requestSms.launch(Manifest.permission.SEND_SMS)
                }
            }) { Text("Send test SMS",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp))
            }

            OutlinedButton(onClick = onAskSmsPermission) { Text("Permission help",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp))
            }
        }

        if (events.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No events yet. Tap Add event.")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(events, key = { it.id }) { e ->
                    ElevatedCard(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(
                                e.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text("When ${repo.formatMillis(e.eventDateMillis)}")
                            e.notes?.takeIf { it.isNotBlank() }?.let {
                                Spacer(Modifier.height(4.dp))
                                Text(it, maxLines = 3)
                            }
                            Spacer(Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(onClick = { editing = e; showEditor = true }) {
                                    Text("Edit")
                                }
                                TextButton(onClick = { repo.deleteEvent(e.id); refresh() }) {
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
            onDismiss = { showEditor = false; editing = null },
            onSave = { saved ->
                if (saving) return@EventEditorDialog
                saving = true

                val success = if (editing == null) {
                    repo.insertEvent(saved)
                } else {
                    repo.updateEvent(saved)
                }

                if (success) {
                    try { repo.scheduleSmsIfPermitted(saved) } catch (_: Throwable) {}
                    editing = null
                    showEditor = false
                    refresh()
                } else {
                    Toast.makeText(ctx, "Save failed", Toast.LENGTH_SHORT).show()
                }

                saving = false
            }
        )
    }
}

// Dialog to add or edit an event with date and time pickers
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
    var phone by remember { mutableStateOf(initial?.phone ?: "") }
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
        title = { Text(if (initial == null) "Add event" else "Edit event") },
        text = {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    singleLine = true
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val preview = android.text.format.DateFormat
                        .format("yyyy-MM-dd HH:mm", selectedMillis)
                        .toString()
                    Text("When $preview")
                    OutlinedButton(onClick = { pickDateTime() }) { Text("Pick date and time") }
                }
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone for SMS") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") }
                )
            }
        },
        confirmButton = {
            Button(
                enabled = !isBusy,
                onClick = {
                    if (title.isBlank()) {
                        Toast.makeText(ctx, "Title required", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val e = (initial ?: Event()).copy(
                        title = title.trim(),
                        eventDateMillis = selectedMillis,
                        phone = phone.trim().ifBlank { null },
                        notes = notes.trim().ifBlank { null }
                    )
                    onSave(e) // parent saves and closes
                }
            ) { Text(if (initial == null) "Save" else "Update") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
