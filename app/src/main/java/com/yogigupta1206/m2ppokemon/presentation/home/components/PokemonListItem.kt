package com.yogigupta1206.m2ppokemon.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yogigupta1206.m2ppokemon.R
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardEntity
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview
import kotlinx.coroutines.Dispatchers

@Composable
fun PokemonListItem(pokemon: PokemonCardPreview, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemon.imageUrls.small)
                .crossfade(true)
                .dispatcher(Dispatchers.IO)
                .build(),
            contentDescription = pokemon.name,
        modifier = Modifier.size(80.dp),
        contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = pokemon.name)
            Row(modifier = Modifier.padding(top = 4.dp)) {
                pokemon.types?.forEach { type ->
                    Box(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            /*.background(
                                Color(
                                    android.graphics.Color.parseColor(
                                        "#${type.color}"
                                    )
                                ),
                                shape = RoundedCornerShape(4.dp)
                            )*/
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = type, color = Color.White)
                    }
                }
            }
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),

                contentDescription = "Level",
                modifier = Modifier.size(16.dp)
                )
                Text(text = "Lvl ${pokemon.level}", modifier = Modifier.padding(start = 4.dp))
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "HP",
                    modifier = Modifier.size(16.dp)
                )
                Text(text = "${pokemon.hp} HP", modifier = Modifier.padding(start = 4.dp))
            }
        }
    }
    }
}