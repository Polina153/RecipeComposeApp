package com.example.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.util.FavoriteDataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteDataStoreManager: FavoriteDataStoreManager =
        FavoriteDataStoreManager(application)

    // Базовое состояние — объявляем ДО uiState
    private val _baseState = MutableStateFlow(RecipeDetailsUiState())

    // Поток текущего ID рецепта
    private val currentRecipeId = MutableStateFlow<Int?>(null)

    // Реактивный поток избранного: при изменении ID автоматически подтягиваем новое значение из DataStore
    private val isFavoriteFlow = currentRecipeId
        .filterNotNull()
        .flatMapLatest { recipeId ->
            favoriteDataStoreManager.isFavoriteFlow(recipeId)
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = false
        )

    // Объединяем базовое состояние и поток избранного
    val uiState: StateFlow<RecipeDetailsUiState> =
        combine(_baseState, isFavoriteFlow) { state, isFavorite ->
            state.copy(isFavorite = isFavorite)
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = RecipeDetailsUiState()
        )

    fun initializeWithRecipe(recipe: RecipeUiModel) {
        // Раскладываем рецепт по полям состояния
        _baseState.update { state ->
            state.copy(
                id = recipe.id,
                title = recipe.title,
                imageUrl = recipe.imageUrl,
                ingredients = recipe.ingredients,
                method = recipe.method,
                servings = recipe.servings,
                currentPortions = recipe.servings,
                isLoading = false,
                error = null
            )
        }
        // Устанавливаем ID для реактивной подписки на избранное
        currentRecipeId.value = recipe.id
    }

    fun toggleFavorite() {
        val recipeId = currentRecipeId.value ?: return
        viewModelScope.launch {
            val currentlyFavorite = uiState.value.isFavorite
            if (currentlyFavorite) {
                favoriteDataStoreManager.removeFavorite(recipeId)
            } else {
                favoriteDataStoreManager.addFavorite(recipeId)
            }
        }
    }

    fun updatePortions(count: Int) {
        _baseState.update { it.copy(currentPortions = count) }
    }
}
