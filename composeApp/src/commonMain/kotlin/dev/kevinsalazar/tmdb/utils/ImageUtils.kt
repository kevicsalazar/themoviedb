package dev.kevinsalazar.tmdb.utils

import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kevinsalazar.tmdb.utils.Constants.ONE

fun getAsyncImageModel(data: Any?, context: PlatformContext): ImageRequest {
    return ImageRequest.Builder(context)
        .data(data = data)
        .crossfade(ONE)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()
}
