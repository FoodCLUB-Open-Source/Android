package live.foodclub.network.remotedatasource.posts.provider

import live.foodclub.network.remotedatasource.posts.sources.PostsRemoteDataSource
import live.foodclub.network.remotedatasource.posts.annotations.BookmarkRemoteDataSource
import live.foodclub.network.remotedatasource.posts.annotations.DiscoverRemoteDataSource
import live.foodclub.network.remotedatasource.posts.annotations.HomeRemoteDataSource
import live.foodclub.network.remotedatasource.posts.annotations.ProfilePostsRemoteDataSource
import javax.inject.Inject

class PostsRemoteDataSourceProviderImpl @Inject constructor(
    @HomeRemoteDataSource private val homePostsRemoteDataSource: PostsRemoteDataSource,
    @ProfilePostsRemoteDataSource private val profilePostsRemoteDataSource: PostsRemoteDataSource,
    @DiscoverRemoteDataSource private val discoverRemoteDataSource: PostsRemoteDataSource,
    @BookmarkRemoteDataSource private val bookmarkRemoteDataSource: PostsRemoteDataSource
): PostsRemoteDataSourceProvider {
    override fun getHomeDataSource(): PostsRemoteDataSource {
        return homePostsRemoteDataSource
    }

    override fun getProfilePostsDataSource(): PostsRemoteDataSource {
        return profilePostsRemoteDataSource
    }

    override fun getDiscoverDataSource(): PostsRemoteDataSource {
        return discoverRemoteDataSource
    }

    override fun getBookmarkDataSource(): PostsRemoteDataSource {
        return bookmarkRemoteDataSource
    }
}