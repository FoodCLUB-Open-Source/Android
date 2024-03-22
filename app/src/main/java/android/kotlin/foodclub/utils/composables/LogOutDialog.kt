package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LogOutDialog(onDismissRequest: () -> Unit, onConfirmRequest: () -> Unit )
{
    Dialog(onDismissRequest = onDismissRequest, properties = DialogProperties(dismissOnBackPress = false)) {
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
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {

                Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_20))) {
                    Text(text = stringResource(id = R.string.Log_out_confirmation), fontFamily = Montserrat, fontWeight = FontWeight.Black)
                }
                
                Row(modifier = Modifier
                    .align(Alignment.End)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = onDismissRequest, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Black)) {
                        Text(text = stringResource(id = R.string.no), fontFamily = Montserrat, fontWeight = FontWeight.Bold)
                    }
                    Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Black)) {
                        Text(text = stringResource(id = R.string.yes), fontFamily = Montserrat, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}