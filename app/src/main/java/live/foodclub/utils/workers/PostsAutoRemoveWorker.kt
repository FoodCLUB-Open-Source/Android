package live.foodclub.utils.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import live.foodclub.localdatasource.room.dao.PostDao

@HiltWorker
class PostsAutoRemoveWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val postDao: PostDao
): Worker(appContext, workerParams) {

    override fun doWork(): Result {
        return try {
            postDao.deleteOrphanedPosts()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}