package com.hence.data.response


import kotlinx.serialization.Serializable

@Serializable
data class CategoryList(
    val broad_category: List<BroadCategory>
) {
    @Serializable
    data class BroadCategory(
        val cate_name: String, // 토크/캠방
        val cate_no: String, // 00130000
        val child: List<Child>?
    ) {
        @Serializable
        data class Child(
            val cate_name: String, // 종합게임
            val cate_no: String // 00040121
        )
    }
}