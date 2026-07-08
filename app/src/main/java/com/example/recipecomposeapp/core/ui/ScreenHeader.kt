package com.example.recipecomposeapp.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMain
import com.example.recipecomposeapp.ui.theme.Dimens.paddingMediumLarge
import com.example.recipecomposeapp.ui.theme.PrimaryColor
import com.example.recipecomposeapp.ui.theme.SurfaceColor

@Composable
fun ScreenHeader(
    imagePainter: Painter,
    contentDescription: String,
    title: String,
    showShareButton: Boolean,
    onShareClick: () -> Unit
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
        if (showShareButton) {
            IconButton(
                onShareClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(paddingMain)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "Поделиться",
                    tint = SurfaceColor,

                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewScreenHeader() {
    ScreenHeader(
        painterResource(id = R.drawable.categories),
        "Заголовок экрана Категории",
        "Категории",
        true,
        { }
    )
}