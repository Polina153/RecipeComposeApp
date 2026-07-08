package com.example.recipecomposeapp.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.ui.theme.Dimens.paddingLarge


@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {

    Column(modifier = modifier.fillMaxSize()) {
        ScreenHeader(
            painterResource(id = R.drawable.favorites),
            "Заголовок экрана Избранное",
            "Избранное",
            false,
            {}
        )
        Text(
            text = "Список избранных рецептов",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingLarge)
        )
    }
}