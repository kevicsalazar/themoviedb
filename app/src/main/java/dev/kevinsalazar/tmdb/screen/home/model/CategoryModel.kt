package dev.kevinsalazar.tmdb.screen.home.model

import androidx.annotation.StringRes

data class CategoryModel(
    @StringRes val categoryResourceId: Int,
    val category: String
)
