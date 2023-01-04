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
