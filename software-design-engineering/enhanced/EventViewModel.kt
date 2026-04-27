package com.example.digitalplannertime

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalplannertime.data.Repository
import com.example.digitalplannertime.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel separates UI from business logic and data handling
class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = Repository(application)

    // Holds the current list of events for the UI
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    // Load all events from database
    fun loadEvents() {
        viewModelScope.launch {
            _events.value = repo.getAllEvents()
        }
    }

    // Add a new event
    fun addEvent(event: Event) {
        viewModelScope.launch {
            if (repo.insertEvent(event)) {
                loadEvents()
            }
        }
    }

    // Update an existing event
    fun updateEvent(event: Event) {
        viewModelScope.launch {
            if (repo.updateEvent(event)) {
                loadEvents()
            }
        }
    }

    // Delete an event
    fun deleteEvent(eventId: Long) {
        viewModelScope.launch {
            repo.deleteEvent(eventId)
            loadEvents()
        }
    }
}