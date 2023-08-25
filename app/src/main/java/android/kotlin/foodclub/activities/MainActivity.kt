package android.kotlin.foodclub.activities

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.kotlin.foodclub.ui.theme.FoodClubTheme
import android.kotlin.foodclub.views.home.ChangePasswordView
import android.kotlin.foodclub.views.home.EditProfileSetting
import android.kotlin.foodclub.views.home.PrivacySetting
import android.kotlin.foodclub.views.home.SettingsView
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.foodclub.navigation.graphs.RootNavigationGraph
import com.example.foodclub.views.home.DiscoverView

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
            FoodClubTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}