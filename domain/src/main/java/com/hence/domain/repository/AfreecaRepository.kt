package com.hence.domain.repository


import androidx.paging.PagingData
import com.hence.domain.model.Broadcast
import com.hence.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface AfreecaRepository {
    fun getBroadcastList(selectKey: String, selectValue: String): Flow<PagingData<Broadcast>>
    fun getCategoryList(): Flow<List<Category>>
}