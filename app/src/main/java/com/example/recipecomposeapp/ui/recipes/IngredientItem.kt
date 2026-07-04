package com.example.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.data.model.IngredientDto
import com.example.recipecomposeapp.ui.recipes.model.IngredientUiModel
import com.example.recipecomposeapp.ui.recipes.model.toUiModel
import com.example.recipecomposeapp.ui.theme.Dimens.paddingSmall
import com.example.recipecomposeapp.ui.theme.TextSecondaryColor

@Composable
fun IngredientItem(ingredient: IngredientUiModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingSmall)
    ) {
        Text(
            text = ingredient.name.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryColor,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${ingredient.quantity} ${ingredient.unitOfMeasure}".uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryColor,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@Preview
@Composable
fun PreviewIngredientItem() {
    IngredientItem(
        ingredient = IngredientDto(
            quantity = "0.5",
            unitOfMeasure = "кг",
            description = "говяжий фарш"
        ).toUiModel(),
        modifier = Modifier
    )
}