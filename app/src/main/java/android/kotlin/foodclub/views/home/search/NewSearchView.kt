package android.kotlin.foodclub.views.home.search

import android.accounts.Account
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.utils.composables.shimmerBrush
import android.kotlin.foodclub.utils.helpers.checkInternetConnectivity
import android.kotlin.foodclub.viewModels.home.profile.ProfileViewModel
import android.kotlin.foodclub.viewModels.home.search.SearchEvents
import android.kotlin.foodclub.views.home.discover.MainTabRow
import android.kotlin.foodclub.views.home.messagingView.User
import android.kotlin.foodclub.views.home.profile.GridItem
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay

@Composable
fun NewSearchView(
    navController: NavController,
    events: SearchEvents,
    state: SearchState
){
    val context = LocalContext.current
    val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context))
    val brush = shimmerBrush()
    var mainTabIndex by remember { mutableIntStateOf(0) }
    val tabItems = listOf(stringResource(id = R.string.All),
        stringResource(id = R.string.Accounts), stringResource(id = R.string.Recipes)).toTypedArray()

    var accountTabItems = listOf<User>()
    var recipeTabItems = listOf<VideoModel>()

    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(key1 = searchText) {
        if (searchText.isNotEmpty()) {
            events.searchByText(searchText)
            delay(6000)
            if (state.userList.isNotEmpty()){
                Log.i("1111111", state.userList[1].toString())
            }
            if (state.postList.isNotEmpty()){
                Log.i("1111111", state.postList[1].toString())
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NewSearchRow(
            searchTextValue = searchText,
            onSearchTextChanged = { newText -> searchText = newText },
            navController = navController
        )
        MainTabRow(
            isInternetConnected,
            brush,
            tabsList = tabItems,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            mainTabIndex = it
        }
        when (mainTabIndex) {
            0 ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    SearchBodyBoth(
                        accountTabItems = accountTabItems,
                        recipeTabItems=recipeTabItems,
                        brush = brush,
                        isInternetConnected = isInternetConnected)
                }

            1 -> Column(modifier = Modifier.fillMaxWidth()) {
                SearchBodyAccounts(
                    accountTabItems = accountTabItems,
                    brush = brush,
                    isInternetConnected = isInternetConnected)
                    }
            2 -> Column(modifier = Modifier.fillMaxWidth()) {
                SearchBodyRecipes(
                    recipeTabItems=recipeTabItems,
                    brush = brush,
                    isInternetConnected = isInternetConnected
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_25)))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSearchRow(
    searchTextValue: String,
    onSearchTextChanged: (String) -> Unit,
    navController: NavController,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.dim_60),
                end = dimensionResource(id = R.dimen.dim_20),
                start = dimensionResource(id = R.dimen.dim_10),
                bottom = dimensionResource(id = R.dimen.dim_10)
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.back_arrow),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .border(
                    width = dimensionResource(id = R.dimen.dim_1),
                    color = Color.LightGray,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                )
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_15)))
                .padding(
                    start = dimensionResource(id = R.dimen.dim_5)
                )
        ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(
                    RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
                ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
                unfocusedTextColor = Color.Gray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            value = searchTextValue,
            onValueChange = {
                    newText -> onSearchTextChanged(newText)
            },
            placeholder = {
                Text(
                    modifier = Modifier.padding(top =dimensionResource(id = R.dimen.dim_3)),
                    text = "Search Here",
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
                        painterResource(id = R.drawable.search_icon_ingredients),
                        contentDescription = "search"
                    )
                }
            }
        )
        }

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))
        }

}

@Composable
fun SearchBodyBoth(
    modifier: Modifier = Modifier,
    accountTabItems : List<User>,
    recipeTabItems : List<VideoModel>,
    brush:Brush,
    isInternetConnected :Boolean
) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(
                start = dimensionResource(id = R.dimen.dim_20),
                top = dimensionResource(id = R.dimen.dim_20)
            )
    ) {
        Text(
            text = stringResource(id = R.string.Accounts),
            color = Color.LightGray,
            fontWeight = FontWeight.Normal,
            fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
            lineHeight = dimensionResource(id = R.dimen.fon_24).value.sp,
            textAlign = TextAlign.Start,
            fontFamily = Montserrat
        )

        accountTabItems.forEach { _ ->
            SearchAccountGridItem()
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_16)))

        Text(
            text = stringResource(id = R.string.Recipes),
            color = Color.LightGray,
            fontWeight = FontWeight.Normal,
            fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
            lineHeight = dimensionResource(id = R.dimen.fon_24).value.sp,
            textAlign = TextAlign.Start,
            fontFamily = Montserrat
        )

        recipeTabItems.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { dataItem ->
                    SearchRecipeGridItem(
                        modifier = Modifier.weight(1f),
                        brush = brush,
                        isInternetConnected = isInternetConnected,
                        dataItem = dataItem
                        )
                }

                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@Composable
fun SearchBodyAccounts(
    modifier: Modifier = Modifier,
    accountTabItems : List<User>,
    brush:Brush,
    isInternetConnected :Boolean
) {
    Column(modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.dim_20),
                top = dimensionResource(id = R.dimen.dim_20))
        ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            val lazyListState = rememberLazyListState()
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = accountTabItems, key = { it }) { _ ->
                    SearchAccountGridItem()
                }
            }
        }
    }

}

@Composable
fun SearchBodyRecipes(
    modifier: Modifier = Modifier,
    recipeTabItems : List<VideoModel>,
    brush:Brush,
    isInternetConnected :Boolean
) {
    Column(modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.dim_20),
                top = dimensionResource(id = R.dimen.dim_20)))
    {
        Row (modifier= Modifier.fillMaxWidth()){
            val lazyGridState = rememberLazyGridState()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = lazyGridState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = recipeTabItems, key = { it }) { dataItem ->
                    SearchRecipeGridItem(
                        brush = brush,
                        isInternetConnected = isInternetConnected,
                        dataItem = dataItem)
                }
            }
        }
    }
}

@Composable
fun SearchRecipeGridItem(
    modifier: Modifier = Modifier,
    brush: Brush,
    isInternetConnected:Boolean,
    dataItem: VideoModel,
){
    val thumbnailPainter = rememberAsyncImagePainter(dataItem.thumbnailLink)

    Card(modifier = Modifier
        .height(dimensionResource(id = R.dimen.dim_272))
        .width(dimensionResource(id = R.dimen.dim_178))
        .padding(dimensionResource(id = R.dimen.dim_10))
        ,shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (thumbnailPainter.state is AsyncImagePainter.State.Loading || !isInternetConnected) brush
                    else SolidColor(Color.Transparent)
                )
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Image(
                painter = painterResource(id = R.drawable.salad_ingredient),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun SearchAccountGridItem(){
    val username by remember { mutableStateOf("Username") }
    val name by remember { mutableStateOf("Name") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dim_8))
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dim_50))
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_16)))

        Column (){
            Text(
                text = username,
                fontWeight = FontWeight.SemiBold,
                fontSize = dimensionResource(id = R.dimen.fon_17).value.sp,
                fontFamily = Montserrat,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_4))
            )
            Text(text = name,
                fontWeight = FontWeight.Normal,
                fontFamily = Montserrat,
                fontSize = dimensionResource(id = R.dimen.fon_15).value.sp)
        }
    }
}