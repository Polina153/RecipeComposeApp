package com.example.recipecomposeapp

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val KEY_RECIPE_OBJECT = "Key recipe object"

    // Deep Links
    const val DEEP_LINK_SCHEME = "recipeapp"
    const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"
    const val PARAM_RECIPE_ID = "recipeId"

    const val PREF_KEY = "recipe_app_prefs"
    const val PREF_ID_KEY = "favorite_recipe_ids"

    fun createRecipeDeepLink(recipeId: Int): String {
        return "$DEEP_LINK_BASE_URL/recipe/$recipeId"
    }
}