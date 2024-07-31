package com.yogigupta1206.m2ppokemon.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_cards")
data class PokemonCardEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "images")
    val imageUrl: ImageUrls,

    val name: String,
    val types: List<String>?,
    val subtypes: List<String>?,

    val level: Int? = null,
    val hp: Int,

    @ColumnInfo(name = "attacks")
    val attacks: List<Attack>?,

    @ColumnInfo(name = "weaknesses")
    val weaknesses: List<Weakness>?,

    @ColumnInfo(name = "resistances")
    val resistances: List<Resistance>?,

    @ColumnInfo(name = "abilities")
    val abilities: List<Ability>?
)

data class Attack(
    val name: String,
    val cost: List<String>,
    val damage: String,
    val text: String
)

data class Weakness(
    val type: String,
    val value: String
)

data class Resistance(
    val type: String,
    val value: String
)

data class Ability(
    val name: String,
    val text: String
)

data class ImageUrls(
    val small: String,
    val large: String
)
