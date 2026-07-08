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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.recipecomposeapp.ui.theme.recipesAppTypography
import com.example.recipecomposeapp.utils.shareRecipe
import java.util.Locale

@Composable
fun RecipeDetailsScreen(modifier: Modifier = Modifier, recipe: RecipeUiModel) {

    val context = LocalContext.current

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
            onShareClick = { shareRecipe(context, recipe.id, recipe.title) }
        )
        Text(
            text = "Ингредиенты".uppercase(Locale.ROOT),
            style = recipesAppTypography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        )
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(cornerMedium),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
        ) {
            recipe.ingredients.forEachIndexed { index, ingredient ->
                IngredientItem(
                    ingredient,
                    modifier = Modifier
                        .padding(
                            horizontal = paddingMedium,
                            vertical = paddingMedium
                        )
                )
                if (index < recipe.ingredients.lastIndex) {
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