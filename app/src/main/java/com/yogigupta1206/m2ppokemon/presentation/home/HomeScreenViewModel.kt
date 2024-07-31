package com.yogigupta1206.m2ppokemon.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview
import com.yogigupta1206.m2ppokemon.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemonList = mutableStateOf<List<PokemonCardPreview>>(listOf())

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            repository.getPokemonList().collect { result ->
                _pokemonList.value = result
            }
        }
    }

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _sortOption = MutableStateFlow(SortOption.HP) // Default sorting
    val sortOption: StateFlow<SortOption> = _sortOption

    val pokemonList: StateFlow<List<PokemonCardPreview>> = combine(
        repository.getPokemonList(), _searchText, _sortOption
    ) { allPokemon, search, sortOption ->
        val filteredPokemon = allPokemon.filter { it.name.contains(search, ignoreCase = true) }
        when (sortOption) {
            SortOption.LEVEL -> filteredPokemon.sortedBy { it.level }
            SortOption.HP -> filteredPokemon.sortedBy { it.hp }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun onSearchTextChange(newText: String) {
        _searchText.value = newText
    }

    fun onSortOptionChange(newOption: SortOption) {
        _sortOption.value = newOption
    }

}

enum class SortOption { LEVEL, HP }
