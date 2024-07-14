package dev.kevinsalazar.data.utils

fun String.constructMediumImageUrl(): String {
    return "${Constants.MEDIUM_IMAGE_URL}$this"
}

fun String.constructOriginalImageUrl(): String {
    return "${Constants.ORIGINAL_IMAGE_URL}$this"
}
