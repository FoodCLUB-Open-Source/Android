package android.kotlin.foodclub.domain.models.snaps

import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
data class SnapModel(
    val snapAuthor: SimpleUserModel,
    val userReactions:Map<SimpleUserModel, Reactions>,
    val isSaved:Boolean,
    val imageUrl:String,
    val dateTime:String
)