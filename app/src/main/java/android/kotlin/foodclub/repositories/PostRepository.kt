package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.api.authentication.API
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.data.models.PostModel
import android.kotlin.foodclub.data.models.VideoModel
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.utils.helpers.ValueParser
import java.io.IOException

class PostRepository(
    private val api: API
) {
    private fun mapDtoToModel(dtoModel: PostModel): VideoModel {
        return VideoModel(
            videoId = dtoModel.id,
            authorDetails = dtoModel.username ?: "Marc",
            videoStats = VideoModel.VideoStats(
                dtoModel.likes ?: 15,
                0L,
                0L,
                0L,
                dtoModel.views ?: 100
            ),
            videoLink = dtoModel.videoUrl,
            description = dtoModel.description,
            thumbnailLink = dtoModel.thumbnailUrl
        )
    }
    suspend fun getPost(id: Long): Resource<VideoModel, DefaultErrorResponse> {
        val response = try {
            api.getPost(id)
        } catch (e: IOException) {
            return Resource.Error(
                "Cannot retrieve data. Check your internet connection and try again."
            )
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred.")
        }

        if(response.isSuccessful && response.body() != null && response.body()?.data != null
            && response.body()?.data?.isEmpty() == false){
            return Resource.Success(mapDtoToModel(response.body()!!.data[0]))
        }
        return Resource.Error("Unknown error occurred.")

    }

    suspend fun getHomepagePosts(
        userId: Long, pageSize: Int? = null, pageNo: Int? = null
    ): Resource<List<VideoModel>, DefaultErrorResponse> {
        val response = try {
            api.getHomepagePosts(userId, pageSize, pageNo)
        } catch (e: IOException) {
            return Resource.Error("Cannot retrieve data. Check your internet connection and try again.")
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred.")
        }

        if(response.isSuccessful && response.body() != null && response.body()?.posts != null
            && response.body()?.posts?.isEmpty() == false){
            return Resource.Success(response.body()!!.posts.map { mapDtoToModel(it) })
        }
        return Resource.Error(ValueParser.errorResponseToMessage(response))
    }

    suspend fun getWorldCategoryPosts(
        userId: Long, pageSize: Int? = null, pageNo: Int? = null
    ): Resource<List<VideoModel>, DefaultErrorResponse> {
        val response = try {
            api.getPostByWorldCategory(userId, pageSize, pageNo)
        } catch (e: IOException) {
            return Resource.Error(
                "Cannot retrieve data. Check your internet connection and try again."
            )
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred.")
        }

        if(response.isSuccessful && response.body() != null && response.body()?.posts != null
            && response.body()?.posts?.isEmpty() == false){
            return Resource.Success(response.body()!!.posts.map { mapDtoToModel(it) })
        }
        return Resource.Error("Unknown error occurred.")
    }

    suspend fun deletePost(id: Long): Resource<Boolean, DefaultErrorResponse> {
        val response = try {
            api.deletePost(id)
        } catch (e: IOException) {
            return Resource.Error(
                "Cannot retrieve data. Check your internet connection and try again."
            )
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred.")
        }

        if(response.isSuccessful && response.body() != null && response.body()?.status == "Post Deleted") {
            return Resource.Success(true)
        }
        return Resource.Error("Unknown error occurred.")

    }

}