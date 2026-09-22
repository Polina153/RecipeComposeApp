package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMedium

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
) {

    val viewModel: RecipesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier.fillMaxSize()) {
        ScreenHeader(
            rememberAsyncImagePainter(model = uiState.imageUrl),
            "Заголовок экрана Рецепты",
            uiState.category,
            false,
            {},
            false,
            isFavorite = false,
            onFavoriteToggle = {},
        )
        val error = uiState.error
        when {
            // 1. Состояние загрузки
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            } // 2. Состояние ошибки
            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$error",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            // 3. Пустой список
            uiState.isEmpty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "В этой категории пока нет рецептов",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            // 4. Успешное отображение данных
            else -> {
                LazyColumn(Modifier.weight(1f)) {
                    items(
                        uiState.recipesList,
                        key = { recipe -> recipe.id }
                    ) { recipe ->
                        RecipeItem(
                            recipe = recipe,
                            onClick = { recipeId ->
                                onRecipeClick(recipeId, recipe)
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
