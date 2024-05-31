package live.foodclub.network.remotedatasource.posts.provider

import live.foodclub.network.remotedatasource.posts.sources.PostsRemoteDataSource

interface PostsRemoteDataSourceProvider {
    fun getHomeDataSource(): PostsRemoteDataSource
    fun getProfilePostsDataSource(): PostsRemoteDataSource
    fun getDiscoverDataSource(): PostsRemoteDataSource
    fun getBookmarkDataSource(): PostsRemoteDataSource
}