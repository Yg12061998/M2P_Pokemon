package com.yogigupta1206.m2ppokemon.presentation.pokemon_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogigupta1206.m2ppokemon.domain.repository.PokemonRepository
import com.yogigupta1206.m2ppokemon.presentation.pokemon_info.components.PokemonDetailsState
import com.yogigupta1206.m2ppokemon.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel@Inject constructor(
    private val pokemonRepository: PokemonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _pokemonDetails: MutableStateFlow<PokemonDetailsState> =
        MutableStateFlow(PokemonDetailsState())
    val pokemonDetails: StateFlow<PokemonDetailsState> = _pokemonDetails

    init {
        savedStateHandle.get<String>("id")?.let { pokemonId ->
            getPokemonDetails(pokemonId)
        }
    }

    private fun getPokemonDetails(pokemonId: String) {
        pokemonRepository.getPokemonDetails(pokemonId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _pokemonDetails.value = PokemonDetailsState(pokemon = result.data, isLoading = false)
                }
                is Resource.Error -> {
                    _pokemonDetails.value = PokemonDetailsState(error = result.message ?: "Unknown error",  isLoading = false)
                }
                is Resource.Loading -> {
                    _pokemonDetails.value = PokemonDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}