package com.yogigupta1206.m2ppokemon.presentation.pokemon_info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardEntity
import kotlinx.coroutines.Dispatchers


@Composable
fun PokemonDetailsScreen(
    viewModel: PokemonDetailsViewModel = hiltViewModel() ,
    onNavigateBack:() -> Unit
) {
    val state by viewModel.pokemonDetails.collectAsState()

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if
        (state.error.isNotEmpty())
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.error, color = Color.Red)
        }
    } else {
        state.pokemon?.let { PokemonDetailsContent(it, onNavigateBack) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailsContent(pokemon: PokemonCardEntity, onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pokemon.name) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl.large)
                    .crossfade(true)
                    .dispatcher(Dispatchers.IO)
                    .build(),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentScale = ContentScale.Inside
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = pokemon.name, style = MaterialTheme.typography.headlineLarge)
                Text(text = "Types: ${pokemon.types}", style = MaterialTheme.typography.bodyMedium)
            }
            DetailsGrid(pokemon)
        }
    }
}

@Composable
fun DetailsGrid(pokemon: PokemonCardEntity) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(8.dp)) {
        item { PokemonDetailItem("Level", pokemon.level?.toString() ?: "N/A") }
        item { PokemonDetailItem("HP", pokemon.hp.toString()) }
        item { PokemonDetailItem("Types", pokemon.types.toString()) }
        item { PokemonDetailItem("Subtypes", pokemon.subtypes.toString()) }

        pokemon.attacks?.let { attacks ->
            if(attacks.isNotEmpty()){
                item {
                    Text(
                        text = "Attacks:",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                items(attacks.size) { index ->
                    Text(
                        text = "${attacks[index].name} (${attacks.get(index = index).cost.joinToString(" ")}): ${attacks[index].damage} ${attacks[index].text}",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        pokemon.weaknesses?.let { weaknesses ->
            if(weaknesses.isNotEmpty()){
                item {
                    Text(
                        text = "Weaknesses:",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                items(weaknesses.size) { index ->
                    Text(
                        text = "${weaknesses[index].type} (${weaknesses[index].value})",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        pokemon.resistances?.let { resistances ->
            if(resistances.isNotEmpty()){
                item {
                    Text(
                        text = "Resistances:",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                items(resistances.size) { index ->
                    Text(
                        text = "${resistances[index].type} (${resistances[index].value})",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        pokemon.abilities?.let { abilities ->
            if(abilities.isNotEmpty()){
                item {
                    Text(
                        text = "Abilities:",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                items(abilities.size) { index ->
                    Text(
                        text = "${abilities[index].name}: ${abilities[index].text}",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}