package dev.kevinsalazar.tmdb.ui.states.scrollflags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import dev.kevinsalazar.tmdb.ui.states.FixedScrollFlagState

class ExitUntilCollapsedState(
    heightRange: IntRange,
    scrollOffset: Float = 0f
) : FixedScrollFlagState(heightRange) {

    private var _scrollOffset
        by mutableFloatStateOf(scrollOffset.coerceIn(0f, rangeDifference.toFloat()))

    private var _consumed: Float = 0f

    override val consumed: Float
        get() = _consumed

    override var scrollOffset: Float
        get() = _scrollOffset
        set(value) {
            if (scrollTopLimitReached) {
                val oldOffset = _scrollOffset
                _scrollOffset = value.coerceIn(0f, rangeDifference.toFloat())
                _consumed = oldOffset - _scrollOffset
            } else {
                _consumed = 0f
            }
        }

    companion object {
        val Saver = run {

            val minHeightKey = "MinHeight"
            val maxHeightKey = "MaxHeight"
            val scrollOffsetKey = "ScrollOffset"

            mapSaver(
                save = {
                    mapOf(
                        minHeightKey to it.minHeight,
                        maxHeightKey to it.maxHeight,
                        scrollOffsetKey to it.scrollOffset
                    )
                },
                restore = {
                    ExitUntilCollapsedState(
                        heightRange = (it[minHeightKey] as Int)..(it[maxHeightKey] as Int),
                        scrollOffset = it[scrollOffsetKey] as Float,
                    )
                }
            )
        }
    }
}
