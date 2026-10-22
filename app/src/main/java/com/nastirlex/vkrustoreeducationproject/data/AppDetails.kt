package com.nastirlex.vkrustoreeducationproject.data

data class AppDetails(
    val id: String,
    val name: String,
    val developer: String,
    val category: Category,
    val ageRating: Int,
    val size: Float,
    val iconRes: Int,
    val screenshotResList: List<Int>,
    val description: String,
)
