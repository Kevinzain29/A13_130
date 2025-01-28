package com.example.soala13pam.repository

import com.example.soala13pam.model.Peserta
import com.example.soala13pam.serviceAPI.PesertaService
import java.io.IOException

interface PesertaRepository {
    suspend fun getPeserta(): List<Peserta>
    suspend fun insertPeserta(peserta: Peserta)
    suspend fun updatePeserta(idPeserta: String, peserta: Peserta)
    suspend fun deletePeserta(idPeserta: String)
    suspend fun getPesertaById(idPeserta: String): Peserta
}

class NetworkPesertaRepository(
    private val pesertaAPIService: PesertaService
) : PesertaRepository {
    override suspend fun insertPeserta(peserta: Peserta) {
        pesertaAPIService.insertPeserta(peserta)
    }

    override suspend fun updatePeserta(idPeserta: String, peserta: Peserta) {
        pesertaAPIService.updatePeserta(idPeserta, peserta)
    }

    override suspend fun deletePeserta(idPeserta: String) {
        try {
            val response = pesertaAPIService.deletePeserta(idPeserta)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete peserta. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPeserta(): List<Peserta> = pesertaAPIService.getPeserta()
    override suspend fun getPesertaById(idPeserta: String): Peserta {
        return pesertaAPIService.getPesertaById(idPeserta)
    }
}