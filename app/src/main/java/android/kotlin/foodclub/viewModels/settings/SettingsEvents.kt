package android.kotlin.foodclub.viewModels.settings

import android.kotlin.foodclub.localdatasource.room.entity.UserDetailsModel

interface SettingsEvents {
    fun logout()
    fun changePassword(oldPassword: String, newPassword: String)
    fun updateUserDetails(userId: Long, user: UserDetailsModel)
}