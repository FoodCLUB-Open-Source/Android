package android.kotlin.foodclub.localdatasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class UserDetailsModel(
    @PrimaryKey(autoGenerate = false)
    val id: Long,

    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String? = null,

    @ColumnInfo(name = "profile_picture")
    var profilePicture: String? = null,

    @ColumnInfo(name = "userBio")
    val userBio: String? = null,

    @ColumnInfo(name = "gender")
    val gender: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: String? = null,

    @ColumnInfo(name = "dietary_prefs")
    val dietaryPrefs: List<String>? = null,

    @ColumnInfo(name = "country")
    val country: String? = null,

    @ColumnInfo(name = "shipping_address")
    val shippingAddress: String? = null,

    @ColumnInfo(name = "full_name")
    val fullName: String? = null
)