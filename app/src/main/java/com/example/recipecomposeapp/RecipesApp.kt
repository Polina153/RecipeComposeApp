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
import androidx.navigation.navDeepLink
//import com.example.recipecomposeapp.Constants.KEY_RECIPE_OBJECT
import com.example.recipecomposeapp.Constants.PARAM_CATEGORY_ID
import com.example.recipecomposeapp.Constants.PARAM_CATEGORY_IMAGE_URL
import com.example.recipecomposeapp.Constants.PARAM_CATEGORY_TITLE
import com.example.recipecomposeapp.Constants.PARAM_RECIPE_ID
import com.example.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.example.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.example.recipecomposeapp.features.favorites.ui.FavoritesScreen
import com.example.recipecomposeapp.features.recipes.ui.RecipesScreen
import com.example.recipecomposeapp.ui.navigation.BottomNavigation
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
                ) {
                    CategoriesScreen(
                        onCategoryClick = { categoryId, categoryTitle, categoryImageUrl ->
                            navController.navigate(
                                Destination.Recipes.createRoute(
                                    categoryId,
                                    categoryTitle,
                                    categoryImageUrl
                                )
                            )
                        }
                    )

                }
                composable(
                    Destination.Favorites.route
                ) {
                    FavoritesScreen(
                        onRecipeClick = { recipeId ->
                            navController.navigate(Destination.Details.createRoute(recipeId))
                        }
                    )
                }
                composable(
                    Destination.Recipes.route,
                    arguments = listOf(
                        navArgument(PARAM_CATEGORY_ID) { type = NavType.IntType },
                        navArgument(PARAM_CATEGORY_TITLE) {
                            type =
                                NavType.StringType
                        },
                        navArgument(PARAM_CATEGORY_IMAGE_URL) {
                            type = NavType.StringType
                        })
                ) {

                    RecipesScreen(
                        modifier = Modifier,
                        onRecipeClick = { recipeId, recipe ->
                           // navController.currentBackStackEntry?.savedStateHandle[KEY_RECIPE_OBJECT] =
                             //   recipe
                            navController.navigate(Destination.Details.createRoute(recipeId))
                        },
                    )
                }
                composable(
                    Destination.Details.route,
                    arguments = listOf(navArgument(PARAM_RECIPE_ID) { type = NavType.IntType }),
                    deepLinks = listOf(navDeepLink {
                        uriPattern = "recipeapp://recipe/{$PARAM_RECIPE_ID}"
                    })
                ) { //backStackEntry ->
                    //val recipeId =
                        //backStackEntry.arguments?.getInt(PARAM_RECIPE_ID) ?: return@composable
                    //val recipe = getRecipeById(recipeId)?.toUiModel()
                    RecipeDetailsScreen(
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}
