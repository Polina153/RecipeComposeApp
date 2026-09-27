package com.example.recipecomposeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub.Companion.getRecipeById
import com.example.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import com.example.recipecomposeapp.util.FavoriteDataStoreManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import java.io.IOException

class FavoritesViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val favoriteManager = FavoriteDataStoreManager(application)

    val uiState: StateFlow<FavoritesUiState> =
        favoriteManager.getFavoriteIdsFlow().map { idStrings -> loadFavoriteRecipes(idStrings) }
            .retry(retries = 2) { cause -> cause is IOException }
            .catch { exception -> emit(FavoritesUiState(error = exception.message)) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FavoritesUiState(isLoading = true)
            )

    private fun loadFavoriteRecipes(ids: Set<String>): FavoritesUiState {
        if (ids.isEmpty()) { // Фикс бага: явно указываем isLoading = false для пустого списка
            return FavoritesUiState(listFavoriteRecipes = emptyList(), isLoading = false)
        }
        val recipes = ids.mapNotNull { idString ->
            idString.toIntOrNull()?.let { id -> getRecipeById(id) }
        }.map { it.toUiModel() }

        return FavoritesUiState(
            listFavoriteRecipes = recipes,
            isLoading = false
        )
    }
}
