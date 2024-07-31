package com.yogigupta1206.m2ppokemon.data.data_source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardEntity
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_cards")
    suspend fun getAll(): List<PokemonCardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonCards: List<PokemonCardEntity>)

    @Query("DELETE FROM pokemon_cards")
    suspend fun deleteAll()

    @Query("SELECT id, images, name, types, level, hp FROM pokemon_cards")
    suspend fun getPokemonCardPreviews(): List<PokemonCardPreview>

    @Query("SELECT * FROM pokemon_cards WHERE id = :pokemonId")
    suspend fun getPokemonCard(pokemonId: String): PokemonCardEntity
}