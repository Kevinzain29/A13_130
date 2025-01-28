package com.example.soala13pam.container

import com.example.soala13pam.repository.EventRepository
import com.example.soala13pam.repository.NetworkEventRepository
import com.example.soala13pam.repository.NetworkPesertaRepository
import com.example.soala13pam.repository.NetworkTiketRepository
import com.example.soala13pam.repository.NetworkTransaksiRepository
import com.example.soala13pam.repository.PesertaRepository
import com.example.soala13pam.repository.TiketRepository
import com.example.soala13pam.repository.TransaksiRepository
import com.example.soala13pam.serviceAPI.EventService
import com.example.soala13pam.serviceAPI.PesertaService
import com.example.soala13pam.serviceAPI.TiketService
import com.example.soala13pam.serviceAPI.TransaksiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pesertaRepository: PesertaRepository
    val eventRepository: EventRepository
    val tiketRepository: TiketRepository
    val transaksiRepository: TransaksiRepository
}

class Container : AppContainer {
    private val baseUrl = "http://10.0.2.2/SoalA13PAM/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json.to".toMediaType()))
        .baseUrl(baseUrl).build()

    private val pesertaService: PesertaService by lazy { retrofit.create(PesertaService::class.java) }
    override val pesertaRepository: PesertaRepository by lazy { NetworkPesertaRepository(pesertaService) }

    private val eventService: EventService by lazy { retrofit.create(EventService::class.java) }
    override val eventRepository: EventRepository by lazy { NetworkEventRepository(eventService) }

    private val tiketService: TiketService by lazy { retrofit.create(TiketService::class.java) }
    override val tiketRepository: TiketRepository by lazy { NetworkTiketRepository(tiketService) }

    private val transaksiService: TransaksiService by lazy { retrofit.create(TransaksiService::class.java) }
    override val transaksiRepository: TransaksiRepository by lazy { NetworkTransaksiRepository(transaksiService) }
}