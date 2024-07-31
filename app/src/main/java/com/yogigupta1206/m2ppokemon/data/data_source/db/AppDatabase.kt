package com.yogigupta1206.m2ppokemon.data.data_source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardEntity
import com.yogigupta1206.m2ppokemon.utils.AbilityConverter
import com.yogigupta1206.m2ppokemon.utils.AttackConverter
import com.yogigupta1206.m2ppokemon.utils.ImageUrlsConverter
import com.yogigupta1206.m2ppokemon.utils.ResistanceConverter
import com.yogigupta1206.m2ppokemon.utils.StringConverter
import com.yogigupta1206.m2ppokemon.utils.WeaknessConverter

@Database(entities = [PokemonCardEntity::class], version = 1)
@TypeConverters(
    AttackConverter::class,
    ResistanceConverter::class,
    WeaknessConverter::class,
    AbilityConverter::class,
    StringConverter::class,
    ImageUrlsConverter::class
)
abstract class AppDbDataSource : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val DATABASE_NAME = "m2p_pokemon_db"
    }

}