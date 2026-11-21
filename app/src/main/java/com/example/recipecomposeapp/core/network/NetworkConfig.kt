package com.example.recipecomposeapp.core.network

import android.util.Log
import com.example.recipecomposeapp.BuildConfig
import com.example.recipecomposeapp.core.network.NetworkConfig.BASE_URL
import com.example.recipecomposeapp.core.network.api.RecipesApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object NetworkConfig {

    const val BASE_URL = "https://recipes.aisprints.ru/api/"
}

const val TAG = "RecipesNetwork"

object RecipesNetwork {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val client = OkHttpClient.Builder()
        // Добавляем интерцептор только если сборка отладочная
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor { Log.d(TAG, "$it") }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(
            10,
            TimeUnit.SECONDS
        )
        .callTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val apiService: RecipesApiService by lazy {
        retrofit.create(RecipesApiService::class.java)
    }
}
