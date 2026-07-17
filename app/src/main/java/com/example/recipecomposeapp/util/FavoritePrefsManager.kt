package com.example.recipecomposeapp.util

import android.content.Context
import com.example.recipecomposeapp.Constants.PREF_ID_KEY
import com.example.recipecomposeapp.Constants.PREF_KEY
import androidx.core.content.edit

class FavoritePrefsManager(val context: Context) {

    fun isFavorite(recipeId: Int): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        val favoriteRecipeIds = sharedPreferences.getStringSet(PREF_ID_KEY, emptySet())
        return favoriteRecipeIds.orEmpty().contains(recipeId.toString())
    }

    fun addToFavorites(recipeId: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        val favoriteRecipeIds = sharedPreferences.getStringSet(PREF_ID_KEY, emptySet())
        val updatedFavorites = favoriteRecipeIds?.toMutableSet() ?: mutableSetOf()
        updatedFavorites.add(recipeId.toString())
        sharedPreferences.edit {
            putStringSet(PREF_ID_KEY, updatedFavorites)
        } // Аpply() вызывается автоматически, т.е. работа асинхронно проводится, оптмизировано
    }

    fun removeFromFavorites(recipeId: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        val favoriteRecipeIds = sharedPreferences.getStringSet(PREF_ID_KEY, emptySet())
        val updatedFavorites = favoriteRecipeIds?.toMutableSet() ?: mutableSetOf()
        updatedFavorites.remove(recipeId.toString())
        sharedPreferences.edit {
            putStringSet(PREF_ID_KEY, updatedFavorites)
        } // Аpply() вызывается автоматически, т.е. работа асинхронно проводится, оптмизировано
    }

    fun getAllFavorites(): Set<String> {
        val sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        val favoriteRecipeIds = sharedPreferences.getStringSet(PREF_ID_KEY, emptySet())
        return favoriteRecipeIds.orEmpty()
    }
}