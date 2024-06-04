package live.foodclub.presentation.ui.settings

import live.foodclub.localdatasource.room.entity.UserDetailsModel

interface SettingsEvents {
    fun logout()
    fun changePassword(oldPassword: String, newPassword: String)
    fun updateUserDetails(userId: Long, user: UserDetailsModel)
}