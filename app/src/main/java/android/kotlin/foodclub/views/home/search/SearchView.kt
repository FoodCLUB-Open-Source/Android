package android.kotlin.foodclub.views.home.search

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.views.home.discover.MainTabRow
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SearchView(
    navController: NavController
){
    var mainTabIndex by remember { mutableIntStateOf(0) }
    val mainTabItemsList = stringArrayResource(id = R.array.search_tabs)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        SearchRow(
            searchTextValue = "",
            navController = navController
        )

        MainTabRow(
            tabsList = mainTabItemsList,
            horizontalArrangement = Arrangement.Start
        ) {
            mainTabIndex = it
        }

        Spacer(modifier = Modifier.height(25.dp))

        SearchHistory()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRow(
    searchTextValue: String,
    navController: NavController
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp, end = 20.dp, start = 20.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(
                    RoundedCornerShape(15.dp)
                )
            ,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Color(0xFFF5F5F5)
            ),
            value = searchTextValue,
            onValueChange = {
                // TODO implementation
            },
            placeholder = {
                Text(
                    modifier = Modifier.padding(top = 3.dp),
                    text = "",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.back_arrow),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        )

        Spacer(modifier = Modifier.width(5.dp))

        Button(
            shape = RoundedCornerShape(corner = CornerSize(25.dp)),
            modifier = Modifier
                .height(56.dp)
                .width(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(245, 245, 245, 255),
            ),
            contentPadding = PaddingValues(),
            onClick = {
                // TODO impl search - this can be done
            }
        ) {

            BadgedBox(
                badge = {
                    Badge(
                        modifier = Modifier.offset(x = (-5).dp, y = 5.dp),
                        containerColor = foodClubGreen
                    )
                    { Text(
                        text = stringResource(id = R.string.dummy_badge_number),
                        color = Color.Black
                    ) }
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.vector__1_),
                    contentDescription = stringResource(id = R.string.add_to_basket),
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun SearchHistory(){
    val searchHistoryList = stringArrayResource(id = R.array.dummy_search_history)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(id = R.string.recent_searches),
            fontSize = 20.sp,
            lineHeight = 24.38.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight(700)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            content = {
                itemsIndexed(searchHistoryList) { index, data ->
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color(0xFFF5F5F5))
                            .weight(1f)

                            .align(Alignment.Start)
                        ,
                        onClick = { /*TODO*/ }
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.history_icon),
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                                Text(
                                    modifier = Modifier.padding(start=10.dp),
                                    text = data,
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500),
                                    lineHeight = 19.5.sp
                                )

                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        )
    }
}