package live.foodclub.config.ui

import live.foodclub.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Montserrat = FontFamily(
    Font(R.font.montserratregular),
    Font(R.font.montserratbold, FontWeight.Bold),
    Font(R.font.montserratsemibold, FontWeight.SemiBold),
    Font(R.font.montserratmedium, FontWeight.Medium)
)

val PlusJakartaSans = FontFamily(
    Font(R.font.plusjakartasans_regular),
    Font(R.font.plusjakartasans_bold, FontWeight.Bold),
    Font(R.font.plusjakartasans_semibold, FontWeight.SemiBold)
)

val Avenir = FontFamily(
    Font(R.font.avenirblack, FontWeight.Bold),
    Font(R.font.avenirbook, FontWeight.Medium)
)

val Raleway = FontFamily(
    Font(R.font.ralewayextrabold, FontWeight.ExtraBold),
)

val Satoshi = FontFamily(
    Font(R.font.satoshi, FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)