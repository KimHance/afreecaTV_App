package com.hence.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hence.domain.usecase.GetCategoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase
) : ViewModel() {

    private val _categoryList = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val categoryList = _categoryList.asStateFlow()

    fun getCategoryList() {
        viewModelScope.launch {
            getCategoryListUseCase()
                .catch {
                    _categoryList.value = CategoryUiState.Error
                }
                .collect { list ->
                    _categoryList.value = CategoryUiState.Success(list)
                }
        }
    }
}