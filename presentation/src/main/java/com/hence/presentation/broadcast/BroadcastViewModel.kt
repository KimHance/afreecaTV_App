package com.hence.presentation.broadcast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hence.domain.model.Broadcast
import com.hence.domain.usecase.GetBroadcastListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BroadcastViewModel @Inject constructor(
    private val getBroadcastListUseCase: GetBroadcastListUseCase
) : ViewModel() {

    private val _broadcastList = MutableStateFlow<PagingData<Broadcast>>(PagingData.empty())
    val broadcastList = _broadcastList.asStateFlow()

    var selectedCategoryNumber = ""

    fun getBroadcastList(categoryNum: String) {
        viewModelScope.launch {
            getBroadcastListUseCase(categoryNum)
                .cachedIn(this)
                .collectLatest { data ->
                    _broadcastList.emit(data)
                }
        }
    }

    fun refreshBroadcastList() {
        viewModelScope.launch {
            _broadcastList.emit(PagingData.empty())
            getBroadcastList(selectedCategoryNumber)
        }
    }

    fun updateSelectedNumber(categoryNum: String) {
        selectedCategoryNumber = categoryNum
    }
}
