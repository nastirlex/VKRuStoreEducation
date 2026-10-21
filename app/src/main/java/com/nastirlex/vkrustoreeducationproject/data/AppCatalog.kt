package com.nastirlex.vkrustoreeducationproject.data

import androidx.annotation.DrawableRes
import com.nastirlex.vkrustoreeducationproject.R

data class CatalogApp(
    val id: String,
    val name: String,
    val summary: String,
    val categoryLabel: String,
    val category: Category,
    @param:DrawableRes val iconRes: Int,
    val developer: String,
) {
    fun details() = AppDetails(
        id = id,
        name = name,
        developer = developer,
        category = category,
        ageRating = 12,
        size = 64f,
        iconRes = iconRes,
        screenshotResList = listOf(R.drawable.screenshot_0, R.drawable.screenshot_1, R.drawable.screenshot_2),
        description = "$summary. Это учебная карточка приложения $name. " +
            "Все данные и иллюстрации хранятся в проекте и доступны без интернета. " +
            "Изучите описание и пролистайте витрину, чтобы найти другие приложения.",
    )
}

object AppCatalog {
    const val APPS_PER_PAGE = 3
    const val PAGE_COUNT = 3

    val apps = listOf(
        CatalogApp("vk", "ВКонтакте", "Общение, друзья и сообщества", "Социальные сети", Category.SOCIAL, R.drawable.app_icon_0, "VK"),
        CatalogApp("mail", "Почта", "Письма и важные события в одном месте", "Продуктивность", Category.PRODUCTIVITY, R.drawable.app_icon_1, "VK"),
        CatalogApp("music", "Музыка", "Любимые треки и новые открытия", "Музыка", Category.MUSIC, R.drawable.app_icon_2, "VK"),
        CatalogApp("messenger", "Мессенджер", "Всегда на связи с близкими", "Общение", Category.SOCIAL, R.drawable.app_icon_3, "VK"),
        CatalogApp("books", "Книги", "Истории, которые остаются с вами", "Книги", Category.BOOKS, R.drawable.app_icon_4, "Учебная студия"),
        CatalogApp("maps", "Карты", "Новые места и удобные маршруты", "Путешествия", Category.MAPS, R.drawable.app_icon_5, "Учебная студия"),
        CatalogApp("learn", "Знания", "Небольшие уроки каждый день", "Образование", Category.EDUCATION, R.drawable.app_icon_6, "Учебная студия"),
        CatalogApp("tasks", "Мои дела", "Планы, задачи и полезные привычки", "Продуктивность", Category.PRODUCTIVITY, R.drawable.app_icon_7, "Учебная студия"),
        CatalogApp("health", "Здоровье", "Время позаботиться о себе", "Здоровье", Category.HEALTH, R.drawable.app_icon_8, "Учебная студия"),
    )
    val showcasePages = apps.chunked(APPS_PER_PAGE)
    fun find(id: String?): CatalogApp? = apps.find { it.id == id }
}
