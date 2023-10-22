package android.kotlin.foodclub

import android.kotlin.foodclub.network.retrofit.responses.stories.RetrieveUserFriendsStoriesResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun createDummyResponse() {
        // Given
        val json = """
    {
        "stories": [
            {
                "user_id": 3,
                "profile_picture": "empty",
                "username": "User3",
                "stories": [
                    {
                        "story_id": "string",
                        "thumbnail_url": "https link",
                        "video_url": "https link"
                    }
                ]
            },
            {
                "user_id": 3,
                "profile_picture": "empty",
                "username": "User3",
                "stories": [
                    {
                        "story_id": "string",
                        "thumbnail_url": "https link",
                        "video_url": "https link"
                    }
                ]
            }
        ]
    }
    """

        val gson = Gson()
        val response = gson.fromJson(json, RetrieveUserFriendsStoriesResponse::class.java)

        // Then (perform specific assertions)
        assertEquals(3, response.stories[0].userId)
        assertEquals("empty", response.stories[0].profilePicture)
        assertEquals("User3", response.stories[0].username)
        // Add more assertions as needed for other fields and objects

        println("Response: $response testing")
    }
}