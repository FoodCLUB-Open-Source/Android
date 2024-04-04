package android.kotlin.foodclub.localdatasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("profile_data")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = false)
    var userId: Long,
    val userName: String,
    @ColumnInfo(defaultValue = "undefined")
    val fullName: String,
    var profilePicture: String? = null,
    val totalUserFollowers: Int? = null,
    val totalUserFollowing: Int? = null,
    val totalUserLikes: Int? = null
    )
