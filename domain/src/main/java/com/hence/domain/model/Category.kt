package com.hence.domain.model

data class Category(
    val name: String,
    val number: String,
    val child: List<DetailCategory>
)

data class DetailCategory(
    val name: String,
    val number: String
)

enum class CategoryType(val categoryName: String) {
    TALK(categoryName = "토크/캠방"),
    EAT(categoryName = "먹방/쿡방"),
    GAME(categoryName = "게임")
}