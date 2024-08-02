package com.yogigupta1206.m2ppokemon.domain.repository

import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardEntity
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview
import com.yogigupta1206.m2ppokemon.presentation.home.SortOption
import com.yogigupta1206.m2ppokemon.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<Resource<List<PokemonCardPreview>>>
    fun getPokemonDetails(pokemonId: String): Flow<Resource<PokemonCardEntity>>
}