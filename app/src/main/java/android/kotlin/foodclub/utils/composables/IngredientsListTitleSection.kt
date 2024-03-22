package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun IngredientsListTitleSection(modifier: Modifier, view: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.dim_20),
                top = dimensionResource(id = R.dimen.dim_15),
                bottom = dimensionResource(id = R.dimen.dim_15)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.name),
            fontWeight = FontWeight(500),
            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
            lineHeight = dimensionResource(id = R.dimen.fon_16).value.sp,
            fontFamily = Montserrat,
            color = Color.Gray
        )
        Text(
            modifier = modifier.padding(start = dimensionResource(id = R.dimen.dim_15)),
            text = stringResource(id = R.string.quantity),
            fontWeight = FontWeight(500),
            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
            lineHeight = dimensionResource(id = R.dimen.fon_16).value.sp,
            fontFamily = Montserrat,
            color = Color.Gray
        )
        Text(
            text = stringResource(id = R.string.expiry_date),
            fontWeight = FontWeight(500),
            fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
            lineHeight = dimensionResource(id = R.dimen.fon_16).value.sp,
            fontFamily = Montserrat,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))
    }
}
