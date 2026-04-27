package com.example.digitalplannertime.data

import android.content.Context
import android.text.format.DateFormat
import com.example.digitalplannertime.model.Event

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
        return if (id != -1L) {
            e.id = id
            true
        } else {
            false
        }
    }

    fun updateEvent(e: Event): Boolean = db.updateEvent(e)

    fun deleteEvent(id: Long) {
        db.deleteEvent(id)
    }

    // Format a timestamp for display on cards
    fun formatMillis(ms: Long): String {
        return DateFormat.format("yyyy-MM-dd HH:mm", ms).toString()
    }
}