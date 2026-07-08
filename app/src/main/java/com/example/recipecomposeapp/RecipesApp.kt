package com.example.recipecomposeapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipecomposeapp.Constants.KEY_RECIPE_OBJECT
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub.Companion.getRecipeById
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.recipes.model.toUiModel
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp(deepLinkIntent: Intent?) {

    val navController = rememberNavController()

    // Обрабатываем deep link
    LaunchedEffect(deepLinkIntent) {
        deepLinkIntent?.data?.let { uri: Uri ->
            // Извлекаем id рецепта из URI
            // URI вида: recipeapp://recipe/123 или https://recipes.androidsprint.ru/recipe/123
            val recipeId = uri.lastPathSegment?.toIntOrNull()
            if (recipeId != null) {
                navController.navigate(Destination.Details.createRoute(recipeId)) {
                    // Убираем из back stack промежуточные экраны,
                    // чтобы при «назад» не возвращаться на пустой Categories
                    popUpTo(Destination.Categories.route) { inclusive = false }
                }
            }
        }
    }

    RecipeComposeAppTheme() {
        Scaffold(
            bottomBar = { BottomNavigation(navController) }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Destination.Categories.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(
                    Destination.Categories.route
                ) { backStackEntry ->
                    CategoriesScreen(
                        onCategoryClick = { categoryId, categoryTitle ->
                            navController.navigate(
                                Destination.Recipes.createRoute(
                                    categoryId,
                                    categoryTitle
                                )
                            )
                        }
                    )

                }
                composable(
                    Destination.Favorites.route
                ) { backStackEntry ->
                    FavoritesScreen()
                }
                composable(
                    Destination.Recipes.route,
                    arguments = listOf(
                        navArgument("categoryId") { type = NavType.IntType },
                        navArgument("categoryTitle") {
                            type =
                                NavType.StringType
                        })
                ) { backStackEntry ->
                    val selectedCategoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
                    val selectedCategoryTitle =
                        backStackEntry.arguments?.getString("categoryTitle")
                    RecipesScreen(
                        categoryId = selectedCategoryId,
                        categoryTitle = selectedCategoryTitle ?: "Рецепты",
                        onRecipeClick = { recipeId, recipe ->
                            navController.currentBackStackEntry?.savedStateHandle[KEY_RECIPE_OBJECT] =
                                recipe
                            navController.navigate(Destination.Details.createRoute(recipeId))
                        }
                    )
                }
                composable(
                    Destination.Details.route,
                    arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: return@composable
                    val recipe = getRecipeById(recipeId)?.toUiModel()
                    recipe?.let {
                        RecipeDetailsScreen(modifier = Modifier, recipe = it)
                    }
                }
            }
        }
    }
}

/*@Composable
@Preview(showBackground = true)
fun RecipeAppPreview() {
    RecipesApp()
}*/
