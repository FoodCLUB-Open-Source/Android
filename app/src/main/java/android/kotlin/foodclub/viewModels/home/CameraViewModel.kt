package android.kotlin.foodclub.viewModels.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {
    var minutes = mutableIntStateOf(0)
        private set
    var seconds = mutableIntStateOf(0)
        private set
    var milliseconds = mutableIntStateOf(0)
        private set

    var totalMilliseconds = mutableIntStateOf(0)
        private set

    private var isCounting = false

    private var prevMinutes = mutableListOf<Int>()
    private var prevSeconds = mutableListOf<Int>()
    private var prevMilliseconds = mutableListOf<Int>()
    private var prevTotalMilliseconds = mutableListOf<Int>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                if (isCounting) {
                    totalMilliseconds.value++
                    if (milliseconds.value < 100) {
                        milliseconds.value++
                    }
                    else {
                        if (seconds.value < 60) {
                            seconds.value++
                            milliseconds.value = 0
                        } else {
                            minutes.value++
                            seconds.value = 0
                            milliseconds.value = 0
                        }
                    }
                }
                delay(8)
            }
        }
    }

    fun onEvent(event: StopWatchEvent) {
        when (event) {
            StopWatchEvent.onReset -> {
                isCounting = false
                minutes.value = 0
                seconds.value = 0
                milliseconds.value = 0
                totalMilliseconds.value = 0
                prevMinutes.clear()
                prevSeconds.clear()
                prevMilliseconds.clear()
                prevTotalMilliseconds.clear()
            }

            StopWatchEvent.onStart -> {
                prevMinutes.add(minutes.value)
                prevSeconds.add(seconds.value)
                prevMilliseconds.add(milliseconds.value)
                prevTotalMilliseconds.add(totalMilliseconds.value)
                isCounting = true
            }

            StopWatchEvent.onStop -> {
                isCounting = false
            }

            StopWatchEvent.onRecall -> {
                isCounting = false
                minutes.value = prevMinutes[prevMinutes.lastIndex]
                prevMinutes.removeAt(prevMinutes.lastIndex)
                seconds.value = prevSeconds[prevSeconds.lastIndex]
                prevSeconds.removeAt(prevSeconds.lastIndex)
                milliseconds.value = prevMilliseconds[prevMilliseconds.lastIndex]
                prevMilliseconds.removeAt(prevMilliseconds.lastIndex)
                totalMilliseconds.value = prevTotalMilliseconds[prevTotalMilliseconds.lastIndex]
                prevTotalMilliseconds.removeAt(prevTotalMilliseconds.lastIndex)
            }
        }
    }
}

sealed interface StopWatchEvent {
    object onStart : StopWatchEvent
    object onStop : StopWatchEvent
    object onReset : StopWatchEvent
    object onRecall : StopWatchEvent
}
