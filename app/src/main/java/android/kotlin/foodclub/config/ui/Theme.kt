package android.kotlin.foodclub.config.ui

import android.app.Activity
import android.kotlin.foodclub.R
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val defaultProfileImage = R.drawable.story_user

@Composable
fun FoodClubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.White.toArgb()
            WindowCompat.getInsetsController(window, view).let {
                it.isAppearanceLightStatusBars = !darkTheme
                it.isAppearanceLightNavigationBars = !darkTheme
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun textFieldCustomColors(
    containerColor: Color = Color(0xFFDADADA).copy(alpha = 0.04F),
    textColor: Color = Color(0xFF939393),
    cursorColor: Color = Color(0xFF7EC60B),
    focusedIndicatorColor: Color = Color.Transparent,
    unfocusedLabelColor: Color = Color(0xFF000000).copy(alpha = 0.4F),
    focusedLabelColor: Color = Color(0xFF000000).copy(alpha = 0.4F),
    unfocusedIndicatorColor: Color = Color.Transparent,
    disabledIndicatorColor: Color = Color.Transparent
) = TextFieldDefaults.textFieldColors(
    containerColor = containerColor,
    unfocusedTextColor = textColor,
    focusedTextColor = textColor,
    cursorColor = cursorColor,
    unfocusedLabelColor = unfocusedLabelColor,
    focusedLabelColor = focusedLabelColor,
    focusedIndicatorColor = focusedIndicatorColor,
    unfocusedIndicatorColor = unfocusedIndicatorColor,
    disabledIndicatorColor = disabledIndicatorColor
)

@Composable
fun defaultButtonColors(
    containerColor: Color = foodClubGreen,
    disabledContainerColor: Color = Color(0xFFC9C9C9),
    disabledContentColor: Color = Color.White,
    contentColor: Color = Color.White
) = ButtonDefaults.buttonColors(
    containerColor = containerColor,
    disabledContainerColor = disabledContainerColor,
    disabledContentColor = disabledContentColor,
    contentColor = contentColor
)