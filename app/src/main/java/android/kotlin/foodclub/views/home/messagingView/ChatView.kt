package android.kotlin.foodclub.views.home.messagingView

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ChatView(
    onBackPressed: () -> Unit
){
    var sendingText by remember {
        mutableStateOf("")
    }

    BackHandler {
        onBackPressed()
    }

    Scaffold(
        topBar = {
            ChatViewTopBar(onBackPressed)
        },
        content = {
            it.calculateBottomPadding()
            it.calculateTopPadding()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ){

            }
        },
        bottomBar = {
            ChatViewBottomBar(
                onInputTextChanged = {
                    sendingText = it
                },
                text = sendingText
            )
        }
    )
}

@Composable
fun ChatViewTopBar(onBackPressed: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.chat_view_top_bar_background))
            .padding(
                top = dimensionResource(id = R.dimen.dim_30),
                bottom = dimensionResource(id = R.dimen.dim_10),
                start = dimensionResource(id = R.dimen.dim_10)
            )
    ) {
        IconButton(
            onClick = { onBackPressed() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = null,
                tint = Color.White
            )
        }
        Image(
            painter = painterResource(id = R.drawable.profilepicture),
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_30))
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))

        ChefAiNameBox()
    }
}

@Composable
fun ChatViewBottomBar(
    onInputTextChanged: (String) -> Unit,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .background(colorResource(id = R.color.chat_view_text_field_container_color)),
    ) {
        TextField(
            modifier = Modifier.align(Alignment.CenterStart),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.chat_view_text_field_container_color),
                unfocusedContainerColor = colorResource(id = R.color.chat_view_text_field_container_color),
                disabledContainerColor = colorResource(id = R.color.chat_view_text_field_container_color),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White
            ),
            value = text,
            onValueChange = {
                onInputTextChanged(it)
            },
            label = {
                Text(
                    stringResource(id = R.string.message_input_label),
                    fontWeight = FontWeight(400),
                    fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                    lineHeight = dimensionResource(id = R.dimen.fon_17).value.sp,
                    fontFamily = Montserrat,
                    color = colorResource(id = R.color.chat_view_text_label_color)
                )
            }
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.post_comment),
                contentDescription = null,
                tint = foodClubGreen
            )
        }
    }
}