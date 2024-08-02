package com.yogigupta1206.m2ppokemon.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.yogigupta1206.m2ppokemon.R
import com.yogigupta1206.m2ppokemon.domain.model.ImageUrls
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview
import kotlinx.coroutines.Dispatchers

@Composable
fun PokemonListItem(pokemon: PokemonCardPreview, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically

        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.images.small)
                    .crossfade(true)
                    .dispatcher(Dispatchers.IO)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .transformations(RoundedCornersTransformation())
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = pokemon.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = pokemon.name, style = MaterialTheme.typography.titleLarge)
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    pokemon.types?.forEach { type ->
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = type, style = MaterialTheme.typography.titleSmall)
                        }
                    }
                }
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Lvl ${pokemon.level}", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "${pokemon.hp} HP", modifier = Modifier.padding(start = 4.dp),style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}