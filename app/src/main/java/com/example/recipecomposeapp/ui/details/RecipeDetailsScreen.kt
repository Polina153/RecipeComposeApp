package com.example.recipecomposeapp.ui.details

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.compose.rememberAsyncImagePainter
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.ui.recipes.IngredientItem
import com.example.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.example.recipecomposeapp.ui.theme.Dimens.cornerMedium
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMedium
import com.example.recipecomposeapp.ui.theme.Dimens.sliderHeight
import com.example.recipecomposeapp.ui.theme.DividerColor
import com.example.recipecomposeapp.ui.theme.TextSecondaryColor
import com.example.recipecomposeapp.ui.theme.recipesAppTypography
import com.example.recipecomposeapp.util.FavoritePrefsManager
import com.example.recipecomposeapp.util.shareRecipe
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    recipe: RecipeUiModel
) {

    val context = LocalContext.current
    var currentPortions by rememberSaveable { mutableIntStateOf(recipe.servings) }
    val prefManager = remember { FavoritePrefsManager(context) }
    var isFavorite by remember { mutableStateOf(prefManager.isFavorite(recipe.id)) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(
            rememberAsyncImagePainter(model = recipe.imageUrl),
            "Изображение рецепта ${recipe.title}",
            recipe.title,
            showShareButton = true,
            onShareClick = { shareRecipe(context, recipe.id, recipe.title) },
            showFavoriteButton = true,
            isFavorite = isFavorite,
            onFavoriteToggle = {
                isFavorite = !isFavorite
                if (prefManager.isFavorite(recipeId = recipe.id)) {
                    prefManager.removeFromFavorites(recipeId = recipe.id)
                } else {
                    prefManager.addToFavorites(recipeId = recipe.id)
                }
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
            text = "Порции: $currentPortions",
            style = recipesAppTypography.titleSmall,
            color = TextSecondaryColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        )
        PortionsSlider(
            currentPortions = currentPortions,
            onPortionsChange = { currentPortions = it }
        )
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(cornerMedium),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
        ) {
            // Пересчитываем ингредиенты только при изменении порций или списка
            val scaledIngredients = remember(recipe.ingredients, currentPortions) {
                val multiplier = currentPortions.toDouble() / recipe.servings
                recipe.ingredients.map { ingredient ->
                    ingredient.copy(
                        amount = ingredient.amount?.let { it * multiplier }
                    )
                }
            }
            scaledIngredients.forEachIndexed { index, ingredient ->
                IngredientItem(
                    ingredient,
                    modifier = Modifier
                        .padding(
                            horizontal = paddingMedium,
                            vertical = paddingMedium
                        )
                )
                if (index < scaledIngredients.lastIndex) {
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
            modifier = modifier
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
