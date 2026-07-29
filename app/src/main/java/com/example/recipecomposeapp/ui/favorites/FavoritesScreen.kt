package com.example.recipecomposeapp.ui.favorites

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.ui.recipes.RecipeItem
import com.example.recipecomposeapp.ui.recipes.model.toUiModel
import com.example.recipecomposeapp.ui.theme.Dimens.paddingLarge
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMedium
import com.example.recipecomposeapp.util.FavoriteDataStoreManager
import kotlinx.coroutines.flow.map


@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    repository: RecipesRepositoryStub,
    onRecipeClick: (Int) -> Unit,
    manager: FavoriteDataStoreManager
) {

    val listFavoriteRecipes by remember {
        manager.getFavoriteIdsFlow().map { set ->
            set.mapNotNull { idString ->
                idString.toIntOrNull()?.let { id ->
                    repository.getRecipeById(id)
                }
            }.map { dto ->
                dto.toUiModel()
            }
        }
    }.collectAsState(initial = emptyList())


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
        if (listFavoriteRecipes.isEmpty()) {
            Text(
                text = "Здесь появится список избранных рецептов",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingLarge)
            )
        }
        LazyColumn(modifier.weight(1f)) {
            items(listFavoriteRecipes) { recipe ->
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