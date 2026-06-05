package com.example.recipecomposeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun ScreenHeader(imagePainter: Painter, contentDescription: String, title: String) {
    Box(modifier = Modifier.height(Dimens.headerHeight)){
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = paddingMain, bottom = paddingMediumLarge),
            content = { Text(title) }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewScreenHEader(){
    ScreenHeader(painterResource(id = R.drawable.categories), "Заголовок экрана Категории","Категории")
}