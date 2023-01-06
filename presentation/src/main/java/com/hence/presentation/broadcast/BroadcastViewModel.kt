package com.hence.presentation.broadcast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hence.domain.model.Broadcast
import com.hence.domain.model.CategoryType
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

    private val _talkBroadcastList = MutableStateFlow<PagingData<Broadcast>>(PagingData.empty())
    val talkBroadcastList = _talkBroadcastList.asStateFlow()

    private val _eatBroadcastList = MutableStateFlow<PagingData<Broadcast>>(PagingData.empty())
    val eatBroadcastList = _eatBroadcastList.asStateFlow()

    private val _gameBroadcastList = MutableStateFlow<PagingData<Broadcast>>(PagingData.empty())
    val gameBroadcastList = _gameBroadcastList.asStateFlow()

    fun getBroadcastList(categoryNum: String) {
        viewModelScope.launch {
            getBroadcastListUseCase(categoryNum)
                .cachedIn(this)
                .collectLatest { data ->
                    emitData(CategoryType.getCategory(categoryNum), data)
                }
        }
    }

    private suspend fun emitData(categoryType: CategoryType, data: PagingData<Broadcast>) {
        when (categoryType) {
            CategoryType.TALK -> {
                _talkBroadcastList.emit(data)
            }
            CategoryType.GAME -> {
                _gameBroadcastList.emit(data)
            }
            CategoryType.EAT -> {
                _eatBroadcastList.emit(data)
            }
            CategoryType.UNDEFINED -> {
                // TODO error
            }
        }
    }
}