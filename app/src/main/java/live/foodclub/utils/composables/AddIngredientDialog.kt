package live.foodclub.utils.composables

import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddIngredientDialog(
    headline: String,
    textFirst: String,
    textSecond: String? = "",
    ingredientName: String? = ""
) {
    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                .width(dimensionResource(id = R.dimen.dim_500))
                .fillMaxHeight(0.25f)
                .background(Color.White),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = dimensionResource(id = R.dimen.dim_10)
            ),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_10)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.dim_10)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_34))
                            .clip(CircleShape)
                            .background(foodClubGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_24)),
                            imageVector = Icons.Default.Check,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = headline,
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dim_10)),
                        fontWeight = FontWeight(600),
                        lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                        fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                        fontFamily = Montserrat
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.dim_10),
                            horizontal = dimensionResource(id = R.dimen.dim_30)
                        ),
                ) {
                    Text(
                        text = "$textFirst $ingredientName $textSecond",
                        fontFamily = Montserrat,
                        fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                        lineHeight = dimensionResource(id = R.dimen.fon_17).value.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }
    }
}