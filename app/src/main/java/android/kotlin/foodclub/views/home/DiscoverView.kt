package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.Satoshi
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.profile.UserPosts
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.IngredientsBottomSheet
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import android.kotlin.foodclub.viewModels.home.DiscoverViewModel
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DiscoverView(navController: NavController) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 240.dp

    var isSmallScreen by remember { mutableStateOf(false) }
    if (screenHeight <= 440.dp) { isSmallScreen = true }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White, darkIcons = true
        )
    }

    val viewModel: DiscoverViewModel = hiltViewModel()

    val mainSearchText by viewModel.mainSearchText.collectAsState()
    val ingredientsSearchText by viewModel.ingredientsSearchText.collectAsState()

    val displayedProducts = viewModel.displayedProducts.collectAsState()
    val dbProducts = viewModel.productsDatabase.collectAsState()

    val productsList = displayedProducts.value.ifEmpty {
        dbProducts.value.productsList
    }

    var ingredientToEdit: Ingredient? = null

    var homePosts: State<List<VideoModel>>?
    val worldPosts: State<List<VideoModel>>? = null

    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }

    val datePickerDialogColors = DatePickerDefaults.colors(
        containerColor = Color.White,
        titleContentColor = Color.White,
        headlineContentColor = Color.White,
    )
    val datePickerColors = DatePickerDefaults.colors(
        weekdayContentColor = Color.Gray,
        selectedDayContainerColor = Color.Red,
        todayDateBorderColor = Color.Red,
        todayContentColor = Color.Red
    )

    viewModel.getPostsByWorld(197)
    viewModel.getPostsByUserId()
    viewModel.myFridgePosts()

    val initialPage = 0
    val pagerState1 = rememberPagerState(
        initialPage = initialPage,
        initialPageOffsetFraction = 0f
    ) {
        4
    }

    val fling = PagerDefaults.flingBehavior(
        state = pagerState1, lowVelocityAnimationSpec = tween(
            easing = LinearEasing, durationMillis = 300
        )
    )
    var mainTabIndex by remember { mutableIntStateOf(0) }
    val mainTabItemsList = listOf("My Kitchen", "World", "Categories")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            MainSearchBar(
                searchTextValue = mainSearchText,
                navController = navController
            )
        }

        item {
            MainTabRow(
                tabsList = mainTabItemsList,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                mainTabIndex = it
            }
        }

        item {
            SubSearchBar(
                searchTextValue = ingredientsSearchText,
                onSearch = { input->
                    viewModel.onSubSearchTextChange(input)
                }
            )
        }

        item {
            var subTabIndex by remember { mutableIntStateOf(0) }
            SubTabRow(
                onTabChanged = {
                    subTabIndex = it
                }
            )
        }

        item {
            if (isDatePickerVisible) {
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    CustomDatePicker(
                        modifier = Modifier.shadow(5.dp),
                        shape = RoundedCornerShape(6.dp),
                        datePickerState = datePickerState,
                        datePickerColors = datePickerColors,
                        datePickerDialogColors = datePickerDialogColors,
                        onDismiss = { isDatePickerVisible = false },
                        onSave = { date ->
                            if (date != null){
                                selectedDate = date
                            }
                        }
                    )
                }
            }
        }

        item {
            if (mainTabIndex == 0){
                homePosts = viewModel.postList.collectAsState()

                IngredientsList(
                    Modifier,
                    productsList,
                    onAddDateClicked = { isDatePickerVisible = true },
                    onEditClicked = {
                            item->
                        ingredientToEdit = item
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            navController.navigate("MY_DIGITAL_PANTRY_VIEW")
                        },
                        text = "See All",
                        color = foodClubGreen,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))


                HorizontalPager(
                    beyondBoundsPageCount = 1,
                    flingBehavior = fling,
                    modifier = Modifier
                        .height(500.dp)
                        .padding(top = 0.dp),
                    state = pagerState1
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, start = 15.dp, end = 15.dp, bottom = 100.dp)
                    ) {
                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                            val userName = viewModel.sessionUserName.value

                            if(homePosts!=null){
                                items(homePosts!!.value) { dataItem ->
                                    viewModel.getPostData(dataItem.videoId)
                                    GridItem2(navController, dataItem, userName)
                                }
                            }

                            else if(worldPosts!=null){
                                items(worldPosts.value) { dataItem ->

                                    viewModel.getPostData(dataItem.videoId)
                                    GridItem2(navController, dataItem, userName)
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    var showSheet by remember { mutableStateOf(false) }

    val triggerBottomSheetModal: () -> Unit = {
        showSheet = !showSheet
        systemUiController.setStatusBarColor(
            color = Color(0xFFACACAC), darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = Color.Black, darkIcons = true
        )
    }
    SideEffect {
        if (!showSheet) {
            systemUiController.setSystemBarsColor(
                color = Color.White, darkIcons = true
            )
        }
    }
    if (showSheet) {
        IngredientsBottomSheet(triggerBottomSheetModal, viewModel.productsDatabase)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSearchBar(
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
                .pointerInput(Unit) {
                    navController.navigate("SEARCH_VIEW")
                }
            ,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Color(0xFFF5F5F5)
            ),
            value = searchTextValue,
            onValueChange = {

            },
            placeholder = {
                Text(
                    modifier = Modifier.padding(top = 3.dp),
                    text = "Search for recipes, usernames...",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painterResource(id = R.drawable.search_icon_ingredients),
                        contentDescription = "",
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
                        containerColor = foodClubGreen)
                    { Text(text = "5", color = Color.Black) }
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.vector__1_),
                    contentDescription = "Add to Basket",
                    tint = Color.Black
                )
            }
        }
    }
}


