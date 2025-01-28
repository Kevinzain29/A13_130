package com.example.soala13pam.repository

import com.example.soala13pam.model.Event
import com.example.soala13pam.serviceAPI.EventService
import java.io.IOException

interface EventRepository {
    suspend fun getEvent(): List<Event>
    suspend fun insertEvent(event: Event)
    suspend fun updateEvent(idEvent: String, event: Event)
    suspend fun deleteEvent(idEvent: String)
    suspend fun getEventById(idEvent: String): Event
}

class NetworkEventRepository(
    private val eventAPIService: EventService
) : EventRepository {
    override suspend fun insertEvent(event: Event) {
        eventAPIService.insertEvent(event)
    }

    override suspend fun updateEvent(idEvent: String, event: Event) {
        eventAPIService.updateEvent(idEvent, event)
    }

    override suspend fun deleteEvent(idEvent: String) {
        try {
            val response = eventAPIService.deleteEvent(idEvent)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete event. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getEvent(): List<Event> = eventAPIService.getEvent()
    override suspend fun getEventById(idEvent: String): Event {
        return eventAPIService.getEventById(idEvent)
    }
}