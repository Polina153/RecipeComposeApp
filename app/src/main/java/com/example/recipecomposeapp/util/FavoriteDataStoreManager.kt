package com.example.recipecomposeapp.util

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FavoriteDataStoreManager(private val appContext: Context) {

    fun getFavoriteIdsFlow(): Flow<Set<String>> {
        val preferencesFlow: Flow<Preferences> = appContext.dataStore.data
        return preferencesFlow.map { preferences ->
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
        }
    }

    fun isFavoriteFlow(recipeId: Int): Flow<Boolean> {
        return getFavoriteIdsFlow().map { favoriteIds ->
            favoriteIds.contains(recipeId.toString())
        }
    }

    fun getFavoriteCountFlow(): Flow<Int> {
        return getFavoriteIdsFlow().map { it.size }
    }


    suspend fun isFavorite(recipeId: Int): Boolean {
        val preferences = appContext.dataStore.data.first()
        val favoriteIds = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
        return favoriteIds.contains(recipeId.toString())
    }

    suspend fun addFavorite(recipeId: Int) {
        appContext.dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updatedFavorites = currentFavorites + recipeId.toString()
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        appContext.dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            if (currentFavorites.contains(recipeId.toString())) {
                val updatedFavorites = currentFavorites - recipeId.toString()
                preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites
            }

        }
    }
}