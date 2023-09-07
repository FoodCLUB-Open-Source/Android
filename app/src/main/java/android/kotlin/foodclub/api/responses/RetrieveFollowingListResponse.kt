package android.kotlin.foodclub.api.responses

import android.kotlin.foodclub.data.models.FollowingUserModel

data class RetrieveFollowingListResponse(
    val data: List<FollowingUserModel> = listOf()
)
