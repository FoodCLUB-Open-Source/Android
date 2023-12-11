package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.views.settings.colorGray
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Settings Layout
 *
 * Settings layout used for every Settings screen in FoodCLUB app.
 * This arranges all components to build the screen
 *
 * @param label Label of the screen displayed on the very top.
 * @param onBackAction Executes when back button is clicked.
 * @param content [Composable] content of the screen
 */
@Composable
fun SettingsLayout(
    label: String,
    onBackAction: () -> Unit,
    content: @Composable() (() -> Unit)
) {
    Box(
        modifier =Modifier.fillMaxWidth()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding( dimensionResource(id = R.dimen.dim_16)).padding(top = dimensionResource(id = R.dimen.dim_80))
                .background(Color.White),
        ) {
            SettingsTopBar(label = label, onBackAction = onBackAction)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_50)))
            content()
        }
    }
}

@Composable
private fun SettingsTopBar(label: String, onBackAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column{
            IconButton(
                onClick = { onBackAction() },
                modifier = Modifier
                    .background(color = colorGray, RoundedCornerShape(dimensionResource(id = R.dimen.dim_8)))
                    .size( dimensionResource(id = R.dimen.dim_35)),
                content = {
                    SettingsIcons(size = 20, icon =  R.drawable.back_icon)
                }
            )
        }

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_20)))

        Column {
            SettingsText(text = label, size = 28, weight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun SettingsIcons(
    size: Int,
    icon: Int
){
    Icon(
        painter = painterResource(id = icon),
        contentDescription = stringResource(id = R.string.go_back),
        modifier = Modifier
            .size(size.dp)
    )
}

@Composable
private fun SettingsText(
    text: String,
    size: Int,
    weight: FontWeight,
    fontC: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Center
){
    Text(
        text = text,
        fontSize = size.sp,
        color = fontC,
        fontFamily = Montserrat,
        fontWeight = weight,
        textAlign = textAlign
    )
}