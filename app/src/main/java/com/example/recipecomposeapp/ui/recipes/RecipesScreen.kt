package com.example.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.example.recipecomposeapp.ui.recipes.model.toUiModel
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMedium

@Composable
fun RecipesScreen(
    categoryId: Int,
    categoryTitle: String,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }

    LaunchedEffect(categoryId) {
        recipes = RecipesRepositoryStub.getRecipesByCategoryId(categoryId).map { it.toUiModel() }
    }

    Column(modifier.fillMaxSize()) {
        ScreenHeader(
            painterResource(id = R.drawable.recipe),
            "Заголовок экрана Рецепты",
            categoryTitle
        )
        LazyColumn(modifier.weight(1f)) {
            items(recipes) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    /*onClick = onRecipeClick*/
                    onClick = { recipeId ->
                        onRecipeClick(recipeId)
                    },
                    modifier = Modifier.padding(
                        horizontal = paddingMedium,
                        vertical = paddingMedium
                    )
                )
            }
        }
    }
}