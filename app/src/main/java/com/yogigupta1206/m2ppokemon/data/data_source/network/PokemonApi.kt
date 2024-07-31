package com.yogigupta1206.m2ppokemon.data.data_source.network

import com.yogigupta1206.m2ppokemon.domain.model.PokemonApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface PokemonApi {

    @GET("v2/cards?pageSize=20")
    suspend fun getPokemonList(): Response<PokemonApiResponse>
}