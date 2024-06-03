package live.foodclub.presentation.ui.settings

import live.foodclub.localdatasource.room.entity.UserDetailsModel
import live.foodclub.utils.helpers.StoreData

data class SettingsState(
    val error: String,
    val title: String,
    val user : UserDetailsModel?,
    val dataStore: StoreData?
) {
    companion object {
        fun default() = SettingsState(
            error = "",
            title = "",
            user = null,
            dataStore = null
        )
    }
}

