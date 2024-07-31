package com.yogigupta1206.m2ppokemon.data.repository

import android.util.Log
import com.yogigupta1206.m2ppokemon.data.data_source.db.PokemonDao
import com.yogigupta1206.m2ppokemon.data.data_source.network.NetworkHelper
import com.yogigupta1206.m2ppokemon.data.data_source.network.PokemonApi
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardEntity
import com.yogigupta1206.m2ppokemon.domain.model.PokemonCardPreview
import com.yogigupta1206.m2ppokemon.domain.repository.PokemonRepository
import com.yogigupta1206.m2ppokemon.utils.Resource
import com.yogigupta1206.m2ppokemon.utils.toPokemonCardEntities
import com.yogigupta1206.m2ppokemon.utils.toPokemonCardPreviews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
    private val networkHelper: NetworkHelper
) : PokemonRepository {

    companion object{
        val TAG = PokemonRepositoryImpl::class.java.simpleName
    }

    override fun getPokemonList(): Flow<List<PokemonCardPreview>> = flow {
        // Attempt to fetch from cache
        val cachedPokemon = pokemonDao.getPokemonCardPreviews()
        emit(cachedPokemon)

        // Fetch from network
        val response = getPokemonCardsFromNetwork()
        val pokemonList = response.responseData?.data.toPokemonCardEntities()

        // Cache pokemon
        pokemonList?.let { pokemonDao.insertAll(it) }
        emit(pokemonDao.getAll().toPokemonCardPreviews())
    }.flowOn(Dispatchers.IO) // Ensure network operations run on IO dispatcher

    private suspend fun getPokemonCardsFromNetwork() = networkHelper.safeApiCall { pokemonApi.getPokemonList() }

    override fun getPokemonDetails(pokemonId: String): Flow<Resource<PokemonCardEntity>> = flow {
        try {
            emit(Resource.Loading()) // Indicate loading state
            val pokemon = pokemonDao.getPokemonCard(pokemonId)
            Log.d(TAG, "getPokemonDetails: id:$pokemonId and $pokemon")
            emit(Resource.Success(pokemon)) // Emit the pokemon details
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error")) // Emit error if any
        }
    }.flowOn(Dispatchers.IO)

}