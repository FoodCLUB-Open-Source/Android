package android.kotlin.foodclub.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("profile_data")
data class ProfileModel(
    @PrimaryKey
    val userId: Long,
    val userName: String,
    val email: String,
    var profilePicture: String? = null,
    var createdAt: String? = null,
)
