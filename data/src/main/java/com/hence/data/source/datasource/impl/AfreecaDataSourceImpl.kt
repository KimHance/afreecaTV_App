package com.hence.data.source.datasource.impl

import com.hence.data.response.BroadCastList
import com.hence.data.response.CategoryList.BroadCategory
import com.hence.data.service.AfreecaService
import com.hence.data.source.datasource.AfreecaDataSource
import javax.inject.Inject

class AfreecaDataSourceImpl @Inject constructor(
    private val afreecaService: AfreecaService
) : AfreecaDataSource {

    override suspend fun getBroadCastList(
        selectKey: String,
        selectValue: String,
        page: Int
    ): List<BroadCastList.Broad> {
        val response = afreecaService.getBroadCastList(
            selectKey = selectKey,
            selectValue = selectValue,
            page = page
        )
        return if (response.isSuccessful) {
            response.body()!!.broad
        } else {
            throw Error(response.message())
        }
    }

    override suspend fun getCategoryList(): List<BroadCategory> {
        val response = afreecaService.getCategoryList()
        return if (response.isSuccessful) {
            response.body()!!.broad_category
        } else {
            throw  Error(response.message())
        }
    }
}