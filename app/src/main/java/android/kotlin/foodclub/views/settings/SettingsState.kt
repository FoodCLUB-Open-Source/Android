package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.domain.models.profile.UserDetailsModel

data class SettingsState(
    val error: String,
    val title: String,
    val user : UserDetailsModel?
) {
    companion object {
        fun default() = SettingsState(
            error = "",
            title = "",
            user = null
        )
    }
}
