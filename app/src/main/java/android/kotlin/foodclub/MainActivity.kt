package android.kotlin.foodclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.kotlin.foodclub.config.ui.FoodClubTheme
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import android.kotlin.foodclub.navigation.RootNavigationGraph
import android.kotlin.foodclub.utils.composables.MainLayout
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                val navController = rememberNavController()
                MainLayout(navController = navController) { showSheet, triggerBottomSheetModal,
                                                            triggerStory, setBottomBarVisibility ->
                    RootNavigationGraph(navController, showSheet, triggerBottomSheetModal, triggerStory, setBottomBarVisibility)
                }
            }
        }
    }
}