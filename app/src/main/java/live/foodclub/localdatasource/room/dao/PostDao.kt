package live.foodclub.localdatasource.room.dao

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import live.foodclub.domain.enums.PostType
import live.foodclub.localdatasource.room.entity.BookmarkPostEntity
import live.foodclub.localdatasource.room.entity.HomePostEntity
import live.foodclub.localdatasource.room.entity.PostEntity
import live.foodclub.localdatasource.room.entity.ProfileEntity
import live.foodclub.localdatasource.room.entity.ProfilePostEntity
import live.foodclub.localdatasource.room.entity.toBookmarkEntity
import live.foodclub.localdatasource.room.entity.toHomePosts
import live.foodclub.localdatasource.room.entity.toProfilePosts
import live.foodclub.localdatasource.room.relationships.PostWithUser

@Dao
interface PostDao {
    @Transaction
    @Query("SELECT * FROM posts INNER JOIN home_posts ON posts.postId = home_posts.postId")
    fun getHomePagePosts(): PagingSource<Int, PostWithUser>

    @Transaction
    @Query("""
        SELECT * FROM posts INNER JOIN profile_posts ON posts.postId = profile_posts.postId 
        ORDER BY posts.createdAt DESC
    """)
    fun getProfilePosts(): PagingSource<Int, PostWithUser>

    @Transaction
    @Query("""
        SELECT * FROM posts INNER JOIN bookmark_posts ON posts.postId = bookmark_posts.postId 
        ORDER BY posts.createdAt DESC
    """)
    fun getBookmarkPosts(): PagingSource<Int, PostWithUser>

    @Query("DELETE FROM posts where postId=:id")
    fun deletePost(id: Long)

    @Upsert
    suspend fun insertPosts(posts: List<PostEntity>)

    @Transaction
    suspend fun insertPostUsers(users: List<ProfileEntity>) {
        users.forEach {
            val id = insertPostUser(it)
            if (id == -1L) updatePostUser(it.userId, it.userName, it.fullName, it.profilePicture)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPostUser(user: ProfileEntity): Long

    @Query("""
        UPDATE profile_data
        SET userName = :username, fullName = :fullName, profilePicture = :profilePicture
        WHERE userId = :userId
    """)
    suspend fun updatePostUser(userId: Long, username: String, fullName: String, profilePicture: String?)

    @Transaction
    suspend fun insertPostsWithUser(posts: List<PostWithUser>) {
        val allUsers = posts.map { it.user }.distinctBy { it.userId }

        insertPostUsers(allUsers)
        insertPosts(posts.map { it.postEntity })
    }

    @Upsert
    suspend fun insertHomePosts(posts: List<HomePostEntity>)

    @Upsert
    suspend fun insertProfilePosts(posts: List<ProfilePostEntity>)

    @Upsert
    suspend fun insertBookmarkPosts(posts: List<BookmarkPostEntity>)

    @Query("DELETE FROM home_posts")
    fun clearAllHomePosts()

    @Query("DELETE FROM profile_posts")
    fun clearAllProfilePosts()

    @Query("DELETE FROM bookmark_posts")
    fun clearAllBookmarkPosts()

    @Transaction
    suspend fun insertPaginatedDataForType(
        posts: List<PostWithUser>, loadType: LoadType, postType: PostType
    ) {
        val refresh = loadType == LoadType.REFRESH
        insertPostsWithUser(posts)

        when(postType){
            PostType.PROFILE -> {
                if(refresh) { clearAllProfilePosts() }
                insertProfilePosts(posts.map { it.postEntity.toProfilePosts() })
            }
            PostType.BOOKMARK -> {
                if(refresh) { clearAllBookmarkPosts() }
                insertBookmarkPosts(posts.map { it.postEntity.toBookmarkEntity() })
            }
            PostType.HOME -> {
                if(refresh) { clearAllHomePosts() }
                insertHomePosts(posts.map { it.postEntity.toHomePosts() })
            }
            PostType.DISCOVER -> TODO()
        }
    }

    fun getPagingData(postType: PostType): PagingSource<Int, PostWithUser> {
        return when(postType) {
            PostType.PROFILE -> getProfilePosts()
            PostType.BOOKMARK -> getBookmarkPosts()
            PostType.HOME -> getHomePagePosts()
            PostType.DISCOVER -> TODO()
        }
    }

    fun countRows(postType: PostType): Flow<Int> {
        return when(postType) {
            PostType.PROFILE -> countProfilePosts()
            PostType.BOOKMARK -> countBookmarkPosts()
            PostType.HOME -> countHomePosts()
            PostType.DISCOVER -> TODO()
        }
    }

    @Query("SELECT COUNT(*) FROM home_posts")
    fun countHomePosts(): Flow<Int>

    @Query("SELECT COUNT(*) FROM profile_posts")
    fun countProfilePosts(): Flow<Int>

    @Query("SELECT COUNT(*) FROM bookmark_posts")
    fun countBookmarkPosts(): Flow<Int>

    @Query("""
        DELETE FROM posts
        WHERE postId NOT IN (SELECT postId FROM home_posts)
        AND postId NOT IN (SELECT postId FROM profile_posts)
        AND postId NOT IN (SELECT postId FROM bookmark_posts)
    """)
    fun deleteOrphanedPosts()
}