package android.kotlin.foodclub.views.home.createRecipe

import android.kotlin.foodclub.domain.models.others.TrimmedVideo
import android.net.Uri
import androidx.media3.exoplayer.ExoPlayer

data class TrimmerState(
    val videoObjects: List<TrimmedVideo>,
    var passedVideoUris: List<Uri>?,
    val player: ExoPlayer,
    val isLoading: Boolean
) {

    companion object {
        fun default(player: ExoPlayer) = TrimmerState(
            videoObjects = listOf(),
            passedVideoUris = emptyList(),
            player = player,
            isLoading = false
        )
    }
}