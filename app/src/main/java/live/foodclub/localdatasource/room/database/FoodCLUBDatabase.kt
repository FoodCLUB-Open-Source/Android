package live.foodclub.localdatasource.room.database

import live.foodclub.localdatasource.room.entity.UserDetailsModel
import live.foodclub.localdatasource.room.converters.Converters
import live.foodclub.localdatasource.room.dao.ProductDao
import live.foodclub.localdatasource.room.dao.ProfileDataDao
import live.foodclub.localdatasource.room.dao.UserDetailsDao
import live.foodclub.localdatasource.room.dao.UserProfileBookmarksDao
import live.foodclub.localdatasource.room.dao.UserProfilePostsDao
import live.foodclub.localdatasource.room.entity.ProductEntity
import live.foodclub.localdatasource.room.entity.ProductUnitEntity
import live.foodclub.localdatasource.room.entity.ProfileEntity
import live.foodclub.localdatasource.room.entity.ProfileBookmarksEntity
import live.foodclub.localdatasource.room.entity.ProfilePostsEntity
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import live.foodclub.localdatasource.room.dao.PostDao
import live.foodclub.localdatasource.room.entity.HomePostEntity
import live.foodclub.localdatasource.room.entity.PostEntity

@Database(
    entities = [
        UserDetailsModel::class,
        ProfilePostsEntity::class,
        ProfileEntity::class,
        ProfileBookmarksEntity::class,
        ProductEntity::class,
        ProductUnitEntity::class,
        PostEntity::class,
        HomePostEntity::class
    ],
    version = 7,
    autoMigrations = [
        AutoMigration(3, 4),
        AutoMigration(4, 5),
        AutoMigration(5, 6),
        AutoMigration(6, 7)
    ]
)
@TypeConverters(Converters::class)
abstract class FoodCLUBDatabase : RoomDatabase() {
    abstract fun getUserDetailsDao(): UserDetailsDao
    abstract fun getUserProfilePostsDao(): UserProfilePostsDao
    abstract fun getProfileDao(): ProfileDataDao
    abstract fun getUserProfileBookmarksDao(): UserProfileBookmarksDao
    abstract fun getProductDao(): ProductDao
    abstract fun getPostsDao(): PostDao

    companion object {
        val migration1To2 = object: Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE `new_user_posts`(" +
                        "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "`videoId` INTEGER NOT NULL, " +
                        "`title` TEXT, " +
                        "`description` TEXT, " +
                        "`createdAt` TEXT, " +
                        "`videoLink` TEXT, " +
                        "`thumbnailLink` TEXT, " +
                        "`totalLikes` INTEGER, " +
                        "`totalViews` INTEGER)")

                db.execSQL("INSERT INTO new_user_posts" +
                        "(videoId, title, description, createdAt, videoLink, " +
                        "thumbnailLink, totalLikes, totalViews) " +
                        "SELECT videoId, title, description, createdAt, videoLink, " +
                        "thumbnailLink, totalLikes, totalViews FROM user_posts")

                db.execSQL("DROP TABLE user_posts")

                db.execSQL("ALTER TABLE new_user_posts RENAME TO user_posts")


                db.execSQL("CREATE TABLE `new_user_bookmarks`(" +
                        "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "`videoId` INTEGER NOT NULL, " +
                        "`title` TEXT, " +
                        "`description` TEXT, " +
                        "`createdAt` TEXT, " +
                        "`videoLink` TEXT, " +
                        "`thumbnailLink` TEXT, " +
                        "`totalLikes` INTEGER, " +
                        "`totalViews` INTEGER)")

                db.execSQL("INSERT INTO new_user_bookmarks" +
                        "(videoId, title, description, createdAt, videoLink, " +
                        "thumbnailLink, totalLikes, totalViews) " +
                        "SELECT videoId, title, description, createdAt, videoLink, " +
                        "thumbnailLink, totalLikes, totalViews FROM user_posts")

                db.execSQL("DROP TABLE user_bookmarks")

                db.execSQL("ALTER TABLE new_user_bookmarks RENAME TO user_bookmarks")
            }

        }

        val migration2to3 = object : Migration(2, 3){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DROP TABLE user_posts")
                db.execSQL("DROP TABLE user_bookmarks")

                db.execSQL("DELETE FROM profile_data WHERE userId IS NULL")
                db.execSQL("CREATE TABLE `profile_data_temp`(" +
                        "`userId` INTEGER NOT NULL PRIMARY KEY, " +
                        "`userName` TEXT NOT NULL, " +
                        "`profilePicture` TEXT, " +
                        "`totalUserFollowers` INTEGER, " +
                        "`totalUserFollowing` INTEGER, " +
                        "`totalUserLikes` INTEGER)")
                db.execSQL("INSERT INTO profile_data_temp(" +
                        "userId, userName, profilePicture, totalUserFollowers, " +
                        "totalUserFollowing, totalUserLikes) " +
                        "SELECT userId, userName, profilePicture, totalUserFollowers, " +
                        "totalUserFollowing, totalUserLikes FROM profile_data")
                db.execSQL("DROP TABLE profile_data")
                db.execSQL("ALTER TABLE profile_data_temp RENAME TO profile_data")

                db.execSQL("CREATE TABLE `profile_posts`(" +
                        "`videoId` INTEGER NOT NULL PRIMARY KEY, " +
                        "`authorId` INTEGER NOT NULL, " +
                        "`title` TEXT, " +
                        "`description` TEXT, " +
                        "`createdAt` TEXT, " +
                        "`videoLink` TEXT, " +
                        "`thumbnailLink` TEXT, " +
                        "`totalLikes` INTEGER, " +
                        "`totalViews` INTEGER, " +
                        "FOREIGN KEY(`authorId`) REFERENCES `profile_data`(`userId`) " +
                        "ON UPDATE NO ACTION ON DELETE CASCADE )")

                db.execSQL("CREATE TABLE `profile_bookmarked_posts`(" +
                        "`videoId` INTEGER NOT NULL PRIMARY KEY, " +
                        "`bookmarkedBy` INTEGER NOT NULL, " +
                        "`title` TEXT, " +
                        "`description` TEXT, " +
                        "`createdAt` TEXT, " +
                        "`videoLink` TEXT, " +
                        "`thumbnailLink` TEXT, " +
                        "`totalLikes` INTEGER, " +
                        "`totalViews` INTEGER, " +
                        "FOREIGN KEY(`bookmarkedBy`) REFERENCES `profile_data`(`userId`) " +
                        "ON UPDATE NO ACTION ON DELETE CASCADE )")
            }

        }
    }

}