package com.example.recipecomposeapp.ui.categories

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
import com.example.recipecomposeapp.ui.components.ScreenHeader
import com.example.recipecomposeapp.ui.theme.Dimens.paddingLarge

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {

    Column(modifier = modifier.fillMaxSize()) {
        ScreenHeader(
            painterResource(id = R.drawable.categories),
            "Заголовок экрана Категории",
            "Категории"
        )
        Text(
            text = "Список категорий",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingLarge)
        )
    }
}
