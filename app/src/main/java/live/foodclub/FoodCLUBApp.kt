package live.foodclub

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import live.foodclub.utils.workers.PostsAutoRemoveWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class FoodCLUBApp : Application(), Configuration.Provider {
    @Inject lateinit var workerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()
        setupPeriodicPostsAutoRemoveWork()
    }

    private fun setupPeriodicPostsAutoRemoveWork() {
        val cleanupRequest = PeriodicWorkRequestBuilder<PostsAutoRemoveWorker>(1, TimeUnit.DAYS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "postsAutoRemoveWorker",
            ExistingPeriodicWorkPolicy.UPDATE,
            cleanupRequest
        )
    }
}