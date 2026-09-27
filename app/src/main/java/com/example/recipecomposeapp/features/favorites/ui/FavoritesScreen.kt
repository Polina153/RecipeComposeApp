package com.example.recipecomposeapp.features.favorites.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.favorites.presentation.FavoritesViewModel
import com.example.recipecomposeapp.features.recipes.ui.RecipeItem
import com.example.recipecomposeapp.ui.theme.Dimens.paddingLarge
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMedium


@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (Int) -> Unit,
) {



    val viewModel: FavoritesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        ScreenHeader(
            painterResource(id = R.drawable.favorites),
            "Заголовок экрана Избранное",
            "Избранное",
            false,
            {},
            false,
            isFavorite = false,
            onFavoriteToggle = {},
        )
        when {
            uiState.isLoading -> {
                Text(
                    text = "Загрузка...",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(paddingLarge)
                )
            }

            uiState.error != null -> {
                Text(
                    text = "Ошибка: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(paddingLarge)
                )
            }

            uiState.listFavoriteRecipes.isEmpty() -> {
                Text(
                    text = "Здесь появится список избранных рецептов",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingLarge)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(uiState.listFavoriteRecipes, key = { it.id }) { recipe ->
                        RecipeItem(
                            recipe = recipe,
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
    }
}
