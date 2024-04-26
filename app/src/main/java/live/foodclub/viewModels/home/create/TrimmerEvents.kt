package live.foodclub.viewModels.home.create

import android.content.Context

interface TrimmerEvents {
    fun togglePlay()
    fun navigate(time: Long)
    fun createVideo(context: Context)
}