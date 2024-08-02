package com.yogigupta1206.m2ppokemon.presentation.home

import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview

data class HomeScreenUiState(
    val pokemonList: List<PokemonCardPreview> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val sortOption: SortOption = SortOption.HP
)

enum class SortOption { LEVEL, HP }
