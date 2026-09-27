package com.example.recipecomposeapp.features.favorites.presentation.model

import androidx.compose.runtime.Immutable
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

@Immutable
data class FavoritesUiState(
    val listFavoriteRecipes: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
