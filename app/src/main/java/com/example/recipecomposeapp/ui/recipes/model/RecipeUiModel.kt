package com.example.recipecomposeapp.ui.recipes.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.recipecomposeapp.Constants
import com.example.recipecomposeapp.data.model.RecipeDto
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class RecipeUiModel constructor(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val isFavorite: Boolean
) : Parcelable

fun RecipeDto.toUiModel(): RecipeUiModel {
    return RecipeUiModel(
        id = id,
        title = title,
        ingredients = ingredients.map { it.toUiModel() },
        method = method,
        imageUrl =
            if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl,
        isFavorite = false
    )
}