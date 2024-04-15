package android.kotlin.foodclub.viewModels.home.trimmer

import android.content.Context

interface TrimmerEvents {
    fun togglePlay()
    fun navigate(time: Long)
    fun createVideo(context: Context)
    fun resetState()
}