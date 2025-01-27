package live.foodclub.presentation.ui.home.messaging

import androidx.annotation.StringRes
import live.foodclub.R
import live.foodclub.config.ui.BottomBarScreenObject
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import live.foodclub.domain.models.auth.Contact
import live.foodclub.presentation.ui.home.messaging.composables.ChatView
import live.foodclub.utils.composables.MessagingProfilePhoto
import live.foodclub.utils.composables.SwipeToDismissContainer

@Composable
fun MessagingView(
    contactsState: ContactsState,
    chatState: ChatState,
    navController: NavController,
    events: MessagingViewEvents
) {
    var showChatView by remember { mutableStateOf(false) }
    var showCreateConversationDialog by remember { mutableStateOf(false) }
    val messagesHistory = if (contactsState.contactsSearchText == "")
        contactsState.contacts else contactsState.contactsSearchResult
    // TODO need fix
    LaunchedEffect(key1 = chatState) {
        if (chatState.conversationId.isNotBlank() && !showChatView) {
            showChatView = true
        }
    }
    if (showChatView) {
        ChatView(
            onBackPressed = { showChatView = !showChatView },
            chatState = chatState,
            events = events,
        )
    } else if (showCreateConversationDialog) {
        CreateConversationView(
            navigateToChatView = {
                showCreateConversationDialog = !showCreateConversationDialog

            },
            contactsState = contactsState,
            events = events,
        )
    } else {
        Scaffold(
            topBar = {
                MessagingTopAppBar(
                    onBackPressed = {
                        navController.navigate(BottomBarScreenObject.Home.route)
                    },
                    titleRes = R.string.messages
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black)
                        .padding(it)
                        .padding(dimensionResource(id = R.dimen.dim_20)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MessagingHeaderSection(contactsState.contactsSearchText) { text ->
                        events.setSearchText(text)
                        events.filterContacts(text)
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_20)))
                    MessagesSection(
                        messagesHistory,
                        onShowChatView = { chatId ->
                            events.getConversation(chatId)
                            showChatView = !showChatView
                        },
                        onDeleteConversation = { chatId ->
                            events.deleteConversation(chatId)
                        }
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    contentColor = foodClubGreen,
                    backgroundColor = foodClubGreen,
                    onClick = { showCreateConversationDialog = !showCreateConversationDialog }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.start_new_chat),
                        contentDescription = null,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dim_32)),
                        tint = Color.Black
                    )
                }
            }
        )
    }
}


@Composable
fun MessagingTopAppBar(
    onBackPressed: () -> Unit,
    @StringRes titleRes: Int,
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
        )
    }

    TopAppBar(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth(),
        backgroundColor = Color.Black,
    ) {
        IconButton(
            onClick = { onBackPressed() }
        ) {
            Icon(
                modifier = Modifier.clickable { onBackPressed() },
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = stringResource(R.string.Messaging_View_Navigate_Back_CD),
                tint = Color.White
            )
        }
        Text(
            text = stringResource(id = titleRes),
            fontWeight = FontWeight(600),
            fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
            fontFamily = Montserrat,
            color = Color.White
        )

    }
}


@Composable
fun MessagingHeaderSection(
    searchTextValue: String,
    onSearch: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        MessagingSearchBar(searchTextValue = searchTextValue, onSearch = onSearch)
        // we will use in future
        //Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_20)))
        // StartNewGroupSection()
    }
}

@Composable
fun MessagingSearchBar(
    searchTextValue: String,
    onSearch: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10))),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.messaging_view_search_contacts_container_color),
                unfocusedContainerColor = colorResource(id = R.color.messaging_view_search_contacts_container_color),
                disabledContainerColor = colorResource(id = R.color.messaging_view_search_contacts_container_color),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White
            ),
            value = searchTextValue,
            onValueChange = {
                onSearch(it)
            },
            singleLine = true,
            placeholder = {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = stringResource(id = R.string.search_contacts),
                    color = colorResource(id = R.color.messaging_view_search_contacts_color),
                    textAlign = TextAlign.Center,
                    fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight(400),
                    lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp
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
                        tint = colorResource(id = R.color.messaging_view_search_contacts_color),
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dim_20))
                    )
                }
            }
        )
    }
}

