package android.kotlin.foodclub.views.home.search



import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.utils.composables.ShimmerBrush
import android.kotlin.foodclub.utils.helpers.checkInternetConnectivity
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
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
){ val context = LocalContext.current
    val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context))

    val brush = ShimmerBrush()
    var mainTabIndex by remember { mutableStateOf(0) }
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
            isInternetConnected,
            brush,
            tabsList = mainTabItemsList,
            horizontalArrangement = Arrangement.Start
        ) {
            mainTabIndex = it
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_25)))

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
            .padding(top = dimensionResource(id = R.dimen.dim_60), end = dimensionResource(id = R.dimen.dim_20), start = dimensionResource(id = R.dimen.dim_20), bottom = dimensionResource(id = R.dimen.dim_10)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(
                    RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))
                )
            ,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = containerColor
            ),
            value = searchTextValue,
            onValueChange = {
                // TODO implementation
            },
            placeholder = {
                Text(
                    modifier = Modifier.padding(top =dimensionResource(id = R.dimen.dim_3)),
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

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))

        Button(
            shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.dim_25))),
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dim_56))
                .width(dimensionResource(id = R.dimen.dim_56)),
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
                        modifier = Modifier.offset(x = -dimensionResource(id = R.dimen.dim_5), y =dimensionResource(id = R.dimen.dim_5)),
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
            .padding(start = dimensionResource(id = R.dimen.dim_20), end = dimensionResource(id = R.dimen.dim_20)),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_10)),
            text = stringResource(id = R.string.recent_searches),
            fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
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
                            .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
                            .background(containerColor)
                            .weight(1f)

                            .align(Alignment.Start)
                        ,
                        onClick = { /*TODO*/ }
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dim_20), end = dimensionResource(id = R.dimen.dim_20)),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.history_icon),
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                                Text(
                                    modifier = Modifier.padding(start=dimensionResource(id = R.dimen.dim_10)),
                                    text = data,
                                    color = Color.Black,
                                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                                    fontWeight = FontWeight(500),
                                    lineHeight = 19.5.sp
                                )

                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
                }
            }
        )
    }
}