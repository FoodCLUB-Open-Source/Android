package live.foodclub.localdatasource.room.database

import live.foodclub.localdatasource.room.entity.UserDetailsModel
import live.foodclub.localdatasource.room.converters.Converters
import live.foodclub.localdatasource.room.dao.ProductDao
import live.foodclub.localdatasource.room.dao.ProfileDataDao
import live.foodclub.localdatasource.room.dao.UserDetailsDao
import live.foodclub.localdatasource.room.entity.ProductEntity
import live.foodclub.localdatasource.room.entity.ProductUnitEntity
import live.foodclub.localdatasource.room.entity.ProfileEntity
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import live.foodclub.localdatasource.room.dao.PostDao
import live.foodclub.localdatasource.room.entity.BookmarkPostEntity
import live.foodclub.localdatasource.room.entity.DiscoverPostEntity
import live.foodclub.localdatasource.room.entity.HomePostEntity
import live.foodclub.localdatasource.room.entity.PostEntity
import live.foodclub.localdatasource.room.entity.ProfilePostEntity

@Database(
    entities = [
        UserDetailsModel::class,
        ProfileEntity::class,
        ProductEntity::class,
        ProductUnitEntity::class,
        PostEntity::class,
        HomePostEntity::class,
        BookmarkPostEntity::class,
        ProfilePostEntity::class,
        DiscoverPostEntity::class
    ],
    version = 11,
    autoMigrations = [
        AutoMigration(3, 4),
        AutoMigration(4, 5),
        AutoMigration(5, 6),
        AutoMigration(6, 7),
        AutoMigration(7, 8),
        AutoMigration(10, 11)
    ]
)
@TypeConverters(Converters::class)
abstract class FoodCLUBDatabase : RoomDatabase() {
    abstract fun getUserDetailsDao(): UserDetailsDao
    abstract fun getProfileDao(): ProfileDataDao
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

        val migration8to9 = object : Migration(8, 9){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DROP TABLE home_posts")

                db.execSQL("CREATE TABLE IF NOT EXISTS `home_posts`(" +
                        "`id` INTEGER NOT NULL PRIMARY KEY, " +
                        "`postId` INTEGER NOT NULL, " +
                        "FOREIGN KEY(`postId`) REFERENCES `posts`(`postId`) " +
                        "ON UPDATE NO ACTION ON DELETE CASCADE)")
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_home_posts_postId` ON `home_posts` (`postId`)")
            }
        }

        val migration9to10 = object : Migration(9, 10){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DROP TABLE profile_posts")
                db.execSQL("DROP TABLE profile_bookmarked_posts")

                db.execSQL("CREATE TABLE IF NOT EXISTS `profile_posts`(" +
                        "`id` INTEGER NOT NULL PRIMARY KEY, " +
                        "`postId` INTEGER NOT NULL, " +
                        "FOREIGN KEY(`postId`) REFERENCES `posts`(`postId`) " +
                        "ON UPDATE NO ACTION ON DELETE CASCADE)")
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_profile_posts_postId` ON `profile_posts` (`postId`)")

                db.execSQL("CREATE TABLE IF NOT EXISTS `bookmark_posts`(" +
                        "`id` INTEGER NOT NULL PRIMARY KEY, " +
                        "`postId` INTEGER NOT NULL, " +
                        "FOREIGN KEY(`postId`) REFERENCES `posts`(`postId`) " +
                        "ON UPDATE NO ACTION ON DELETE CASCADE)")
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_bookmark_posts_postId` ON `bookmark_posts` (`postId`)")
            }
        }
    }

}