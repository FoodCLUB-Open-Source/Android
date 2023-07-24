package android.kotlin.foodclub.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.kotlin.foodclub.ui.theme.FoodClubTheme
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.foodclub.navigation.graphs.RootNavigationGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            FoodClubTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}