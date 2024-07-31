package com.yogigupta1206.m2ppokemon.domain.model

data class PokemonApiResponse(
    val data: List<PokemonCardRawData>?,
    val page: Int,
    val pageSize: Int,
    val count: Int,
    val totalCount: Int
)

data class PokemonCardRawData(
    val id: String,
    val images: ImageUrls,
    val name: String,
    val types: List<String>,
    val subtypes: List<String>,
    val level: Int?,
    val hp: Int,
    val attacks: List<Attack>,
    val weaknesses: List<Weakness>,
    val resistances: List<Resistance>?,
    val abilities: List<Ability>?
)