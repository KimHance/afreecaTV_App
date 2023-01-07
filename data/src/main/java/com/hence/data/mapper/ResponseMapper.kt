package com.hence.data.mapper

import com.hence.data.response.BroadCastResponse
import com.hence.data.response.CategoryResponse
import com.hence.domain.model.Broadcast
import com.hence.domain.model.Category
import com.hence.domain.model.DetailCategory

fun List<BroadCastResponse.Broad>.toBroadCast(): List<Broadcast> {
    return this.map {
        Broadcast(
            title = it.title,
            isPrivate = it.isPassword,
            categoryNum = it.CategoryNumber,
            broadNum = it.number,
            userId = it.userId,
            userNick = it.userNick,
            userProfile = it.profile,
            broadThumb = it.thumb,
            broadGrade = it.grade,
            viewCount = it.totalCount
        )
    }
}

fun CategoryResponse.toCategory(): List<Category> {
    return this.broadcastCategory.map {
        Category(
            name = it.categoryName,
            number = it.categoryNumber,
            child = it.subCategory.toDetailCategory()
        )
    }
}

fun List<CategoryResponse.BroadCategory.Child>.toDetailCategory(): List<DetailCategory> {
    return this.map {
        DetailCategory(
            name = it.categoryName,
            number = it.categoryNumber
        )
    }
}