@Composable
fun MainTabRow(
    tabsList: List<String>,
    horizontalArrangement: Arrangement.Horizontal,
    onTabChanged: (Int) -> Unit) {
    var mainTabIndex by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
        ,
        horizontalArrangement = horizontalArrangement
    ) {
        tabsList.forEachIndexed { index, data ->
            val isSelected = index == mainTabIndex

            Text(
                text = data,
                modifier = Modifier
                    .clickable {
                        onTabChanged(index)
                        mainTabIndex = index
                    }
                    .drawBehind {
                        if (isSelected) {
                            val strokeWidthPx = 2.dp.toPx()
                            val topPaddingPx = 4.dp.toPx()
                            val underlineHeight = 2.dp.toPx()
                            val verticalOffset = size.height - (underlineHeight / 2) + topPaddingPx
                            drawLine(
                                color = Color.Black,
                                strokeWidth = strokeWidthPx,
                                start = Offset(0f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        }
                    },

                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.Black else Color(0xFFC2C2C2),
                fontSize = 20.sp,
                lineHeight = 24.38.sp,
                textAlign = TextAlign.Start,
                fontFamily = Montserrat
            )
            if (tabsList[0] != "My Kitchen"){
                Spacer(modifier = Modifier.width(50.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubSearchBar(
    searchTextValue: String,
    onSearch: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, end = 20.dp, start = 20.dp, bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.68f)
                .clip(
                    RoundedCornerShape(15.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Color(0xFFF5F5F5)
            ),
            value = searchTextValue,
            onValueChange = {
                onSearch(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier.padding(top = 3.dp),
                    text = "Search to find or add...",
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
                        contentDescription = "",
                    )
                }
            }
        )

        Button(
            shape = RoundedCornerShape(corner = CornerSize(25.dp)),
            modifier = Modifier
                .height(56.dp)
                .width(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = foodClubGreen,
            ),
            contentPadding = PaddingValues(),
            onClick = {
                // TODO impl camera
            }
        ) {
            Icon(painterResource(id = R.drawable.camera_icon), contentDescription = "")
        }
        Button(
            shape = RoundedCornerShape(corner = CornerSize(25.dp)),
            modifier = Modifier
                .height(56.dp)
                .width(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = foodClubGreen,
            ),
            contentPadding = PaddingValues(),
            onClick = {
                // TODO impl microphone
            }
        ) {
            Icon(painterResource(id = R.drawable.mic_icon), contentDescription = "")
        }

    }
}
@Composable
fun SubTabRow(
    onTabChanged: (Int) -> Unit
) {
    val subTabItemsList = listOf("Veg & Fruits", "Grains & Cereals", "Dairy & Alternatives")
    var subTabIndex by remember { mutableIntStateOf(0) }

    LazyRow(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 5.dp, top = 10.dp),
        content = {
            itemsIndexed(subTabItemsList) { index, data ->
                val selected = subTabIndex == index

                Text(
                    modifier = Modifier
                        .drawBehind {
                            if (selected) {
                                val strokeWidthPx = 2.dp.toPx()
                                val topPaddingPx =
                                    4.dp.toPx() // Adjust the top padding as needed
                                val underlineHeight =
                                    2.dp.toPx() // Adjust the underline height as needed
                                val verticalOffset =
                                    size.height - (underlineHeight / 2) + topPaddingPx
                                drawLine(
                                    color = Color.Black,
                                    strokeWidth = strokeWidthPx,
                                    start = Offset(0f, verticalOffset),
                                    end = Offset(size.width, verticalOffset)
                                )
                            }
                        }
                        .clickable {
                            subTabIndex = index
                            onTabChanged(index)
                        }
                    ,
                    text = data,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    color = if (selected) Color.Black else Color(0xFFC2C2C2),
                    fontSize = 17.sp,
                    lineHeight = 20.88.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = Montserrat
                )
                Spacer(modifier = Modifier.width(50.dp)) // Add spacing
            }
        }
    )
}

@Composable
fun IngredientsList(
    modifier: Modifier,
    productsList: List<Ingredient>,
    onAddDateClicked: () -> Unit,
    onEditClicked: (Ingredient) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.57f)
            .background(
                color = Color.White
            )
    ) {
        TitlesSection(modifier, view = "DiscoverView")
        SwipeableItemsLazyColumn(
            modifier = modifier,
            height = 275,
            productsList = productsList,
            onEditClicked = onEditClicked,
            onAddDateClicked = onAddDateClicked,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabHomeDiscover(
    tabItems: List<String>, pagerState1: PagerState, scope: CoroutineScope, fontSize: TextUnit
) {

    TabRow(
        selectedTabIndex = pagerState1.currentPage,
        modifier = Modifier.background(Color.White),
        containerColor = Color.White,
        contentColor = Color.White,
        divider = {},
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState1.currentPage]),
                height = 0.dp,
                color = Color.Black
            )
        }

    ) {
        tabItems.forEachIndexed { index, item ->
            Tab(selected = pagerState1.currentPage == index,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                selectedContentColor = Color.Black,
                onClick = {
                    scope.launch {
                        pagerState1.animateScrollToPage(index)
                    }

                },

                text = {
                    Text(
                        text = AnnotatedString(item), style = TextStyle(
                            fontWeight = if (pagerState1.currentPage == index)
                                FontWeight.Bold
                            else
                                FontWeight.Normal,
                            color = if (pagerState1.currentPage == index)
                                Color.Black
                            else
                                Color(0xFFC2C2C2),
                            fontSize = fontSize,
                            textAlign = TextAlign.Center,
                        )
                    )
                })

        }
    }
}

