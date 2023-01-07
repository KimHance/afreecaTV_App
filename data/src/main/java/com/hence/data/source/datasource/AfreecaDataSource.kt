package com.hence.data.source.datasource

import com.hence.data.mapper.toBroadCast
import com.hence.data.mapper.toCategory
import com.hence.data.service.AfreecaService
import com.hence.domain.model.Broadcast
import com.hence.domain.model.Category
import javax.inject.Inject

class AfreecaDataSource @Inject constructor(
    private val afreecaService: AfreecaService
) {
    suspend fun getBroadCastList(
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
            throw Exception(response.message())
        }
    }

    suspend fun getCategoryList(): List<Category> {
        val response = afreecaService.getCategoryList()
        return if (response.isSuccessful) {
            response.body()?.toCategory() ?: emptyList()
        } else {
            throw Exception(response.message())
        }
    }
}