package android.kotlin.foodclub

import android.Manifest
import android.content.pm.PackageManager
import android.kotlin.foodclub.config.ui.FoodClubTheme
import android.kotlin.foodclub.navigation.RootNavigationGraph
import android.kotlin.foodclub.utils.composables.MainLayout
import android.kotlin.foodclub.utils.helpers.checkPermissions
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // TODO: Inform user that that your app will not show notifications.
            }
        }
        requestPermissionsForNotifications(requestPermissionLauncher)

        enableEdgeToEdge()
        var keepSplashOnScreen = true
        val delay = 2500L

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashOnScreen = false
        }, delay)
        setContent {
            // this is for taking profile picture with camera
            // later move this to an appropriate place
            if (!checkPermissions(context = applicationContext)) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CAMERA), 0
                )
            }

            FoodClubTheme {
                val navController = rememberNavController()
                MainLayout(
                    navController = navController
                ) { showSheet,
                    triggerBottomSheetModal,
                    triggerStory,
                    setBottomBarVisibility ->
                    RootNavigationGraph(
                        navController,
                        showSheet,
                        triggerBottomSheetModal,
                        triggerStory,
                        setBottomBarVisibility
                    )
                }
            }
        }
    }

    private fun requestPermissionsForNotifications(requestPermissionLauncher: ActivityResultLauncher<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}