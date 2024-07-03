package dev.kevinsalazar.tmdb.screen.player

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), PlayerContract {

    private val title: String get() = checkNotNull(savedStateHandle["title"])
    private val videoUrl: String get() = checkNotNull(savedStateHandle["videoUrl"])

    private val _state = MutableStateFlow(PlayerContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PlayerContract.Effect>()
    override val effect = _effect.asSharedFlow()

    override fun onEvent(event: PlayerContract.Event) {
        when (event) {
            PlayerContract.Event.LoadVideo -> loadVideo()
            PlayerContract.Event.OnBackPressed -> onBackPressed()
        }
    }

    private fun loadVideo() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    title = title,
                    videoUrl = Uri.decode(videoUrl),
                    loading = false
                )
            }
        }
    }

    private fun onBackPressed() {
        viewModelScope.launch {
            _effect.emit(PlayerContract.Effect.OnBackPressed)
        }
    }
}
