package android.kotlin.foodclub.activities

import android.kotlin.foodclub.views.home.ProgressionBar
import android.kotlin.foodclub.views.home.SettingsView
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        var keepSplashOnScreen = true
        val delay = 2500L

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashOnScreen = false }, delay)
        setContent {
//            FoodClubTheme {
//                RootNavigationGraph(navController = rememberNavController())
//            }

           // ProgressionBar(3000)
            SettingsView()
        }
    }
}