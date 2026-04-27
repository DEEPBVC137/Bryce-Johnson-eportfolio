package com.example.digitalplannertime.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.digitalplannertime.model.Event
import java.security.MessageDigest

// SQLiteOpenHelper owns the schema for users and events and exposes basic CRUD
class DatabaseHelper(ctx: Context) : SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    // Create both tables on first run
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $T_USERS (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE NOT NULL, password_hash TEXT NOT NULL)"
        )
        db.execSQL(
            "CREATE TABLE $T_EVENTS (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, event_date INTEGER NOT NULL, notes TEXT, phone TEXT)"
        )
    }

    // Simple upgrade strategy for coursework. Drop and recreate tables.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $T_EVENTS")
        db.execSQL("DROP TABLE IF EXISTS $T_USERS")
        onCreate(db)
    }

    // Insert a new user, storing only a hash of the password
    fun createUser(username: String, passwordPlain: String): Boolean {
        if (username.isBlank() || passwordPlain.isBlank()) return false
        val cv = ContentValues().apply {
            put("username", username.trim())
            put("password_hash", sha256(passwordPlain))
        }
        return writableDatabase.insert(T_USERS, null, cv) != -1L
    }

    // Compare the supplied password hash to the stored one
    fun validateUser(username: String, passwordPlain: String): Boolean {
        if (username.isBlank() || passwordPlain.isBlank()) return false
        val c = readableDatabase.query(
            T_USERS, arrayOf("password_hash"), "username=?", arrayOf(username.trim()), null, null, null
        )
        val ok = c.moveToFirst() && c.getString(0) == sha256(passwordPlain)
        c.close()
        return ok
    }

    // Add a row to the events table and return its id
    fun insertEvent(e: Event): Long {
        val cv = ContentValues().apply {
            put("title", e.title)
            put("event_date", e.eventDateMillis)
            put("notes", e.notes)
            put("phone", e.phone)
        }
        return writableDatabase.insert(T_EVENTS, null, cv)
    }

    // Update an existing row based on id
    fun updateEvent(e: Event): Boolean {
        val cv = ContentValues().apply {
            put("title", e.title)
            put("event_date", e.eventDateMillis)
            put("notes", e.notes)
            put("phone", e.phone)
        }
        val rows = writableDatabase.update(T_EVENTS, cv, "id=?", arrayOf(e.id.toString()))
        return rows > 0
    }

    // Remove a row by id
    fun deleteEvent(id: Long) {
        writableDatabase.delete(T_EVENTS, "id=?", arrayOf(id.toString()))
    }

    // Read all events sorted by time
    fun getAllEvents(): List<Event> {
        val out = mutableListOf<Event>()
        val c: Cursor = readableDatabase.query(T_EVENTS, null, null, null, null, null, "event_date ASC")
        while (c.moveToNext()) {
            out.add(
                Event(
                    id = c.getLong(c.getColumnIndexOrThrow("id")),
                    title = c.getString(c.getColumnIndexOrThrow("title")),
                    eventDateMillis = c.getLong(c.getColumnIndexOrThrow("event_date")),
                    notes = c.getString(c.getColumnIndexOrThrow("notes")),
                    phone = c.getString(c.getColumnIndexOrThrow("phone"))
                )
            )
        }
        c.close()
        return out
    }

    // Basic hashing for coursework. This is not a full auth system.
    private fun sha256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
    }

    companion object {
        // Database constants
        const val DB_NAME = "digital_planner.db"
        const val DB_VERSION = 1
        const val T_USERS = "users"
        const val T_EVENTS = "events"
    }
}