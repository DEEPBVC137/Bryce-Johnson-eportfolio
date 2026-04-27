package com.example.digitalplannertime.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.widget.Toast
import com.example.digitalplannertime.alerts.SmsAlarmReceiver
import com.example.digitalplannertime.model.Event
import kotlin.math.max

// Repository is a thin layer over DatabaseHelper plus a few utilities
class Repository(private val ctx: Context) {
    private val db = DatabaseHelper(ctx)

    // Users
    fun createUser(username: String, password: String) = db.createUser(username, password)
    fun validateUser(username: String, password: String) = db.validateUser(username, password)

    // Events
    fun getAllEvents(): List<Event> = db.getAllEvents()

    fun insertEvent(e: Event): Boolean {
        val id = db.insertEvent(e)
        return if (id != -1L) { e.id = id; true } else false
    }

    fun updateEvent(e: Event): Boolean = db.updateEvent(e)


    fun deleteEvent(id: Long) { db.deleteEvent(id) }


    // Format a timestamp for display on cards
    fun formatMillis(ms: Long): String = DateFormat.format("yyyy-MM-dd HH:mm", ms).toString()

    // Schedule a one time alarm to send SMS five minutes before the event time
    fun scheduleSmsIfPermitted(e: Event) {
        if (e.phone.isNullOrBlank()) return

        val am = ctx.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val trigger = max(System.currentTimeMillis(), e.eventDateMillis - 5 * 60 * 1000)

        val i = Intent(ctx, SmsAlarmReceiver::class.java).apply {
            putExtra("title", e.title)
            putExtra("phone", e.phone)
        }
        val pi = PendingIntent.getBroadcast(
            ctx,
            e.id.hashCode(), // stable request code
            i,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            val canExact =
                android.os.Build.VERSION.SDK_INT < 31 || am.canScheduleExactAlarms()
            if (canExact) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pi)
            } else {
                // fallback if exact alarms are not allowed
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pi)
            }
        } catch (_: Throwable) {
            // final safety net
            am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pi)
        }
    }


    // Convenience for the test button. Picks the first event with a phone.
    fun sendTestSmsIfPossible() {
        val first = getAllEvents().firstOrNull { !it.phone.isNullOrBlank() }
        if (first == null) {
            Toast.makeText(ctx, "No event with phone", Toast.LENGTH_SHORT).show()
            return
        }
        SmsAlarmReceiver.sendNow(ctx, first.phone!!, "Event reminder for ${'$'}{first.title}")
    }
}