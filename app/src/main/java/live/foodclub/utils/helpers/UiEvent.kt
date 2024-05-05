package live.foodclub.utils.helpers

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
}