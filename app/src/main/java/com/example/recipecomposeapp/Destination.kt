package com.example.recipecomposeapp

import android.net.Uri

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorites : Destination("favorites")
    object Recipes : Destination("recipes/{categoryId}/{categoryTitle}") {
        fun createRoute(categoryId: Int, categoryTitle: String) =
            "recipes/$categoryId/${Uri.encode(categoryTitle)}"
    }
}