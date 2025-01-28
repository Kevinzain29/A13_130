package com.example.soala13pam.serviceAPI

import com.example.soala13pam.model.Event
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Headers

interface EventService {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("bacaevent.php")
    suspend fun getEvent(): List<Event>

    @GET("baca1event.php/{id_event}")
    suspend fun getEventById(@Query("id_event") idEvent: String): Event

    @POST("insertevent.php")
    suspend fun insertEvent(@Body event: Event)

    @PUT("editevent.php/{id_event}")
    suspend fun updateEvent(@Query("id_event") idEvent: String, @Body event: Event)

    @DELETE("deleteevent.php/{id_event}")
    suspend fun deleteEvent(@Query("id_event") idEvent: String): Response<Void>
}