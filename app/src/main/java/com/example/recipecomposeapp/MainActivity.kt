package com.example.recipecomposeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {

    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 1. Сохраняем Intent, которым запущена Activity
        deepLinkIntent = intent

        setContent {
            // 2. Передаём deepLinkIntent в RecipesApp
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }
    }

    // 3. Обрабатываем warm start (когда Activity уже запущена, но к ней пришёл новый Intent)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        deepLinkIntent = intent
    }
}