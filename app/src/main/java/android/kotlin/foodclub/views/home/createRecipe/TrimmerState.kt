package android.kotlin.foodclub.views.home.createRecipe

import android.kotlin.foodclub.domain.models.others.TrimmedVideo
import androidx.media3.exoplayer.ExoPlayer

data class TrimmerState(
    val videoObjects: List<TrimmedVideo>,
    val player: ExoPlayer,
    val isLoading: Boolean
) {
    companion object {
        fun default(player: ExoPlayer) = TrimmerState(
            videoObjects = listOf(),
            player = player,
            isLoading = false
        )
    }
}