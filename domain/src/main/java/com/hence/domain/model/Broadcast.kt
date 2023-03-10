package com.hence.domain.model

import java.io.Serializable

data class Broadcast(
    val title: String,
    val isPrivate: String,
    val categoryNum: String,
    val broadNum: String,
    val userId: String,
    val userNick: String,
    val userProfile: String,
    val broadThumb: String,
    val broadGrade: String,
    val viewCount: String
) : Serializable
