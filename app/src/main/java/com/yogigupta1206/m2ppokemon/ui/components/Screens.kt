package com.yogigupta1206.m2ppokemon.ui.components


sealed class Screens(val route: String) {
    data object HomePage : Screens("homepage")
    data object PokemonDetailsPage : Screens("pokemonDetails")
}