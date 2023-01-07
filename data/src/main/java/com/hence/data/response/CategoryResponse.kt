package com.hence.data.response


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("broad_category")
    val broadcastCategory: List<BroadCategory>
) {
    data class BroadCategory(
        @SerializedName("cate_name")
        val categoryName: String, // 토크/캠방
        @SerializedName("cate_no")
        val categoryNumber: String, // 00130000
        @SerializedName("child")
        val subCategory: List<Child>
    ) {
        data class Child(
            @SerializedName("cate_name")
            val categoryName: String, // 종합게임
            @SerializedName("cate_no")
            val categoryNumber: String // 00040121
        )
    }
}