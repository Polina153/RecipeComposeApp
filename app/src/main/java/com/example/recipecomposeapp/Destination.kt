package com.example.recipecomposeapp

import android.net.Uri

sealed class Destination(val route: String) {
    object Categories : Destination("Категории")
    object Favorites : Destination("Избранное")
    object Recipes : Destination("Рецепты/{categoryId}/{categoryTitle}/{categoryImageUrl}") {
        fun createRoute(categoryId: Int, categoryTitle: String, categoryImageUrl: String) =
            "Рецепты/$categoryId/${Uri.encode(categoryTitle)}/${Uri.encode(categoryImageUrl)}"
    }
    object Details : Destination("Рецепт/{recipeId}") {
        fun createRoute(recipeId: Int) =
            "Рецепт/$recipeId"
    }
}
