package android.kotlin.foodclub.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("profile_data")
data class OfflineProfileModel(
    @PrimaryKey
    val userId: Long,
    val userName: String,
    val email: String,
    var profilePicture: String? = null,
    val totalUserFollowers: Int? = null,
    val totalUserFollowing: Int? = null,
    )
