package android.kotlin.foodclub.views.home.messagingView

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun MessagingView() {
    var searchText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MessagingHeaderSection(searchText) { searchText = it }
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
        modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_20))
    ) {
        Text(
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_15)),
            text = stringResource(id = R.string.messages),
            fontWeight = FontWeight(600),
            fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
            fontFamily = Montserrat,
            color = Color.White
        )
        MessagingSearchBar(searchTextValue = searchTextValue, onSearch = onSearch)

    }
}

@Composable
fun MessagingSearchBar(
    searchTextValue: String,
    onSearch: (String) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(dimensionResource(id = R.dimen.dim_10))
                ),
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