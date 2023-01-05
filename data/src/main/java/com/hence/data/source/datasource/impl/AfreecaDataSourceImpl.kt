package com.hence.data.source.datasource.impl

import com.hence.data.mapper.toBroadCast
import com.hence.data.mapper.toCategory
import com.hence.data.service.AfreecaService
import com.hence.data.source.datasource.AfreecaDataSource
import com.hence.domain.model.Broadcast
import com.hence.domain.model.Category
import javax.inject.Inject

class AfreecaDataSourceImpl @Inject constructor(
    private val afreecaService: AfreecaService
) : AfreecaDataSource {

    override suspend fun getBroadCastList(
        selectValue: String,
        page: Int
    ): List<Broadcast> {
        val response = afreecaService.getBroadCastList(
            selectValue = selectValue,
            page = page
        )
        return if (response.isSuccessful) {
            response.body()?.broad?.toBroadCast() ?: emptyList()
        } else {
            throw Error(response.message())
        }
    }

    override suspend fun getCategoryList(): List<Category> {
        val response = afreecaService.getCategoryList()
        return if (response.isSuccessful) {
            response.body()?.toCategory() ?: emptyList()
        } else {
            throw Error(response.message())
        }
    }
}