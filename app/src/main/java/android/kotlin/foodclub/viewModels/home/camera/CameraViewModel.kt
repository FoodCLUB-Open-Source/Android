package android.kotlin.foodclub.viewModels.home.camera

import android.kotlin.foodclub.views.home.camera.CameraState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel(), CameraEvents {
    companion object {
        private val TAG = CameraViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(CameraState.default())
    val state: StateFlow<CameraState>
        get() = _state

    private var isCounting = false

    private var prevMinutes = mutableListOf<Int>()
    private var prevSeconds = mutableListOf<Int>()
    private var prevMilliseconds = mutableListOf<Int>()
    private var prevTotalMilliseconds = mutableListOf<Int>()

    private var timerDelay: Long = 8

    init {
        // TODO Not sure this is the best way to do this, either use a delay, Timer or CountDownTimer
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                if (isCounting) {
                    _state.update { it.copy(
                        totalMilliseconds = it.totalMilliseconds + 1
                    )}
                    if (state.value.milliseconds < 100) {
                        _state.update { it.copy(
                            milliseconds = it.milliseconds + 1
                        )}
                    }
                    else {
                        if (state.value.seconds < 60) {
                            _state.update { it.copy(
                                seconds = it.seconds + 1,
                                milliseconds = 0
                            )}
                        } else {
                            _state.update { it.copy(
                                minutes = it.minutes + 1,
                                seconds = 0,
                                milliseconds = 0
                            )}
                        }
                    }
                }
                delay(timerDelay)
            }
        }
    }

    override fun onEvent(event: StopWatchEvent) {
        when (event) {
            StopWatchEvent.onReset -> {
                isCounting = false
                _state.update {
                    it.copy(
                        minutes = 0,
                        seconds = 0,
                        milliseconds = 0,
                        totalMilliseconds = 0,
                    )
                }
                prevMinutes.clear()
                prevSeconds.clear()
                prevMilliseconds.clear()
                prevTotalMilliseconds.clear()
            }

            StopWatchEvent.onStart -> {
                prevMinutes.add(state.value.minutes)
                prevSeconds.add(state.value.seconds)
                prevMilliseconds.add(state.value.milliseconds)
                prevTotalMilliseconds.add(state.value.totalMilliseconds)
                isCounting = true
            }

            StopWatchEvent.onStop -> {
                isCounting = false
            }

            StopWatchEvent.onRecall -> {
                isCounting = false
                _state.update { it.copy(
                    minutes = prevMinutes[prevMinutes.lastIndex],
                    seconds = prevSeconds[prevSeconds.lastIndex],
                    milliseconds = prevMilliseconds[prevMilliseconds.lastIndex],
                    totalMilliseconds = prevTotalMilliseconds[prevTotalMilliseconds.lastIndex],
                )}
                prevMinutes.removeAt(prevMinutes.lastIndex)
                prevSeconds.removeAt(prevSeconds.lastIndex)
                prevMilliseconds.removeAt(prevMilliseconds.lastIndex)
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
