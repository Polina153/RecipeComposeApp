package com.example.recipecomposeapp.core.network.api

import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesApiService {

    @GET("category")
    suspend fun getCategories(): List<CategoryDto>

    @GET("category/{id}/recipes")
    suspend fun getRecipesByCategory(@Path("id") categoryId: Int): List<RecipeDto>

/*    // Получение рецептов по списку ID
    @GET("recipes")
    suspend fun getRecipesByIds(@Query("ids") recipeIds: List<Int>): List<RecipeDto>

    // С пагинацией
    @GET("recipes")
    suspend fun getRecipes(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<RecipeDto>*/
}
