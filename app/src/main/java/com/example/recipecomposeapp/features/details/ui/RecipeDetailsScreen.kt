package com.example.recipecomposeapp.features.details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.example.recipecomposeapp.features.recipes.ui.IngredientItem
import com.example.recipecomposeapp.ui.theme.Dimens.cornerMedium
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMedium
import com.example.recipecomposeapp.ui.theme.Dimens.sliderHeight
import com.example.recipecomposeapp.ui.theme.DividerColor
import com.example.recipecomposeapp.ui.theme.TextSecondaryColor
import com.example.recipecomposeapp.ui.theme.recipesAppTypography
import com.example.recipecomposeapp.util.shareRecipe
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailsViewModel = viewModel(
        factory = RecipeDetailsViewModel.Factory
    )
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error.orEmpty(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        else -> {
            val recipe = uiState.recipe

            if (recipe == null) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Рецепт недоступен")
                }
                return
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ScreenHeader(
                    rememberAsyncImagePainter(model = recipe.imageUrl),
                    "Изображение рецепта ${recipe.title}",
                    recipe.title.toString(),
                    showShareButton = true,
                    onShareClick = {
                        shareRecipe(
                            context,
                            recipe.id,
                            recipe.title
                        )
                    },
                    showFavoriteButton = true,
                    isFavorite = uiState.isFavorite,
                    onFavoriteToggle = viewModel::toggleFavorite
                )
                Text(
                    text = "Ингредиенты".uppercase(Locale.ROOT),
                    style = recipesAppTypography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingMedium)
                )
                Text(
                    text = "Порции: ${uiState.currentPortions}",
                    style = recipesAppTypography.titleSmall,
                    color = TextSecondaryColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingMedium)
                )
                PortionsSlider(
                    currentPortions = uiState.currentPortions,
                    onPortionsChange = viewModel::updatePortions
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(cornerMedium),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                ) {
                    val ingredients = uiState.scaledIngredients

                    ingredients.forEachIndexed { index, ingredient ->
                        IngredientItem(
                            ingredient,
                            modifier = Modifier
                                .padding(
                                    horizontal = paddingMedium,
                                    vertical = paddingMedium
                                )
                        )
                        if (index < ingredients.lastIndex) {
                            HorizontalDivider(
                                thickness = sliderHeight,
                                color = DividerColor
                            )
                        }
                    }
                }
                Text(
                    text = "Способ приготовления".uppercase(Locale.ROOT),
                    style = recipesAppTypography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingMedium)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingMedium),
                    shape = RoundedCornerShape(cornerMedium),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                ) {
                    recipe.method.forEachIndexed { index, method ->
                        Text(
                            "${index + 1}. $method",
                            modifier = Modifier
                                .padding(
                                    horizontal = paddingMedium,
                                    vertical = paddingMedium
                                )
                        )
                        if (index < recipe.method.lastIndex) {
                            HorizontalDivider(
                                thickness = sliderHeight,
                                color = DividerColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PortionsSlider(
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit
) {
    Slider(
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
        valueRange = RecipeDetailsViewModel.MIN_PORTIONS.toFloat()
                ..RecipeDetailsViewModel.MAX_PORTIONS.toFloat(),
        steps = RecipeDetailsViewModel.MAX_PORTIONS -
                RecipeDetailsViewModel.MIN_PORTIONS - 1
    )
}
