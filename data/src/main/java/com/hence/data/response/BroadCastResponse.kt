package com.hence.data.response


import kotlinx.serialization.Serializable

@Serializable
data class BroadCastResponse(
    val broad: List<Broad>,
    val page_no: Int
) {
    @Serializable
    data class Broad(
        val broad_bps: String, // 8000
        val broad_cate_no: String, // 00040019
        val broad_grade: String, // 0
        val broad_no: String, // 244360996
        val broad_resolution: String, // 1920x1080
        val broad_start: String, // 2023-01-04 16:15:56
        val broad_thumb: String, // //liveimg.afreecatv.com/m/244360996
        val broad_title: String, // 이상호x고스트 레전드원딜등장 오늘 상윤x마타 잡고 아최봇 인증하겠습니다
        val is_password: String, // 0
        val profile_img: String, // //profile.img.afreecatv.com/LOGO/ls/lshooooo/lshooooo.jpg?dummy=8609480503
        val total_view_cnt: String, // 26466
        val user_id: String, // lshooooo
        val user_nick: String, // BJ이상호
        val visit_broad_type: String // 0
    )
}