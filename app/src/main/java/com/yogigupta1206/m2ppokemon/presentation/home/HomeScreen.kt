package com.yogigupta1206.m2ppokemon.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogigupta1206.m2ppokemon.presentation.home.components.PokemonListItem

@Composable
fun HomeScreen(
    onNavigateToPokemonDetails: (String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val searchText by viewModel.searchText.collectAsState()

    Scaffold(
        topBar = {
            HomeTopAppBar()
        },
        content = { padding ->
            HomeScreenContent(
                padding,
                uiState,
                searchText,
                {newText ->
                    viewModel.onSearchTextChange(newText)
                },
                viewModel::onSortOptionChange,
                onNavigateToPokemonDetails,
                viewModel::fetchData
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
    uiState: HomeScreenUiState,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    sortOptionChange: (SortOption) -> Unit,
    onNavigateToPokemonDetails: (String) -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.padding(padding)
    ) {
        SearchHeader(
            searchText = searchText,
            onValueChange = onSearchTextChange,
            onSortOptionChange = sortOptionChange
        )

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            !uiState.errorMessage.isNullOrBlank() && uiState.pokemonList.isEmpty()-> {
                ErrorView(message = uiState.errorMessage, onRetry = onRetry)
            }
            else ->{
                LazyColumn {
                    items(uiState.pokemonList){
                        PokemonListItem(it, onClick = {
                            onNavigateToPokemonDetails(it.id)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
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
            onSearch = { }
        )

        Spacer(modifier = Modifier.width(16.dp))

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
