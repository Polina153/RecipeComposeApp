package com.example.recipecomposeapp.features.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.recipecomposeapp.Constants.PARAM_RECIPE_ID
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import com.example.recipecomposeapp.util.FavoriteDataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class RecipeDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
    private val favoriteDataStoreManager: FavoriteDataStoreManager
) : ViewModel() {

    private val recipeId: Int = checkNotNull(
        savedStateHandle[PARAM_RECIPE_ID]
    ) {
        "В маршруте Details отсутствует обязательный параметр $PARAM_RECIPE_ID"
    }

    // Базовое состояние — объявляем ДО uiState
    private val _baseState = MutableStateFlow(RecipeDetailsUiState())

    // Объединяем базовое состояние и поток избранного
    val uiState: StateFlow<RecipeDetailsUiState> =
        combine(
            _baseState,
            favoriteDataStoreManager.isFavoriteFlow(recipeId)
        ) { state, isFavorite ->
            state.copy(
                isFavorite = isFavorite,
                scaledIngredients = state.recipe
                    ?.scaleIngredients(state.currentPortions)
                    .orEmpty()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RecipeDetailsUiState()
        )

    init {
        loadRecipe()
    }

/*    fun initializeWithRecipe(recipe: RecipeUiModel) {
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
    }*/

    fun loadRecipe() {
        viewModelScope.launch {
            _baseState.update {
                it.copy(isLoading = true, error = null)
            }

            val recipe = repository.getRecipeById(recipeId)?.toUiModel()

            if (recipe == null) {
                _baseState.update {
                    it.copy(
                        isLoading = false,
                        error = "Рецепт с ID $recipeId не найден"
                    )
                }
                return@launch
            }

            _baseState.update {
                it.copy(
                    recipe = recipe,
                    currentPortions = recipe.servings,
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            favoriteDataStoreManager.toggleFavorite(recipeId)
        }
    }

    fun updatePortions(count: Int) {
        _baseState.update { state ->
            state.copy(
                currentPortions = count.coerceIn(
                    MIN_PORTIONS,
                    MAX_PORTIONS
                )
            )
        }
    }

    companion object {
        const val MIN_PORTIONS = 1
        const val MAX_PORTIONS = 12

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = requireNotNull(
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                ) {
                    "Application должен быть доступен в CreationExtras"
                }

                RecipeDetailsViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    repository = RecipesRepositoryStub(),
                    favoriteDataStoreManager = FavoriteDataStoreManager(application)
                )
            }
        }
    }
}

private fun com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
        .scaleIngredients(portions: Int): List<IngredientUiModel> {
    if (servings <= 0 || portions <= 0) return ingredients

    val multiplier = portions.toDouble() / servings

    return ingredients.map { ingredient ->
        ingredient.copy(
            amount = ingredient.amount?.let { amount ->
                (amount * multiplier * 100).roundToInt() / 100.0
            }
        )
    }
}
