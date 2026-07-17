package com.example.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.ui.categories.model.toUiModel
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMedium
import com.example.recipecomposeapp.ui.theme.Dimens.paddingSmallest


@Composable
fun CategoriesScreen(modifier: Modifier = Modifier, onCategoryClick: (Int, String) -> Unit) {

    val categories = remember { RecipesRepositoryStub.getCategories().map { it.toUiModel() } }

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
        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .padding(paddingMedium),
            contentPadding = PaddingValues(top = paddingSmallest, bottom = paddingMedium),
            horizontalArrangement = spacedBy(paddingMedium),
            verticalArrangement = spacedBy(paddingMedium)
        ) {
            items(categories, key = { it.id }) { category ->
                // 4. Используем компонент CategoryCard и передаем ему обработчик клика.
                // CategoryCard должен принимать модель и лямбду onClick.
                CategoryItem(category = category, onClick = { onCategoryClick(category.id, category.title) })
            }
        }
    }

}
