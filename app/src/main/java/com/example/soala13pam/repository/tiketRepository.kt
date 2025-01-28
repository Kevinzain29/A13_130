package com.example.soala13pam.repository

import com.example.soala13pam.model.Tiket
import com.example.soala13pam.serviceAPI.TiketService
import java.io.IOException

interface TiketRepository {
    suspend fun getTiket(): List<Tiket>
    suspend fun insertTiket(tiket: Tiket)
    suspend fun updateTiket(idTiket: String, tiket: Tiket)
    suspend fun deleteTiket(idTiket: String)
    suspend fun getTiketById(idTiket: String): Tiket
}

class NetworkTiketRepository(
    private val tiketAPIService: TiketService
) : TiketRepository {
    override suspend fun insertTiket(tiket: Tiket) {
        tiketAPIService.insertTiket(tiket)
    }

    override suspend fun updateTiket(idTiket: String, tiket: Tiket) {
        tiketAPIService.updateTiket(idTiket, tiket)
    }

    override suspend fun deleteTiket(idTiket: String) {
        try {
            val response = tiketAPIService.deleteTiket(idTiket)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete tiket. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTiket(): List<Tiket> = tiketAPIService.getTiket()
    override suspend fun getTiketById(idTiket: String): Tiket {
        return tiketAPIService.getTiketById(idTiket)
    }
}