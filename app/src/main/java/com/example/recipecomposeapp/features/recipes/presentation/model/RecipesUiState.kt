package com.example.recipecomposeapp.features.recipes.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class RecipesUiState(
    val category: String = "",
    val imageUrl: String = "",
    val recipesList: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isEmpty: Boolean
        get() = !isLoading && error == null && recipesList.isEmpty()
}