//@Composable
//fun StartNewGroupSection() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .border(
//                border = BorderStroke(
//                    dimensionResource(id = R.dimen.dim_0pt5),
//                    colorResource(id = R.color.messaging_start_group_container_border_color)
//                ),
//                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_8))
//            )
//            .background(color = colorResource(id = R.color.messaging_start_group_container_color)),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column(
//            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_10)),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.Start
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.group),
//                    contentDescription = null,
//                    modifier = Modifier.size(dimensionResource(id = R.dimen.dim_40))
//                )
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))
//                Column(
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.Start
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.messaging_general_text),
//                        fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
//                        fontFamily = Montserrat,
//                        fontWeight = FontWeight(500),
//                        lineHeight = dimensionResource(id = R.dimen.fon_15).value.sp,
//                        color = Color.White
//                    )
//                    Text(
//                        text = stringResource(id = R.string.messaging_general_text2),
//                        fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
//                        fontFamily = Montserrat,
//                        fontWeight = FontWeight(500),
//                        lineHeight = dimensionResource(id = R.dimen.fon_15).value.sp,
//                        color = Color.White
//                    )
//
//                }
//            }
//        }
//        Column(
//            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_10)),
//            verticalArrangement = Arrangement.Center
//        ) {
//            IconButton(
//                onClick = { /*TODO impl starting new group chat*/ }
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.start_new_group),
//                    contentDescription = null,
//                    modifier = Modifier.size(dimensionResource(id = R.dimen.dim_40))
//                )
//            }
//        }
//    }
//}

@Composable
fun MessagesSection(
    contacts: List<Contact>,
    onShowChatView: (String) -> Unit,
    onDeleteConversation: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_16))
    ) {
        // the first one is index use to delete
        itemsIndexed(contacts) { _, contact ->
            SwipeToDismissContainer(
                containerBackgroundColor = Color.Black,
                iconAlignment = Alignment.CenterEnd,
                onDismiss = {
                    onDeleteConversation(contact.conversationId)
                },
            ) {
                SingleUserRow(
                    contact,
                    onShowChatView = {
                        onShowChatView(contact.conversationId)
                    },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_20)))
            }

        }

    }
}

@Composable
fun SingleUserRow(
    contact: Contact,
    onShowChatView: () -> Unit
) {
    val contactName = contact.recipientFullName.ifBlank { contact.recipientUsername }
    Row(
        modifier = Modifier
            .background(Color.Black)
            .padding(horizontal = dimensionResource(id = R.dimen.dim_8))
            .fillMaxWidth()
            .clickable { onShowChatView() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                MessagingProfilePhoto(photoUrl = contact.recipientProfileImage)
                Spacer(modifier = Modifier.padding(end = dimensionResource(id = R.dimen.dim_10)))
                Column {
                    if (contact.recipientId == 0) {
                        ChefAiNameBox()
                    } else {
                        Text(
                            text = contactName,
                            fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight(500),
                            lineHeight = dimensionResource(id = R.dimen.fon_21).value.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
                    Text(
                        text = contact.lastMessage,
                        fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                        fontFamily = Montserrat,
                        fontWeight = if (contact.isMessageSeen) FontWeight(500) else FontWeight(
                            400
                        ),
                        lineHeight = dimensionResource(id = R.dimen.fon_18).value.sp,
                        color = if (contact.isMessageSeen) Color.White else Color.Gray,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = contact.lastMessageTime,
                fontSize = dimensionResource(id = R.dimen.fon_11).value.sp,
                fontFamily = Montserrat,
                color = Color.White,
                lineHeight = dimensionResource(id = R.dimen.fon_13).value.sp,
                fontWeight = FontWeight(400)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
            if (contact.unSeenMessageCount != 0) {
                Box(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_20))
                        .background(color = foodClubGreen, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = contact.unSeenMessageCount.toString(),
                        fontSize = dimensionResource(id = R.dimen.fon_11).value.sp,
                        fontFamily = Montserrat,
                        color = Color.Black,
                        lineHeight = dimensionResource(id = R.dimen.fon_13).value.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }
    }
}

@Composable
fun ChefAiNameBox() {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.chef),
            fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight(500),
            lineHeight = dimensionResource(id = R.dimen.fon_21).value.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_3)))
        Box(
            modifier = Modifier
                .background(
                    foodClubGreen,
                    RoundedCornerShape(dimensionResource(id = R.dimen.dim_4))
                )
                .padding(horizontal = dimensionResource(id = R.dimen.dim_4))
        ) {
            Text(
                text = stringResource(id = R.string.ai),
                color = Color.Black,
                fontFamily = Montserrat,
                fontWeight = FontWeight(600),
                fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
                lineHeight = dimensionResource(id = R.dimen.fon_20).value.sp
            )
        }
    }
}

