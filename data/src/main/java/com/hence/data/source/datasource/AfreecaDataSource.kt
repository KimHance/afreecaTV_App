package com.hence.data.source.datasource

import com.hence.data.response.BroadCastList
import com.hence.data.response.CategoryList.BroadCategory as BroadCategory

interface AfreecaDataSource {
    suspend fun getBroadCastList(
        selectKey: String,
        selectValue: String,
        page: Int
    ): List<BroadCastList.Broad>

    suspend fun getCategoryList(): List<BroadCategory>
}