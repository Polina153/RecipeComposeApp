package com.example.recipecomposeapp.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onRecipesClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        Button(
            onClick = { onCategoriesClick() },
            modifier = Modifier.weight(1f),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
        ) {
            Text("Категории")
        }
        Button(
            onClick = { onFavoriteClick() },
            modifier = Modifier.weight(1f),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Избранное")
        }
        Button(
            onClick = { onRecipesClick() },
            modifier = Modifier.weight(1f),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Рецепты")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShowBottomNavigation() {
    BottomNavigation({ }, {}, {})
}
