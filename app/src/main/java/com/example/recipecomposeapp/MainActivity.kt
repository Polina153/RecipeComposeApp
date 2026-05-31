package com.example.recipecomposeapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeComposeAppTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    TextShowing(
                        text = "Recipes App",
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewLightTheme() {
    RecipeComposeAppTheme(
        darkTheme = false
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Text(
                text = "JCP-01: Базовые UI-элементы",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Text#1",
                //style = MaterialTheme.typography.headlineSmall
                //style = MaterialTheme.typography.labelSmall
                style = MaterialTheme.typography.bodySmall
            )
            val context = LocalContext.current
            Button(onClick = {
                Toast.makeText(context, "Button", Toast.LENGTH_SHORT).show()
            }) {
                Text("Нажми меня")
            }
            OutlinedButton(onClick = {
                Toast.makeText(context, "OutlinedButton", Toast.LENGTH_SHORT).show()
            }) {
                Text("Нажми меня")
            }
            TextButton(onClick = {
                Toast.makeText(context, "TextButton", Toast.LENGTH_SHORT).show()
            }) {
                Text("Нажми меня")
            }

            Text("ContentScale.Crop")
            Image(
                painter = painterResource(R.drawable.sample),
                contentDescription = "Crop example",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
            )

            // ContentScale.Fit — вписывает изображение целиком, могут быть пустые области
            Text("ContentScale.Fit")
            Image(
                painter = painterResource(R.drawable.sample),
                contentDescription = "Fit example",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(200.dp)
            )

            // ContentScale.FillWidth — растягивает по ширине, высота подстраивается
            Text("ContentScale.FillWidth")
            Image(
                painter = painterResource(R.drawable.sample),
                contentDescription = "FillWidth example",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.size(200.dp)
            )

            // ContentScale.Inside — похож на Fit, но не увеличивает маленькие изображения
            Text("ContentScale.Inside")
            Image(
                painter = painterResource(R.drawable.sample),
                contentDescription = "Inside example",
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(200.dp)
            )

            // Используйте: var text by remember { mutableStateOf("") }
            var text by remember { mutableStateOf("") }
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Введите текст") },
                placeholder = { Text("Ваше имя...") },
                singleLine = true,  // Одна строка вместо многострочного
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            // Отображаем введённый текст
            Text(
                text = "Вы ввели: $text",
                modifier = Modifier.padding(top = 16.dp)
            )

            // Card { Column { Text() + Button() + Image() } }
            Card(
                modifier = Modifier.padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),  // Высота тени
                shape = RoundedCornerShape(12.dp),  // Скругление углов
                colors = CardDefaults.cardColors(
                    containerColor = Color.White  // Цвет фона
                )
            ) {
                // Изображение
                Image(
                    painter = painterResource(R.drawable.sample),
                    contentDescription = "Card image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                // Заголовок
                Text(
                    text = "Заголовок карточки",
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Описание
                Text(
                    text = "Это описание внутри карточки. Здесь может быть любой текст.",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Кнопка
                Button(
                    onClick = { Toast.makeText(context, "CardButton", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Нажми меня")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewDarkTheme() {
    RecipeComposeAppTheme(
        darkTheme = true
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Text(
                text = "JCP-01: Базовые UI-элементы",
                style = MaterialTheme.typography.headlineMedium
            )

            // Создайте различные Text с разными style
            Text(
                text = "Text#1",
                //style = MaterialTheme.typography.headlineSmall
                //style = MaterialTheme.typography.labelSmall
                style = MaterialTheme.typography.bodySmall
            )

            // Покажите различия в стилях кнопок
            val context = LocalContext.current
            Button(onClick = {
                Toast.makeText(context, "Button", Toast.LENGTH_SHORT).show()
            }) {
                Text("Нажми меня")
            }
            OutlinedButton(onClick = {
                Toast.makeText(context, "OutlinedButton", Toast.LENGTH_SHORT).show()
            }) {
                Text("Нажми меня")
            }
            TextButton(onClick = {
                Toast.makeText(context, "TextButton", Toast.LENGTH_SHORT).show()
            }) {
                Text("Нажми меня")
            }

            // Пример: Image(painterResource(R.drawable.sample), contentScale = ContentScale.Crop)
            // Попробуйте: ContentScale.Fit, ContentScale.FillWidth, ContentScale.Inside
            Text("ContentScale.Crop")
            Image(
                painter = painterResource(R.drawable.sample),
                contentDescription = "Crop example",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
            )

            // ContentScale.Fit — вписывает изображение целиком, могут быть пустые области
            Text("ContentScale.Fit")
            Image(
                painter = painterResource(R.drawable.sample),
                contentDescription = "Fit example",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(200.dp)
            )

            // ContentScale.FillWidth — растягивает по ширине, высота подстраивается
            Text("ContentScale.FillWidth")
            Image(
                painter = painterResource(R.drawable.sample),
                contentDescription = "FillWidth example",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.size(200.dp)
            )

            // ContentScale.Inside — похож на Fit, но не увеличивает маленькие изображения
            Text("ContentScale.Inside")
            Image(
                painter = painterResource(R.drawable.sample),
                contentDescription = "Inside example",
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(200.dp)
            )

            // Используйте: var text by remember { mutableStateOf("") }
            var text by remember { mutableStateOf("") }
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Введите текст") },
                placeholder = { Text("Ваше имя...") },
                singleLine = true,  // Одна строка вместо многострочного
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            // Отображаем введённый текст
            Text(
                text = "Вы ввели: $text",
                modifier = Modifier.padding(top = 16.dp)
            )

            // Card { Column { Text() + Button() + Image() } }
            Card(
                modifier = Modifier.padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),  // Высота тени
                shape = RoundedCornerShape(12.dp),  // Скругление углов
                colors = CardDefaults.cardColors(
                    containerColor = Color.White  // Цвет фона
                )
            ) {
                // Изображение
                Image(
                    painter = painterResource(R.drawable.sample),
                    contentDescription = "Card image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                // Заголовок
                Text(
                    text = "Заголовок карточки",
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Описание
                Text(
                    text = "Это описание внутри карточки. Здесь может быть любой текст.",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Кнопка
                Button(
                    onClick = { Toast.makeText(context, "CardButton", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Нажми меня")
                }
            }
        }
    }
}

@Composable
fun TextShowing(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TextShowingPreview() {
    MaterialTheme {
        TextShowing("Android")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecipeComposeAppTheme {
        Greeting("Android")
    }
}
