package android.kotlin.foodclub.activities

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.kotlin.foodclub.ui.theme.FoodClubTheme
import android.kotlin.foodclub.viewmodels.home.ProfileViewModel
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.foodclub.navigation.graphs.RootNavigationGraph
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun profileViewModelFactory(): ProfileViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        var keepSplashOnScreen = true
        val delay = 2500L

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashOnScreen = false }, delay)
        setContent {
            FoodClubTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}