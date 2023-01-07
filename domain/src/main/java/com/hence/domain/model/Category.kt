package com.hence.domain.model

import java.io.Serializable

data class Category(
    val name: String = "",
    val number: String = "",
    val child: List<DetailCategory> = emptyList(),
) : Serializable

data class DetailCategory(
    val name: String,
    val number: String,
) : Serializable

enum class CategoryType(val categoryNumber: String) {
    UNDEFINED(categoryNumber = "00000000"),
    TALK(categoryNumber = "00130000"),
    EAT(categoryNumber = "00330000"),
    GAME(categoryNumber = "00040000");

    companion object {
        fun getCategory(value: String) = values().find { value == it.categoryNumber } ?: UNDEFINED
    }
}
