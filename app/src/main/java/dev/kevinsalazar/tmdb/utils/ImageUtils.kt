package dev.kevinsalazar.tmdb.utils

import android.content.Context
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.kevinsalazar.tmdb.utils.Constants.ONE

fun getAsyncImageModel(data: Any?, context: Context): ImageRequest {
    return ImageRequest.Builder(context)
        .data(data = data)
        .crossfade(ONE)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()
}
