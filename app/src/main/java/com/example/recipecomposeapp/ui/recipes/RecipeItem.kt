package com.example.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.recipes.model.IngredientUiModel
import com.example.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.example.recipecomposeapp.ui.theme.Dimens.cornerExtraLarge
import com.example.recipecomposeapp.ui.theme.Dimens.elevationMedium
import com.example.recipecomposeapp.ui.theme.Dimens.paddingSmall


@Composable
fun RecipeItem(recipe: RecipeUiModel, onClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = { onClick(recipe.id) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cornerExtraLarge),
        elevation = CardDefaults.cardElevation(elevationMedium),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingSmall)
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = "Карточка рецепта",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = cornerExtraLarge,
                            topEnd = cornerExtraLarge
                        )
                    ),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.img_placeholder),
                error = painterResource(R.drawable.img_error)
            )
            Text(
                text = recipe.title,  // заглавные
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Ингредиенты",  // заглавные
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = recipe.ingredients.joinToString(", ") { it.name },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@Preview
@Composable
fun PreviewRecipeItem() {
    RecipeItem(
        recipe = RecipeUiModel(
            1,
            "Бургер",
            "file:///android_asset/burger.jpg",
            ingredients = listOf(
                IngredientUiModel(name = "Булочка", quantity = "1", unitOfMeasure = "шт"),
                IngredientUiModel(name = "Котлета", quantity = "1", unitOfMeasure = "шт")
            ),
            listOf("Обжарить", "Нарезать", "Смешать", "Приготовить"),
            isFavorite = false,
        ),
        onClick = TODO(),
        modifier = Modifier
    )
}