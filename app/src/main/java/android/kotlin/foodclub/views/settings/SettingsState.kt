package android.kotlin.foodclub.views.settings

import android.kotlin.foodclub.localdatasource.room.entity.UserDetailsModel
import android.kotlin.foodclub.utils.helpers.StoreData

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

