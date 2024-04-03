package android.kotlin.foodclub.utils.composables.products

import android.kotlin.foodclub.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun ProductSearchBar(
    onSearch: (String) -> Unit,
    textFieldColors: TextFieldColors,
    placeholder: String,
    textFieldModifier: Modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
        .shadow(
            elevation = dimensionResource(id = R.dimen.dim_2),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16))
        ),
    enableCamera: Boolean = true,
    enableMike: Boolean = true
) {
    var searchText by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.dim_20),
                end = dimensionResource(id = R.dimen.dim_20),
                start = dimensionResource(id = R.dimen.dim_20),
                bottom = dimensionResource(id = R.dimen.dim_15)
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = textFieldModifier,
//                .fillMaxWidth(if (enableCamera && enableMike) 0.68f else 1.0f)
//                .fillMaxWidth()
//                .let { modifier ->
//                    if (actionType == ActionType.ADD_INGREDIENTS_VIEW) {
//                        modifier.clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
//                        modifier.shadow(
//                            elevation = dimensionResource(id = R.dimen.dim_2),
//                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16))
//                        )
//                    } else {
//                        modifier.border(
//                            border = BorderStroke(
//                                width = dimensionResource(id = R.dimen.dim_1),
//                                color = colorResource(id = R.color.gray).copy(alpha = 0.3f)
//                            ),
//                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_16))
//                        )
//                    }
//                },
            colors = textFieldColors,
            value = searchText,
            onValueChange = { searchText = it },
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp,
                    fontWeight = FontWeight(400),
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        // TODO impl search
                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.search_icon_ingredients),
                        contentDescription = null,
                    )
                }
            }
        )

        LaunchedEffect(searchText) {
            delay(1000)
            onSearch(searchText)
        }

//        if (enableCamera) {
//            Button(
//                shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
//                modifier = Modifier
//                    .height(dimensionResource(id = R.dimen.dim_56))
//                    .width(dimensionResource(id = R.dimen.dim_56)),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = foodClubGreen,
//                ),
//                contentPadding = PaddingValues(),
//                onClick = {
//                    navController.navigate("ScanView_route")
//                }
//            ) {
//                Icon(painterResource(id = R.drawable.camera_icon), contentDescription = "")
//            }
//        }
//
//        if (enableMike) {
//            Button(
//                shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
//                modifier = Modifier
//                    .height(dimensionResource(id = R.dimen.dim_56))
//                    .width(dimensionResource(id = R.dimen.dim_56)),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = foodClubGreen,
//                ),
//                contentPadding = PaddingValues(),
//                onClick = {
//
//                    // TODO impl microphone
//                }
//            ) {
//                Icon(painterResource(id = R.drawable.mic_icon), contentDescription = "")
//            }
//        }
    }
}