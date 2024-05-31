package live.foodclub.views.settings

import live.foodclub.domain.models.profile.UserProfile
import live.foodclub.localdatasource.room.entity.UserDetailsModel
import live.foodclub.utils.helpers.StoreData

data class SettingsState(
    val error: String,
    val title: String,
    val user : UserDetailsModel?,
    val userProfile: UserProfile,
    val dataStore: StoreData?
) {
    companion object {
        fun default() = SettingsState(
            error = "",
            title = "",
            user = null,
            userProfile = UserProfile.default(),
            dataStore = null
        )
    }
}

