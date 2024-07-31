package com.yogigupta1206.m2ppokemon.presentation.pokemon_info.components

import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardEntity

data class PokemonDetailsState(
    val pokemon: PokemonCardEntity? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
