package android.kotlin.foodclub.viewModels.home.create

import android.content.Context
import android.kotlin.foodclub.domain.models.others.TrimmedVideo
import android.kotlin.foodclub.utils.helpers.VideoMerger
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
    val player: ExoPlayer
): ViewModel() {
    private val videoUris = savedStateHandle.getStateFlow("videoUris", emptyMap<Int, Uri>())

    private val _videoObjects = MutableStateFlow(listOf<TrimmedVideo>())
    val videoObjects: StateFlow<List<TrimmedVideo>> get() = _videoObjects

    init {
        player.addListener(
            object : Player.Listener {
                override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                    updateVideosStartTimes()
                }
            }
        )
        player.prepare()

        addVideoUri(Uri.parse("https://kretu.sts3.pl/foodclub_videos/trimmer.mp4"))
        addVideoUri(Uri.parse("https://kretu.sts3.pl/foodclub_videos/onboarding.mp4"))
//        addVideoUri(Uri.parse("https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4"))
    }


    private fun updateVideosStartTimes() {
        if(_videoObjects.value.any { !it.durationSet }) return

        viewModelScope.launch {
            var startTime = 0L
            for(id in 0..(player.mediaItemCount-2)) {
                val currentDuration = player.currentTimeline.getWindow(id, Timeline.Window()).durationMs
                val videoObject = _videoObjects.value.getOrNull(id + 1)

                startTime += currentDuration
                videoObject?.startTime = startTime
            }
        }

    }

    @OptIn(UnstableApi::class)
    fun createVideo(context: Context) {
        _videoObjects.value.forEach { it.saveVideo(context) { onVideoSaveListener(context) } }
    }

    private fun onVideoSaveListener(context: Context) {
        if (_videoObjects.value.any { it.savedFilePath == null }) return
        VideoMerger().mergeVideos(context, _videoObjects.value.map { it.savedFilePath!! })
    }

    fun navigate(time: Long) {
        val map = _videoObjects.value.filter { it.startTime <= time && it.startTime + it.duration > time }
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
        val newObjectList = _videoObjects.value.toMutableList()
        newObjectList.add(videoObject.id, videoObject)
        _videoObjects.update { newObjectList }
    }

    fun togglePlay() {
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