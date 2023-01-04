package com.hence.data.source.datasource

import com.hence.domain.model.Broadcast
import com.hence.domain.model.Category

interface AfreecaDataSource {
    suspend fun getBroadCastList(
        selectKey: String,
        selectValue: String,
        page: Int
    ): List<Broadcast>

    suspend fun getCategoryList(): List<Category>
}