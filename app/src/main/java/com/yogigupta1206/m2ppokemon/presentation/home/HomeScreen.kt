package com.yogigupta1206.m2ppokemon.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview
import com.yogigupta1206.m2ppokemon.presentation.home.components.PokemonListItem

@Composable
fun HomeScreen(
    onNavigateToPokemonDetails: (String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val pokemonCollection by viewModel.pokemonList.collectAsState()
    val searchText by viewModel.searchText.collectAsState()

    Scaffold(
        topBar = {
            HomeTopAppBar()
        },
        content = { padding ->
            HomeScreenContent(
                padding,
                pokemonCollection,
                searchText,
                viewModel::onSearchTextChange,
                viewModel::onSortOptionChange,
                onNavigateToPokemonDetails
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar() {
    Column {
        TopAppBar(title = { Text(text = "Pokémon Card Collection") },
            navigationIcon = { /* Drawer Menu Icon (Optional) */ })
    }
}

@Composable
fun HomeScreenContent(
    padding: PaddingValues,
    pokemonListState: List<PokemonCardPreview>,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    sortOptionChange: (SortOption) -> Unit,
    onNavigateToPokemonDetails: (String) -> Unit,
) {
    Column(
        modifier = Modifier.padding(padding)
    ) {
        SearchHeader(
            searchText = searchText,
            onValueChange = onSearchTextChange,
            onSortOptionChange = sortOptionChange
        )

        LazyColumn {
            items(pokemonListState) { pokemon ->
                PokemonListItem(pokemon, onClick = {
                    onNavigateToPokemonDetails(pokemon.id)
                })
            }
        }
    }
}

@Composable
fun SearchHeader(
    searchText: String,
    onValueChange: (String) -> Unit,
    onSortOptionChange: (SortOption) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        SearchBar(
            modifier = Modifier.weight(1f),
            query = searchText,
            onQueryChange = onValueChange,
            onSearch = { /* Handle search action here */ }
        )

        Spacer(modifier = Modifier.width(16.dp)) // Add spacing between search bar and sorting

        var expanded by remember { mutableStateOf(false) }
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Sort",
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                SortOption.entries.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.name) },
                        onClick = {
                            onSortOptionChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Search") },
        modifier = modifier
            .fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = onSearch) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(50)
    )
}
