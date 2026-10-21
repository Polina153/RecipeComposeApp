package com.example.recipecomposeapp.features.details.presentation.model

import androidx.compose.runtime.Immutable
import com.example.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

@Immutable
data class RecipeDetailsUiState(
    val recipe: RecipeUiModel? = null,
    val currentPortions: Int = 1,
    val scaledIngredients: List<IngredientUiModel> = emptyList(),
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null
)/*{
   val scaledIngredients: List<IngredientUiModel>
        get() {
            // Аварийный случай: делить не на что — возвращаем как есть
            if (servings <= 0 || currentPortions <= 0) return recipe!!.ingredients

            // Нормальный случай: пересчитываем amount
            val multiplier = currentPortions.toDouble() / servings
            return recipe!!.ingredients.map { ingredient ->
                ingredient.copy(
                    amount = ingredient.amount?.let { it * multiplier }
                )
            }
        }

}
*/
