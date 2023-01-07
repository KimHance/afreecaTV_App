package com.hence.presentation.category

import com.hence.domain.model.Category

sealed class CategoryUiState {
    data class Success(val data: List<Category>) : CategoryUiState()

    object Loading : CategoryUiState()
    object Error : CategoryUiState()
}
