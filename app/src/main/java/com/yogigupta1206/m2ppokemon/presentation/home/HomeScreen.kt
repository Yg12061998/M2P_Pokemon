package com.yogigupta1206.m2ppokemon.presentation.home

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogigupta1206.m2ppokemon.domain.model.ImageUrls
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardEntity
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview
import com.yogigupta1206.m2ppokemon.presentation.home.components.PokemonListItem
import com.yogigupta1206.m2ppokemon.ui.components.Screens

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val state by viewModel.pokemonList.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val sortOption by viewModel.sortOption.collectAsState()

    Scaffold(
        topBar = {
            HomeTopAppBar(
                searchText = searchText,
                onValueChange = viewModel::onSearchTextChange,
                sortOption = sortOption,
                onSortOptionChange = viewModel::onSortOptionChange
            )
        },
        content = { padding ->
            HomeScreenContent(padding, state, navController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    searchText: String,
    onValueChange: (String) -> Unit,
    sortOption: SortOption,
    onSortOptionChange: (SortOption) -> Unit
) {
    Column {
        TopAppBar(title = { Text(text = "Pokémon Card Collection") },
            navigationIcon = { /* Drawer Menu Icon (Optional) */ })
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

            Spacer(modifier = Modifier.width(8.dp)) // Add spacing between search bar and sorting

            var expanded by remember { mutableStateOf(false) }
            Box {
                Text(
                    text = "Sort: ${sortOption.name}",
                    modifier = Modifier.clickable { expanded = true },
                    style = MaterialTheme.typography.bodyMedium
                )
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
        shape = RoundedCornerShape(50) // Make ends rounded
    )
}

@Composable
fun HomeScreenContent(
    padding: PaddingValues,
    state: List<PokemonCardPreview>,
    navController: NavController
) {
    LazyColumn(contentPadding = padding) {
        items(state.size) { index ->
            PokemonListItem(state[index], onClick = {
                navController.navigate(Screens.PokemonDetailsPage.route + "?id=${state[index].id}")
            })
        }
    }
}
