package com.example.recipecomposeapp.core.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.AccentColor
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMain
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMediumLarge
import com.example.recipecomposeapp.ui.theme.PrimaryColor

@Composable
fun ScreenHeader(
    imagePainter: Painter,
    contentDescription: String,
    title: String,
    showShareButton: Boolean,
    onShareClick: () -> Unit,
    showFavoriteButton: Boolean,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    Box(modifier = Modifier.height(Dimens.headerHeight)) {
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = paddingMain, bottom = paddingMediumLarge)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    title,
                    color = PrimaryColor,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(paddingMain),
            horizontalAlignment = Alignment.End
        ) {
            if (showFavoriteButton) {
                IconButton(onClick = onFavoriteToggle, modifier = Modifier.size(48.dp)) {
                    Crossfade(
                        targetState = isFavorite,
                        animationSpec = tween(durationMillis = 300)
                    ) { favorite ->
                        val vector = ImageVector.vectorResource(
                            id = if (favorite) R.drawable.ic_heart
                            else R.drawable.ic_heart_empty
                        )
                        Icon(
                            painter = rememberVectorPainter(image = vector),
                            contentDescription = if (favorite) "Убрать из избранного"
                            else "Добавить в избранное",
                            tint = AccentColor,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}


/*
@Composable
@Preview(showBackground = true)
fun PreviewScreenHeader() {
    ScreenHeader(
        painterResource(id = R.drawable.categories),
        "Заголовок экрана Категории",
        "Категории",
        true,
        { },
    )
}*/
