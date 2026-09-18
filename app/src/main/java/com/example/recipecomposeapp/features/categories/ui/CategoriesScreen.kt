package com.example.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.categories.presentation.CategoriesViewModel
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMedium
import com.example.recipecomposeapp.ui.theme.Dimens.paddingSmallest


@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int, String, String) -> Unit
) {

    val viewModel: CategoriesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        ScreenHeader(
            painterResource(id = R.drawable.categories),
            "Заголовок экрана Категории",
            "Категории",
            false,
            {},
            showFavoriteButton = false,
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
            // 3. Успешное отображение данных
            else -> {
                LazyVerticalGrid(
                    GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f)
                        .padding(paddingMedium),
                    contentPadding = PaddingValues(top = paddingSmallest, bottom = paddingMedium),
                    horizontalArrangement = spacedBy(paddingMedium),
                    verticalArrangement = spacedBy(paddingMedium)
                ) {
                    items(
                        uiState.categories,
                        key = { category -> category.id }
                    )
                    { category ->
                        CategoryItem(
                            category = category,
                            onClick = {
                                onCategoryClick(
                                    category.id,
                                    category.title,
                                    category.imageUrl
                                )
                            })
                    }
                }
            }
        }
    }
}

