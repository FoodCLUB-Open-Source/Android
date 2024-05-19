package live.foodclub.localdatasource.room.dao

import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import live.foodclub.domain.enums.PostType
import live.foodclub.localdatasource.room.entity.HomePostEntity
import live.foodclub.localdatasource.room.entity.PostEntity
import live.foodclub.localdatasource.room.entity.ProfileEntity
import live.foodclub.localdatasource.room.entity.toHomePosts
import live.foodclub.localdatasource.room.relationships.PostWithUser

@Dao
interface PostDao {
    @Transaction
    @Query("SELECT * FROM posts INNER JOIN home_posts ON posts.postId = home_posts.postId")
    fun getHomePagePosts(): PagingSource<Int, PostWithUser>

    @Upsert
    suspend fun insertPosts(posts: List<PostEntity>)

    @Upsert
    suspend fun insertPostUsers(users: List<ProfileEntity>)

    @Transaction
    suspend fun insertPostsWithUser(posts: List<PostWithUser>) {
        val allUsers = posts.map { it.user }.distinctBy { it.userId }

        insertPostUsers(allUsers)
        insertPosts(posts.map { it.postEntity })
    }

    @Upsert
    suspend fun insertHomePosts(posts: List<HomePostEntity>)

    @Query("DELETE FROM home_posts")
    fun clearAllHomePosts()

    @Transaction
    suspend fun insertPaginatedDataForType(
        posts: List<PostWithUser>, loadType: LoadType, postType: PostType
    ) {
        val refresh = loadType == LoadType.REFRESH
        insertPostsWithUser(posts)

        when(postType){
            PostType.PROFILE -> TODO()
            PostType.BOOKMARK -> TODO()
            PostType.HOME -> {
                if(refresh) { clearAllHomePosts() }
                insertHomePosts(posts.map { it.postEntity.toHomePosts() })
            }
            PostType.DISCOVER -> TODO()
        }
    }

    fun getPagingData(postType: PostType): PagingSource<Int, PostWithUser> {
        return when(postType) {
            PostType.PROFILE -> TODO()
            PostType.BOOKMARK -> TODO()
            PostType.HOME -> getHomePagePosts()
            PostType.DISCOVER -> TODO()
        }
    }

    fun countRows(postType: PostType): Flow<Int> {
        return when(postType) {
            PostType.PROFILE -> TODO()
            PostType.BOOKMARK -> TODO()
            PostType.HOME -> countHomePosts()
            PostType.DISCOVER -> TODO()
        }
    }

    @Query("SELECT COUNT(*) FROM home_posts")
    fun countHomePosts(): Flow<Int>
}