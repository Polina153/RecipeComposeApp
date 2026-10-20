package com.example.recipecomposeapp

import com.example.recipecomposeapp.Constants.PARAM_RECIPE_ID
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Destination(val route: String) {
    object Categories : Destination("Категории")
    object Favorites : Destination("Избранное")
    object Recipes : Destination("Рецепты/{categoryId}/{categoryTitle}/{categoryImageUrl}") {
        fun createRoute(categoryId: Int, categoryTitle: String, categoryImageUrl: String) =
            "Рецепты/$categoryId/${
                URLEncoder.encode(
                    categoryTitle,
                    StandardCharsets.UTF_8.toString()
                )
            }/${URLEncoder.encode(categoryImageUrl, StandardCharsets.UTF_8.toString())}"
    }

    object Details : Destination("Рецепт/{$PARAM_RECIPE_ID}") {
        fun createRoute(recipeId: Int) =
            "Рецепт/$recipeId"
    }
}
