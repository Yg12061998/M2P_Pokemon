package com.yogigupta1206.m2ppokemon.data.repository

import android.util.Log
import com.yogigupta1206.m2ppokemon.data.data_source.db.PokemonDao
import com.yogigupta1206.m2ppokemon.data.data_source.network.NetworkHelper
import com.yogigupta1206.m2ppokemon.data.data_source.network.NetworkResult
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
        val TAG: String = PokemonRepositoryImpl::class.java.simpleName
    }

    override fun getPokemonList(): Flow<Resource<List<PokemonCardPreview>>> = flow {
        emit(Resource.Loading())
        val cachedPokemon = pokemonDao.getPokemonCardPreviews()

        if(cachedPokemon.isNotEmpty()){
            emit(Resource.Success(cachedPokemon))
        }

        when(val response = getPokemonCardsFromNetwork()){
            is NetworkResult.Error -> {
                if(cachedPokemon.isEmpty()){
                    Log.d(TAG, "getPokemonList: Error: ${response.message}")
                    emit(Resource.Error(response.message ?: "Unknown error"))
                }
            }
            is NetworkResult.Success -> {
                Log.d(TAG, "getPokemonList: Success")
                val pokemonList = response.responseData?.data.toPokemonCardEntities()
                pokemonList?.let { pokemonDao.insertAll(it) }
                emit(Resource.Success(pokemonList?.toPokemonCardPreviews() ?: emptyList()))
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun getPokemonCardsFromNetwork() = networkHelper.safeApiCall { pokemonApi.getPokemonList() }

    override fun getPokemonDetails(pokemonId: String): Flow<Resource<PokemonCardEntity>> = flow {
        try {
            emit(Resource.Loading())
            val pokemon = pokemonDao.getPokemonCard(pokemonId)
            Log.d(TAG, "getPokemonDetails: id:$pokemonId and $pokemon")
            emit(Resource.Success(pokemon))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

}