package com.yogigupta1206.m2ppokemon.data.data_source.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

sealed class NetworkResult<T>(
    val responseData: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(message: String? = null, data: T? = null) : NetworkResult<T>(data, message)
}


class NetworkHelper @Inject constructor(@ApplicationContext val context: Context) {

    inline fun <reified T> safeApiCall(apiCall: () -> Response<T>): NetworkResult<T> {
        try {
            return if (isInternetAvailable(context)) {
                val response = apiCall()
                when {
                    response.isSuccessful -> {
                        val body = response.body()
                        NetworkResult.Success(body)
                    }
                    else -> {
                        val jsonString = response.errorBody()?.string()
                        val body = GsonBuilder().create().fromJson(jsonString, T::class.java )
                        error("Api call failed ${response.code()} ${response.message()}", body)
                    }
                }
            } else {
                error("No Internet available!")
            }

        } catch (e: Exception) {
            return error(e.message.toString())
        }
    }

    fun <T> error(errorMessage: String, data: T? = null): NetworkResult<T> =
        NetworkResult.Error("Api Call Failed: $errorMessage", data)


    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }

}
