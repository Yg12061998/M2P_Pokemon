package com.yogigupta1206.m2ppokemon.domain.model

data class PokemonCardPreview(
    val id: String,
    val images: ImageUrls,
    val name: String,
    val types: List<String>? = emptyList(),
    val level: Int?,
    val hp: Int
)