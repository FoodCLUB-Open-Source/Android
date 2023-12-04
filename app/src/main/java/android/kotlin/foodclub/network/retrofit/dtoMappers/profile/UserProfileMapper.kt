package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.profile.TopCreatorsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserProfileDto
import android.kotlin.foodclub.network.retrofit.utils.DomainMapper

class UserProfileMapper(
    private val userPostsMapper: PostToVideoMapper
): DomainMapper<UserProfileDto, UserProfile> {
    override fun mapToDomainModel(entity: UserProfileDto): UserProfile {
        return UserProfile(
            username = entity.username,
            profilePictureUrl = entity.profilePictureUrl,
            totalUserLikes = entity.totalUserLikes,
            totalUserFollowers = entity.totalUserFollowers,
            totalUserFollowing = entity.totalUserFollowing,

            userPosts = entity.userPosts.map {
                userPostsMapper.mapToDomainModel(it)
            },

            topCreators = entity.topCreators.map {
                SimpleUserModel(
                    userId = it.id,
                    username = it.username,
                    profilePictureUrl = it.profilePictureUrl
                )
            }
        )
    }

    override fun mapFromDomainModel(domainModel: UserProfile): UserProfileDto {
        return UserProfileDto(
            username = domainModel.username,
            profilePictureUrl = domainModel.profilePictureUrl,
            totalUserLikes = domainModel.totalUserLikes,
            totalUserFollowers = domainModel.totalUserFollowers,
            totalUserFollowing = domainModel.totalUserFollowing,

            userPosts = domainModel.userPosts.map {
                userPostsMapper.mapFromDomainModel(it)
            },

            topCreators = domainModel.topCreators.map {
                TopCreatorsDto(
                    id = it.userId,
                    username = it.username,
                    profilePictureUrl = it.profilePictureUrl
                )
            }
        )
    }
}