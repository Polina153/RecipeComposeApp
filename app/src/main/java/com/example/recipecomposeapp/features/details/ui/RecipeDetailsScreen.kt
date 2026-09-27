package com.example.recipecomposeapp.features.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
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
    recipe: RecipeUiModel
) {

    val context = LocalContext.current
    val viewModel: RecipeDetailsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(recipe.id) {
        viewModel.initializeWithRecipe(recipe)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(
            rememberAsyncImagePainter(model = uiState.imageUrl),
            "Изображение рецепта ${uiState.title}",
            uiState.title,
            showShareButton = true,
            onShareClick = { shareRecipe(context, uiState.id, uiState.title) },
            showFavoriteButton = true,
            isFavorite = uiState.isFavorite,
            onFavoriteToggle = {
                viewModel.toggleFavorite()
            }
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
            onPortionsChange = { viewModel.updatePortions(it) }
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(cornerMedium),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
        ) {
            uiState.scaledIngredients.forEachIndexed { index, ingredient ->
                IngredientItem(
                    ingredient,
                    modifier = Modifier
                        .padding(
                            horizontal = paddingMedium,
                            vertical = paddingMedium
                        )
                )
                if (index < uiState.scaledIngredients.lastIndex) {
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
            uiState.method.forEachIndexed { index, method ->
                Text(
                    "${index + 1}. $method",
                    modifier = Modifier
                        .padding(
                            horizontal = paddingMedium,
                            vertical = paddingMedium
                        )
                )
                if (index < uiState.method.lastIndex) {
                    HorizontalDivider(
                        thickness = sliderHeight,
                        color = DividerColor
                    )
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
        valueRange = 1f..12f,
        steps = 10
    )
}
