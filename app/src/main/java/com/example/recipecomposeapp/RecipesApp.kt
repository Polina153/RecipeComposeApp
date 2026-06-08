package com.example.recipecomposeapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp() {
    var screenState: ScreenId by remember { mutableStateOf(ScreenId.CATEGORIES) }

    RecipeComposeAppTheme() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { screenState = ScreenId.CATEGORIES },
                    onFavoriteClick = { screenState = ScreenId.FAVORITES },
                    onRecipesClick = { screenState = ScreenId.RECIPES }
                )
            }
        ) { paddingValues ->
            when (screenState) {
                ScreenId.CATEGORIES -> CategoriesScreen(
                    modifier = Modifier.padding(paddingValues)
                )

                ScreenId.FAVORITES -> FavoritesScreen(
                    modifier = Modifier.padding(paddingValues)
                )

                ScreenId.RECIPES -> RecipesScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun RecipeAppPreview() {
    RecipesApp()
}