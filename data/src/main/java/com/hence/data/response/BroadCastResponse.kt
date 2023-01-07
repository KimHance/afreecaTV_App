package com.hence.data.response


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class BroadCastResponse(
    @SerializedName("broad")
    val broadcastList: List<Broad>,
    @SerializedName("page_no")
    val pageNumber: Int
) {
    @Serializable
    data class Broad(
        @SerializedName("broad_bps") val bps: String, // 8000
        @SerializedName("broad_cate_no") val CategoryNumber: String, // 00040019
        @SerializedName("broad_grade") val grade: String, // 0
        @SerializedName("broad_no") val number: String, // 244360996
        @SerializedName("broad_resolution") val resolution: String, // 1920x1080
        @SerializedName("broad_start") val start: String, // 2023-01-04 16:15:56
        @SerializedName("broad_thumb") val thumb: String, // //liveimg.afreecatv.com/m/244360996
        @SerializedName("broad_title") val title: String, // 이상호x고스트 레전드원딜등장 오늘 상윤x마타 잡고 아최봇 인증하겠습니다
        @SerializedName("is_password") val isPassword: String, // 0
        @SerializedName("profile_img") val profile: String, // //profile.img.afreecatv.com/LOGO/ls/lshooooo/lshooooo.jpg?dummy=8609480503
        @SerializedName("total_view_cnt") val totalCount: String, // 26466
        @SerializedName("user_id") val userId: String, // lshooooo
        @SerializedName("user_nick") val userNick: String, // BJ이상호
        @SerializedName("visit_broad_type") val visitType: String // 0
    )
}