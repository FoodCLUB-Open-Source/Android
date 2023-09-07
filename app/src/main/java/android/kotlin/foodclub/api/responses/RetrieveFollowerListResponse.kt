package android.kotlin.foodclub.api.responses

import android.kotlin.foodclub.data.models.FollowerUserModel

data class RetrieveFollowerListResponse(
    val data: List<FollowerUserModel> = listOf()
)
