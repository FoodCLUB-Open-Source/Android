package android.kotlin.foodclub.views.home.messagingView

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun ChatView(
    onBackPressed: () -> Unit
){
    var sendingText by remember { mutableStateOf("") }

    val user1 = User(userId = 1, userName = "User1")
    val user2 = User(userId = 2, userName = "User2")

    var currentUserId by remember { mutableIntStateOf(user1.userId) }

    var conversation by remember {
        mutableStateOf(
            Conversation(
                messages = emptyList(),
                user1 = user1,
                user2 = user2
            )
        )
    }

    BackHandler {
        onBackPressed()
    }

    Scaffold(
        topBar = {
            ChatViewTopBar(onBackPressed)
        },
        content = { scaffoldPadding ->
            MessageHistory(conversation.messages, scaffoldPadding)
        },
        bottomBar = {
            ChatViewBottomBar(
                text = sendingText,
                onInputTextChanged = {
                    sendingText = it
                },
                onMessageSent = {
                    conversation = conversation.copy(
                        messages = conversation.messages + Message(
                            senderId = currentUserId,
                            content = it,
                            timestamp = ""
                        )
                    )
                    sendingText = ""
                    currentUserId = if (currentUserId == user1.userId) user2.userId else user1.userId
                }
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
    text: String,
    onInputTextChanged: (String) -> Unit,
    onMessageSent: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .background(colorResource(id = R.color.chat_view_text_field_container_color)),
    ) {
        TextField(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(0.9f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.chat_view_text_field_container_color),
                unfocusedContainerColor = colorResource(id = R.color.chat_view_text_field_container_color),
                disabledContainerColor = colorResource(id = R.color.chat_view_text_field_container_color),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                cursorColor = foodClubGreen
            ),
            value = text,
            onValueChange = {
                onInputTextChanged(it)
            },
            placeholder = {
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
            onClick = {
                if (text != ""){
                    onMessageSent(text)
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.post_comment),
                contentDescription = null,
                tint = foodClubGreen
            )
        }
    }
}

@Composable
fun MessageHistory(
    messages: List<Message>,
    paddingValues: PaddingValues
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (messagesRef, chatBox) = createRefs()

        val listState = rememberLazyListState()
        LaunchedEffect(messages.size) {
            listState.animateScrollToItem(messages.size)
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
                .padding(dimensionResource(id = R.dimen.dim_15))
                .constrainAs(messagesRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(chatBox.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
        ) {
            items(messages.size) { index ->
                val message = messages[index]
                MessageBox(message = message)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
            }
        }
    }
}

@Composable
fun MessageBox(message: Message) {
    val isSentByUser1 = message.senderId == 1
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isSentByUser1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimensionResource(id = R.dimen.dim_30)),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_6)))
                        .background(
                            colorResource(id = R.color.chat_view_sent_message_box_container_color)
                        )
                        .padding(dimensionResource(id = R.dimen.dim_10)),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = message.content,
                        color = Color.White,
                        fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                        fontFamily = Montserrat,
                        lineHeight = dimensionResource(id = R.dimen.fon_18).value.sp,
                        fontWeight = FontWeight(400)
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profilepicture),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.dim_15))
                        .size(dimensionResource(id = R.dimen.dim_25))
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_6)))
                        .background(
                            colorResource(id = R.color.chat_view_received_message_box_container_color)
                        )
                        .padding(dimensionResource(id = R.dimen.dim_10)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = message.content,
                        color = colorResource(id = R.color.chat_view_received_message_text_color),
                        fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                        fontFamily = Montserrat,
                        lineHeight = dimensionResource(id = R.dimen.fon_18).value.sp,
                        fontWeight = FontWeight(400)
                    )
                }
            }
        }
    }
}


data class Conversation(
    val messages: List<Message>,
    val user1: User,
    val user2: User
)

data class Message(
    val senderId: Int,
    val content: String,
    val timestamp: String
)

data class User(
    val userId: Int,
    val userName: String
)