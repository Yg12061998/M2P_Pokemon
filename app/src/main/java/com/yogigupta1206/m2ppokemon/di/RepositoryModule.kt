package com.yogigupta1206.m2ppokemon.di

import com.yogigupta1206.m2ppokemon.data.data_source.db.AppDbDataSource
import com.yogigupta1206.m2ppokemon.data.data_source.network.NetworkHelper
import com.yogigupta1206.m2ppokemon.data.data_source.network.PokemonApi
import com.yogigupta1206.m2ppokemon.data.repository.PokemonRepositoryImpl
import com.yogigupta1206.m2ppokemon.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providePokemonRepository(appDbDataSource: AppDbDataSource, pokemonApi: PokemonApi, networkHelper: NetworkHelper) : PokemonRepository {
        return PokemonRepositoryImpl(pokemonApi, appDbDataSource.pokemonDao(), networkHelper)
    }

}