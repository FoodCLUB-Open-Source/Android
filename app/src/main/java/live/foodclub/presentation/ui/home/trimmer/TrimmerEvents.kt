package live.foodclub.presentation.ui.home.trimmer

import android.content.Context

interface TrimmerEvents {
    fun togglePlay()
    fun navigate(time: Long)
    fun createVideo(context: Context)
    fun resetState()
}