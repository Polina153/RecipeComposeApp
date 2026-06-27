package com.example.recipecomposeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp() {

    val navController = rememberNavController()


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
                        onRecipeClick = { recipeId ->
                            //navController.navigate(
                                //TODO настроить переход на RecipeScreen
                                /*Destination.Recipes.createRoute(
                                    selectedCategoryId,
                                    Uri.encode(selectedCategoryTitle)*/
                            //)
                        }
                    )
                }
            }


        }
    }

}


@Composable
@Preview(showBackground = true)
fun RecipeAppPreview() {
    RecipesApp()
}