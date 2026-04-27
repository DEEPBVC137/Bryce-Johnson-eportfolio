package com.example.digitalplannertime.alerts

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.content.ContextCompat

// BroadcastReceiver runs when the alarm fires and sends the text if allowed
class SmsAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: return
        val phone = intent.getStringExtra("phone") ?: return
        sendNow(context, phone, "Upcoming event ${'$'}title")
    }

    companion object {
        // Shared utility to send a text immediately
        fun sendNow(ctx: Context, phone: String, message: String) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ctx, "SMS permission missing", Toast.LENGTH_SHORT).show()
                return
            }
            try {
                SmsManager.getDefault().sendTextMessage(phone, null, message, null, null)
                Toast.makeText(ctx, "SMS sent", Toast.LENGTH_SHORT).show()
            } catch (_: Exception) {
                Toast.makeText(ctx, "SMS failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}