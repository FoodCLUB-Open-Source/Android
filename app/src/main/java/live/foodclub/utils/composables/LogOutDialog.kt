package live.foodclub.utils.composables

import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.views.settings.colorGray
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import live.foodclub.config.ui.text_blue

@Composable
fun LogOutDialog(onDismissRequest: () -> Unit, onConfirmRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = false)
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                .width(dimensionResource(id = R.dimen.dim_282))
                .height(dimensionResource(id = R.dimen.dim_135))
                .background(Color.White),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = dimensionResource(id = R.dimen.dim_10)
            ),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight()
            )
            {

                Box(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dim_20))
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.Log_out_confirmation),
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        //.height(IntrinsicSize.Min)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Button(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding()
                            .customBorder(dimensionResource(id = R.dimen.dim_1), colorGray, true)
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            color = text_blue,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = onConfirmRequest,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding()
                            .customBorder(dimensionResource(id = R.dimen.dim_1), colorGray, false)
                    ) {
                        Text(
                            text = stringResource(id = R.string.log_out),
                            fontFamily = Montserrat,
                            color = text_blue,
                            fontWeight = FontWeight(integerResource(id = R.integer.int_600)),
                        )
                    }
                }
            }
        }
    }
}

fun Modifier.customBorder(strokeWidth: Dp, color: Color, isLeft: Boolean = true) : Modifier
= composed {
    val density = LocalDensity.current
    val strokeWidthPx = density.run { strokeWidth.toPx() }

    this then Modifier.drawBehind {
        val width = size.width
        val height = size.height - strokeWidthPx / 2

        drawLine(
            color = color,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = width, y = 0f),
            strokeWidth = strokeWidthPx
        )

        drawLine(
            color = color,
            start = Offset(x = if (isLeft) width else 0f, y = 0f),
            end = Offset(x = if (isLeft) width else 0f, y = height),
            strokeWidth = strokeWidthPx
        )
    }
}

