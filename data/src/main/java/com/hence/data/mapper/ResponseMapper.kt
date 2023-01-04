package com.hence.data.mapper

import com.hence.data.response.BroadCastResponse
import com.hence.data.response.CategoryResponse
import com.hence.domain.model.Broadcast
import com.hence.domain.model.Category
import com.hence.domain.model.DetailCategory

fun BroadCastResponse.toBroadCast(): List<Broadcast> {
    return this.broad.map {
        Broadcast(
            title = it.broad_title,
            isPrivate = it.is_password,
            categoryNum = it.broad_cate_no,
            broadNum = it.broad_no,
            userId = it.user_id,
            userNick = it.user_nick,
            userProfile = it.profile_img,
            broadThumb = it.broad_thumb,
            broadGrade = it.broad_grade,
            viewCount = it.visit_broad_type
        )
    }
}

fun CategoryResponse.toCategory(): List<Category> {
    return this.broad_category.map {
        Category(
            name = it.cate_name,
            number = it.cate_no,
            child = it.child.toDetailCategory()
        )
    }
}

fun List<CategoryResponse.BroadCategory.Child>.toDetailCategory(): List<DetailCategory> {
    return this.map {
        DetailCategory(
            name = it.cate_name,
            number = it.cate_no
        )
    }
}