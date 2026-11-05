package com.example.recipecomposeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.recipecomposeapp.data.model.CategoryDto
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {

    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val TAG = "NetworkLesson"
    private val apiUrl = "https://recipes.aisprints.ru/api/category"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 1. Сохраняем Intent, которым запущена Activity
        deepLinkIntent = intent

        Log.d(TAG, "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")
        setContent {
            // 2. Передаём deepLinkIntent в RecipesApp
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }
        Thread {
            try {
                Log.d(TAG, "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                // Вызываем функцию запроса
                val categories = fetchCategories(apiUrl)

                // Здесь можно обновить UI через runOnUiThread или вернуться в Coroutine Dispatcher.Main
                Log.d(TAG, "Успешно получено данных: ${categories.size} шт.")
                val titles = categories.map { it.title }
                Log.d(
                    TAG,
                    "Названия полученных категорий: $titles"
                )

            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при выполнении запроса", e)
            }
        }.start() // Обязательно вызываем .start(), чтобы поток запустился
    }

    /**
     * Функция выполнения HTTP-запроса.
     * Возвращает список объектов DTO.
     */
    private fun fetchCategories(urlString: String): List<CategoryDto> {
        var urlConnection: HttpURLConnection? = null

        return try {
            val url = URL(urlString)
            val connection = url.openConnection()
            urlConnection = requireNotNull(connection as? HttpURLConnection) {
                "Unexpected URLConnection type for $url: ${connection::class.java}"
            }

            urlConnection.requestMethod = "GET"
            urlConnection.connectTimeout = 10000
            urlConnection.readTimeout = 15000

            if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                // inputStream.bufferedReader().use { ... } гарантирует закрытие ридера В ЛЮБОМ СЛУЧАЕ
                // даже если внутри блока произойдет исключение (OutOfMemoryError, SerializationException и т.д.)
                val responseText = urlConnection.inputStream.bufferedReader().use { reader ->
                    reader.readText()
                }

                Log.d(TAG, "Тело responseText: $responseText.")
                // Десериализация JSON -> Kotlin Objects

                json.decodeFromString<List<CategoryDto>>(responseText)

            } else {
                throw Exception("HTTP Error: ${urlConnection.responseCode}. Message: ${urlConnection.responseMessage}")
            }

        } finally {
            // Соединение нужно закрывать всегда, независимо от успеха или ошибки выше
            urlConnection?.disconnect()
        }
    }

    // 3. Обрабатываем warm start (когда Activity уже запущена, но к ней пришёл новый Intent)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        deepLinkIntent = intent
    }


    companion object {
        private val json = Json { ignoreUnknownKeys = true }
    }
}