@Composable
fun GridItem2(navController: NavController, dataItem: VideoModel, userName:String) {
    Card(modifier = Modifier
        .height(272.dp)
        .width(178.dp)
        .padding(10.dp), shape = RoundedCornerShape(15.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Image(
                painter = rememberAsyncImagePainter(dataItem.thumbnailLink),
                contentDescription = "",
                Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate("DELETE_RECIPE/${dataItem.videoId}") },
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = userName,
                    fontFamily = Satoshi,
                    color = Color.White,
                    fontSize = 15.sp
                )
                Text(
                    text =  dataItem.createdAt ,
                    fontFamily = Satoshi,
                    fontSize = 13.sp,
                    color= Color.White
                )
            }
        }
    }
}

@Composable
fun GridItem2(navController: NavController, dataItem: UserPosts, userName: String) {
    Card(modifier = Modifier
        .height(272.dp)
        .width(178.dp)
        .padding(10.dp), shape = RoundedCornerShape(15.dp)
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Image(
                painter = rememberAsyncImagePainter(dataItem.thumbnailUrl),
                contentDescription = "",
                Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate("DELETE_RECIPE/${dataItem.id}") },
                contentScale = ContentScale.FillHeight
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp), verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = dataItem.totalLikes.toString() ,
                    fontFamily = Satoshi,
                    color = Color.White,
                    fontSize = 15.sp
                )

            }
        }

    }
}