package android.kotlin.foodclub.viewModels.home.create

import android.content.Context
import android.kotlin.foodclub.domain.models.others.TrimmedVideo
import android.kotlin.foodclub.utils.helpers.ffmpeg.VideoMerger
import android.kotlin.foodclub.views.home.createRecipe.TrimmerState
import android.net.Uri
import androidx.annotation.OptIn
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrimmerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val player: ExoPlayer
): ViewModel(), TrimmerEvents {
    private val videoUris = savedStateHandle.getStateFlow("videoUris", emptyMap<Int, Uri>())
    private var onVideoCreate = {}

    private val _state = MutableStateFlow(TrimmerState.default(player))
    val state: StateFlow<TrimmerState>
        get() = _state

    init {
        player.addListener(
            object : Player.Listener {
                override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                    updateVideosStartTimes()
                }
            }
        )
        player.prepare()

        addVideoUri(Uri.parse("https://kretu.sts3.pl/test/IMG_6105.MP4"))
        addVideoUri(Uri.parse("https://kretu.sts3.pl/test/IMG_6106.MP4"))
        addVideoUri(Uri.parse("https://kretu.sts3.pl/test/IMG_6107.MP4"))
    }

    fun setOnVideoCreateFunction(onVideoCreate: () -> Unit = {}) {
        this.onVideoCreate = onVideoCreate
    }

    private fun updateVideosStartTimes() {
        if(_state.value.videoObjects.any { !it.durationSet }) return

        viewModelScope.launch {
            var startTime = 0L
            for(id in 0..(player.mediaItemCount-2)) {
                val currentDuration = player.currentTimeline.getWindow(
                    id, Timeline.Window()
                ).durationMs
                val videoObject = _state.value.videoObjects.getOrNull(id + 1)

                startTime += currentDuration
                videoObject?.startTime = startTime
            }
        }

    }

    @OptIn(UnstableApi::class)
    override fun createVideo(context: Context) {
        _state.update { it.copy(isLoading = true) }
        player.pause()
        _state.value.videoObjects.forEach { it.saveVideo(context) { onVideoSaveListener(context) } }
    }

    private fun onVideoSaveListener(context: Context) {
        if (_state.value.videoObjects.any { it.savedFilePath == null }) return

        // String containing url of saved video. We will be using it when creating the post.
        val url = VideoMerger().mergeVideos(
            context, _state.value.videoObjects.map { it.savedFilePath!! }
        )
        onVideoCreate()
        _state.update { it.copy(isLoading = false) }
    }

    override fun navigate(time: Long) {
        val map = _state.value.videoObjects.filter {
            it.startTime <= time && it.startTime + it.duration > time
        }
        val index = if(map.isNotEmpty()) map[0].id else player.mediaItemCount - 1
        player.seekTo(index, time - (map.getOrNull(0)?.startTime ?: 0))
    }

    private fun addVideoUri(uri: Uri) {
        val index = videoUris.value.size
        val newMap = videoUris.value.toMutableMap()
        newMap[index] = uri
        savedStateHandle["videoUris"] = newMap

        TrimmedVideo(player, index, uri) { addToList(it) }
    }

    private fun addToList(videoObject: TrimmedVideo) {
        val newObjectList = _state.value.videoObjects.toMutableList()
        newObjectList.add(videoObject.id, videoObject)
        _state.update { it.copy(videoObjects = newObjectList) }
    }

    override fun togglePlay() {
        if(player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}