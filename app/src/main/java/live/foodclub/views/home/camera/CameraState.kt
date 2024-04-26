package live.foodclub.views.home.camera

data class CameraState(
    val minutes: Int,
    val seconds: Int,
    val milliseconds: Int,
    val totalMilliseconds: Int,
) {
    companion object {
        fun default() = CameraState(
            minutes = 0,
            seconds = 0,
            milliseconds = 0,
            totalMilliseconds = 0,
        )
    }
}
