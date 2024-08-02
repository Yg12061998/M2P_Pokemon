package com.yogigupta1206.m2ppokemon.utils

import com.yogigupta1206.m2ppokemon.domain.model.Ability
import com.yogigupta1206.m2ppokemon.domain.model.Attack
import com.yogigupta1206.m2ppokemon.domain.model.ImageUrls
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardEntity
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardRawData
import com.yogigupta1206.m2ppokemon.domain.model.Resistance
import com.yogigupta1206.m2ppokemon.domain.model.Weakness


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

fun Attack.toFormattedString(): String {
    return "${this.name} (${this.cost.joinToString(" ")}): ${this.damage} ${this.text}"
}

fun List<Attack>.toAttackFormattedString(): String {
    return this.joinToString("\n") { it.toFormattedString() }
}

fun Weakness.toFormattedString(): String {
    return "${this.type} (${this.value})"
}

fun List<Weakness>.toWeaknessFormattedString(): String {
    return this.joinToString("\n") { it.toFormattedString() }
}

fun Resistance.toFormattedString(): String {
    return "${this.type} (${this.value})"
}

fun List<Resistance>.toResistanceFormattedString(): String {
    return this.joinToString("\n") { it.toFormattedString() }
}

fun Ability.toFormattedString(): String {
    return "${this.name}: ${this.text}"
}

fun List<Ability>.toAbilityFormattedString(): String {
    return this.joinToString("\n") { it.toFormattedString() }
}