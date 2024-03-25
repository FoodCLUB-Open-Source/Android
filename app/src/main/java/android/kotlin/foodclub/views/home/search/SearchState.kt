package android.kotlin.foodclub.views.home.search

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel

data class SearchState(
    val userList: List<SimpleUserModel>,
    val postList: List<VideoModel>
) {
    companion object {
        fun default() = SearchState(
            userList = listOf(),
            postList = listOf()
        )
    }
}

