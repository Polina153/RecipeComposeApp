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
import com.example.recipecomposeapp.data.model.RecipeDto
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val client = OkHttpClient.Builder()
        // Добавляем интерцептор только если сборка отладочная
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor { Log.d(TAG, "$it") }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(
            10,
            TimeUnit.SECONDS
        ) // Добавляем callTimeout для ограничения всего запроса целиком
        .callTimeout(60, TimeUnit.SECONDS)
        .build()

    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val TAG = "Pool"
    private val apiUrl = "https://recipes.aisprints.ru/api/category"
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

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
        threadPool.execute {
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
                getRecipesInThreads(categories)

            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при выполнении запроса", e)
            }
        }
    }

    private fun getRecipesInThreads(categories: List<CategoryDto>) {
        categories.forEach { categoryDto ->
            threadPool.execute {
                fetchRecipe(categoryDto.id, categoryDto.title)
            }
        }
    }

    private fun fetchRecipe(categoryId: Int, categoryTitle: String) {

        val request = Request.Builder()
            .url("${apiUrl}/$categoryId/recipes")
            .get()
            .build()

        Log.d(TAG, "Выполняю запрос рецепта на потоке: ${Thread.currentThread().name}")

        client.newCall(request).execute().use { response ->

            if (!response.isSuccessful) {
                //showError("Проверьте интернет-соединение")
                Log.e(TAG, "HTTP Error for Category $categoryId: ${response.code}")
                throw HttpException(
                    response.code,
                    "HTTP Error for Category $categoryId:: ${response.code}"
                )
            }

            val responseString = response.body?.string()
                ?: throw IOException("Body is null for recipe $categoryId")

            Log.d(
                TAG,
                "Тело responseText Recipes ($categoryId): ${responseString.length}."
            )

            val recipes =
                json.decodeFromString<List<RecipeDto>>(responseString)

            Log.d(
                TAG,
                "Категория $categoryId: получено ${recipes.size} рецептов в потоке ${Thread.currentThread().name}"
            )
            if (recipes.isEmpty()) {
                Log.w(TAG, "Категория $categoryId вернулась пустой")
            }
        }
    }

    /**
     * Функция выполнения HTTP-запроса.
     * Возвращает список объектов DTO.
     */
    private fun fetchCategories(urlString: String): List<CategoryDto> {

        val request = Request.Builder()
            .url(urlString)
            .get()
            .build()

        client.newCall(request).execute().use { response ->

            if (!response.isSuccessful) {
                //showError("Сервер временно недоступен, попробуйте позже")
                throw HttpException(response.code, "HTTP Error: ${response.code}")
            }

            // Безопасное чтение тела. use{} закроет поток автоматически.
            val jsonString =
                response.body?.string() ?: throw IOException("Response body is null")

            Log.d(TAG, "Тело responseText Categories: ${jsonString.length}.")

            // Десериализация JSON -> Kotlin Objects
            return json.decodeFromString<List<CategoryDto>>(jsonString)
        }
    }

    // 3. Обрабатываем warm start (когда Activity уже запущена, но к ней пришёл новый Intent)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        deepLinkIntent = intent
    }

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }

    companion object {
        private val json = Json { ignoreUnknownKeys = true }
    }
}

class HttpException(val code: Int, message: String) : Exception(message)
