package live.foodclub.domain.models.snaps

import live.foodclub.domain.enums.Reactions
import live.foodclub.domain.models.profile.SimpleUserModel
data class SnapModel(
    val snapAuthor: SimpleUserModel,
    val userReactions:Map<SimpleUserModel, Reactions>,
    val isSaved:Boolean,
    val imageUrl:String,
    val dateTime:String
)