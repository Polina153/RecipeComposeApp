package com.example.recipecomposeapp.data.repository

import com.example.recipecomposeapp.data.model.RecipeDto

interface RecipesRepository {
    fun getRecipeById(recipeId: Int): RecipeDto?
}