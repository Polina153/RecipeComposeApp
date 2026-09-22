package com.example.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.Constants.PARAM_CATEGORY_ID
import com.example.recipecomposeapp.Constants.PARAM_CATEGORY_IMAGE_URL
import com.example.recipecomposeapp.Constants.PARAM_CATEGORY_TITLE
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder

class RecipesViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        val categoryId = state.get<Int>(PARAM_CATEGORY_ID) ?: -1
        val categoryTitle = URLDecoder.decode(
            state.get<String>(PARAM_CATEGORY_TITLE) ?: "Неизвестная категория",
            "UTF-8"
        )
        val categoryImageUrl =
            URLDecoder.decode(state.get<String>(PARAM_CATEGORY_IMAGE_URL) ?: "", "UTF-8")

        _uiState.update { currentState ->
            currentState.copy(
                category = categoryTitle,
                imageUrl = categoryImageUrl
            )
        }

        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    error = null
                )
            }
            try {
                val recipes: List<RecipeUiModel> =
                    RecipesRepositoryStub.getRecipesByCategoryId(categoryId).map { it.toUiModel() }

                _uiState.update { state ->
                    state.copy(isLoading = false, recipesList = recipes)
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        recipesList = emptyList(),
                        error = e.localizedMessage ?: "Неизвестная ошибка"
                    )
                }
            }
        }
    }
}
