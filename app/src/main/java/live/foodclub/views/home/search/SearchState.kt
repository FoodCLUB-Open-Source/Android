package live.foodclub.views.home.search

import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.domain.models.profile.SimpleUserModel

data class SearchState(
    val userList: List<SimpleUserModel>,
    val postList: List<VideoModel>
) {
    //test data
    // userList and postList will be changed to empty list listOf()
    // when the back-end is fixed
    companion object {
        fun default() = SearchState(
            userList = listOf(
                SimpleUserModel(
                    1,
                    "user1",
                    null,
                    "user 1 fullname"
                ),
                SimpleUserModel(
                    2,
                    "user2a",
                    null,
                    "user 2 fullname"
                ),
                SimpleUserModel(
                    3,
                    "usera3",
                    null,
                    "user 3 fullname"
                ),
                SimpleUserModel(
                    1,
                    "userq 31",
                    null,
                    "user 1 fullname"
                ),
                SimpleUserModel(
                    2,
                    "user2aw2",
                    null,
                    "user 2 fullname"
                ),
                SimpleUserModel(
                    3,
                    "user3aa5",
                    null,
                    "user 3 fullname"
                ),
                SimpleUserModel(
                    1,
                    "user1a4",
                    null,
                    "user 1 fullname"
                ),
                SimpleUserModel(
                    2,
                    "user2a44",
                    null,
                    "user 2 fullname"
                ),
                SimpleUserModel(
                    3,
                    "user33a3",
                    null,
                    "user 3 fullname"
                ),
                SimpleUserModel(
                    2,
                    "user2q2",
                    null,
                    "user 2 fullname"
                ),
                SimpleUserModel(
                    3,
                    "user3q5",
                    null,
                    "user 3 fullname"
                ),
                SimpleUserModel(
                    1,
                    "user1q4",
                    null,
                    "user 1 fullname"
                ),
                SimpleUserModel(
                    2,
                    "user24w4",
                    null,
                    "user 2 fullname"
                ),
                SimpleUserModel(
                    3,
                    "user33w3",
                    null,
                    "user 3 fullname"
                )
            ),
            postList = listOf(
                VideoModel(
                    1,
                    SimpleUserModel(userId = 0,username = "", profilePictureUrl = null),
                    VideoStats(),
                    "",
                    description = ""
                ),
                VideoModel(
                    2,
                    SimpleUserModel(userId = 0,username = "", profilePictureUrl = null),
                    VideoStats(),
                    "",
                    description = ""
                ),
                VideoModel(
                    1,
                    SimpleUserModel(userId = 0,username = "", profilePictureUrl = null),
                    VideoStats(),
                    "",
                    description = ""
                ),
                VideoModel(
                    2,
                    SimpleUserModel(userId = 0,username = "", profilePictureUrl = null),
                    VideoStats(),
                    "",
                    description = ""
                ),
                VideoModel(
                    5,
                    SimpleUserModel(userId = 0,username = "", profilePictureUrl = null),
                    VideoStats(),
                    "",
                    description = ""
                ),
            )
        )
    }
}

