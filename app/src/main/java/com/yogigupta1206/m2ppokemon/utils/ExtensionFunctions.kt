package com.yogigupta1206.m2ppokemon.utils

import com.yogigupta1206.m2ppokemon.domain.model.ImageUrls
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardEntity
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardRawData


// List<PokemonCardRawData>? to List<PokemonCardEntity>?
fun List<PokemonCardRawData>?.toPokemonCardEntities(): List<PokemonCardEntity>? {
    return this?.map {
        PokemonCardEntity(
            id = it.id,
            imageUrl = ImageUrls(
                small = it.images.small,
                large = it.images.large
            ),
            name = it.name,
            types = it.types,
            subtypes = it.subtypes,
            level = it.level ?: 0,
            hp = it.hp,
            attacks = it.attacks,
            weaknesses = it.weaknesses,
            resistances = it.resistances ?: emptyList(),
            abilities = it.abilities ?: emptyList()
        )
    }
}

// List<PokemonCardEntity>? to List<PokemonCardPreview>
fun List<PokemonCardEntity>?.toPokemonCardPreviews(): List<PokemonCardPreview> {
    return this?.map {
        PokemonCardPreview(
            id = it.id,
            images = it.imageUrl,
            name = it.name,
            types = it.types,
            hp = it.hp,
            level = it.level
        )
    } ?: emptyList()
}