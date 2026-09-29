package com.example.recipecomposeapp.features.details.presentation.model

import androidx.compose.runtime.Immutable
import com.example.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel

@Immutable
data class RecipeDetailsUiState(
    val id: Int = 0,
    val title: String = "",
    val isFavorite: Boolean = false,
    val ingredients: List<IngredientUiModel> = emptyList(),
    val method: List<String> = emptyList(),
    val imageUrl: String = "",
    val servings: Int = 0,
    val currentPortions: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
){
   val scaledIngredients: List<IngredientUiModel>
        get() {
            // Аварийный случай: делить не на что — возвращаем как есть
            if (servings <= 0 || currentPortions <= 0) return ingredients

            // Нормальный случай: пересчитываем amount
            val multiplier = currentPortions.toDouble() / servings
            return ingredients.map { ingredient ->
                ingredient.copy(
                    amount = ingredient.amount?.let { it * multiplier }
                )
            }
        }

}
