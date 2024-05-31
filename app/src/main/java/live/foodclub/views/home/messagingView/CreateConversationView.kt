package live.foodclub.views.home.messagingView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.utils.composables.MessagingProfilePhoto
import live.foodclub.viewModels.home.messaging.MessagingViewEvents

@Composable
fun CreateConversationView(
    navigateToChatView: () -> Unit,
    contactsState: ContactsState,
    events: MessagingViewEvents,
) {
    val followings = if (contactsState.followingsSearchText == "") {
        contactsState.followings
    } else contactsState.followingsSearchResult
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
//        Will be used in future
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { /*TODO create conversation with group and go to group conversation*/ },
//                backgroundColor = foodClubGreen,
//                contentColor = Color.Black
//            ) {
//
//                Text(
//                    text = stringResource(id = R.string.create),
//                    fontWeight = FontWeight.SemiBold,
//                    fontFamily = Montserrat,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//        }
        topBar = {
            MessagingTopAppBar(
                onBackPressed = navigateToChatView,
                titleRes = R.string.create_conversation
            )
        }
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .padding(it)
                .padding(dimensionResource(id = R.dimen.dim_16)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MessagingSearchBar(searchTextValue = contactsState.followingsSearchText,
                onSearch = { text ->
                    events.setFollowingsSearchText(text)
                    events.filterFollowings(text)
                })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_20)))
            LazyColumn(
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_16)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_16))
            ) {
                items(followings) { following ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                events.createConversation(following)
                                navigateToChatView()
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                dimensionResource(id = R.dimen.dim_8)
                            )
                        ) {
                            MessagingProfilePhoto(photoUrl = following.profilePictureUrl)
                            Text(
                                text = following.userFullName ?: following.username,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_32)))
                                .background(foodClubGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                }
            }
        }


    }
}