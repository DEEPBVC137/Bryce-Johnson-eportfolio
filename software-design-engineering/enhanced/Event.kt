package com.example.digitalplannertime.model

// Simple data model for rows in the events table

data class Event(
    var id: Long = 0L,
    var title: String = "",
    var eventDateMillis: Long = 0L,
    var notes: String? = null
)