package com.example.recipecomposeapp.util

import android.content.Context
import android.content.Intent
import com.example.recipecomposeapp.Constants.createRecipeDeepLink

fun shareRecipe(context: Context, recipeId: Int, recipeTitle: String) {
    val deepLink = createRecipeDeepLink(recipeId)

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, recipeTitle)
        putExtra(Intent.EXTRA_TEXT, "Попробуйте этот рецепт: $deepLink")
    }

    context.startActivity(Intent.createChooser(shareIntent, "Поделиться рецептом"))
}