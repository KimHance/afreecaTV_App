package com.hence.data.service

import com.hence.data.response.BroadCastResponse
import com.hence.data.response.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AfreecaService {

    @GET("broad/list")
    suspend fun getBroadCastList(
        @Query("client_id") clientId: String = CLIENT_ID,
        @Query("select_key") selectKey: String = "cate",
        @Query("select_value") selectValue: String = "",
        @Query("page_no") page: Int = STARTING_PAGE
    ): Response<BroadCastResponse>

    @GET("broad/category/list")
    suspend fun getCategoryList(
        @Query("client_id") clientId: String = CLIENT_ID
    ): Response<CategoryResponse>

    companion object {
        const val CLIENT_ID = "af_mobilelab_dev_e0f147f6c034776add2142b425e81777"
        const val STARTING_PAGE = 1
    }
}