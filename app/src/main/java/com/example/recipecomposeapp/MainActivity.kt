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
import androidx.lifecycle.lifecycleScope
import com.example.recipecomposeapp.core.network.RecipesNetwork
import com.example.recipecomposeapp.core.network.TAG
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 1. Сохраняем Intent, которым запущена Activity
        deepLinkIntent = intent

        Log.d(TAG, "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }
        lifecycleScope.launch {
            try {
                Log.d(TAG, "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                val categories = fetchCategories()

                Log.d(TAG, "Успешно получено данных: ${categories.size} шт.")
                val titles = categories.map { it.title }
                Log.d(
                    TAG,
                    "Названия полученных категорий: $titles"
                )
                val recipes = loadRecipesInParallel(categories)

                Log.d(
                    TAG,
                    "Параллельная загрузка завершена. " +
                            "Всего получено рецептов: ${recipes.size}"
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при выполнении запроса", e)
            }
        }
    }

    private suspend fun loadRecipesInParallel(
        categories: List<CategoryDto>
    ): List<RecipeDto> = coroutineScope {
        val deferredRecipes = categories.map { category ->
            async {
                try {
                    fetchRecipe(category.id)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    Log.e(
                        TAG,
                        "Ошибка при загрузке рецептов категории \"${category.title}\"",
                        e
                    )
                    emptyList()
                }
            }
        }

        deferredRecipes.awaitAll().flatten()
    }

    private suspend fun fetchRecipe(categoryId: Int): List<RecipeDto> {
        Log.d(
            TAG,
            "Выполняю запрос рецептов категории $categoryId " +
                    "на потоке: ${Thread.currentThread().name}"
        )

        val recipes = RecipesNetwork.apiService.getRecipesByCategory(categoryId)

        Log.d(
            TAG,
            "Категория $categoryId: получено ${recipes.size} рецептов " +
                    "на потоке: ${Thread.currentThread().name}"
        )

        return recipes
    }


    private suspend fun fetchCategories(): List<CategoryDto> {
        val categories = RecipesNetwork.apiService.getCategories()

        Log.d(
            TAG,
            "Категории загружены на потоке: ${Thread.currentThread().name}"
        )

        return categories
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        deepLinkIntent = intent
    }
}
