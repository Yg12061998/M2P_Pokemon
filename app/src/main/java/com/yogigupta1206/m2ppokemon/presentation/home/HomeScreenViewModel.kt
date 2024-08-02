package com.yogigupta1206.m2ppokemon.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogigupta1206.m2ppokemon.domain.repository.PokemonRepository
import com.yogigupta1206.m2ppokemon.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.getPokemonList(), _searchText.debounce(300), _uiState
            ) { pokemonListResource, searchText, uiState ->
                when (pokemonListResource) {
                    is Resource.Success -> {
                        viewModelScope.launch(Dispatchers.Default) { // Use Default dispatcher
                            val pokemonList = pokemonListResource.data ?: emptyList()
                            val filteredPokemon = pokemonList.filter {
                                it.name.contains(searchText, ignoreCase = true)
                            }
                            val sortedPokemon = when (uiState.sortOption) {
                                SortOption.LEVEL -> filteredPokemon.sortedBy { it.level }
                                SortOption.HP -> filteredPokemon.sortedBy { it.hp }
                            }
                            _uiState.value = _uiState.value.copy(
                                pokemonList = sortedPokemon, isLoading = false, errorMessage = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false, errorMessage = pokemonListResource.message
                        )
                    }

                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }.distinctUntilChanged().launchIn(viewModelScope)
        }
    }

    fun onSearchTextChange(newText: String) {
        _searchText.value = newText
    }

    fun onSortOptionChange(newOption: SortOption) {
        _uiState.value = _uiState.value.copy(sortOption = newOption)
    }

}
