package com.yogigupta1206.m2ppokemon.domain.model

import androidx.room.ColumnInfo

data class PokemonCardPreview(
    val id: String,
    @ColumnInfo("images") val imageUrls: ImageUrls,
    val name: String,
    val types: List<String>? = emptyList(),
    val level: Int?,
    val hp: Int
)