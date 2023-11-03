package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.utils.composables.EditIngredientQuantityPicker
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.helpers.ValueParser
import android.kotlin.foodclub.viewModels.home.DiscoverViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFridgeView(
    navController: NavController
){
    val viewModel: DiscoverViewModel = hiltViewModel()
    viewModel.getPostsByWorld(197)
    viewModel.getPostsByUserId()
    viewModel.myFridgePosts()

    val modifier = Modifier

    var isShowEditScreen by remember { mutableStateOf(false) }
    var topBarTitleText by remember { mutableStateOf("") }

    var ingredientToEdit: Ingredient? = null

    val searchText by viewModel.searchText.collectAsState()
    val displayedProducts = viewModel.displayedProducts.collectAsState()
    val dbProducts = viewModel.productsDatabase.collectAsState()

    val productsList = displayedProducts.value.ifEmpty {
        dbProducts.value.productsList
    }

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

    BackHandler {
        if (isShowEditScreen){
            isShowEditScreen = false
        }else{
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = topBarTitleText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 48.sp,
                    fontFamily = Montserrat,
                ) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (isShowEditScreen){
                                isShowEditScreen = false
                            }else{
                                navController.popBackStack()
                            }
                        }
                    ){
                        Icon(
                            painterResource(id = R.drawable.back_arrow),
                            contentDescription = "",
                        )
                    }
                },
                actions = {
                    if (isShowEditScreen){
                        IconButton(
                            onClick = {
                                isShowEditScreen = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Edit Screen"
                            )
                        }
                    }
                }
            )
        },
        content = {
            Column(
                modifier = modifier
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    )
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Top
            ) {

                if (isShowEditScreen){
                    topBarTitleText = "Edit Item"
                    EditIngredientView(ingredient = ingredientToEdit!!)
                }else{
                    topBarTitleText = "All My Ingredients"
                    SearchMyIngredients(
                        modifier = Modifier,
                        searchTextValue = searchText,
                        onSearch = { input->
                            viewModel.onSearchTextChange(input)
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    IngredientsList(
                        modifier,
                        productsList,
                        onAddDateClicked = { isDatePickerVisible = true },
                        onEditClicked = {
                                item->
                            ingredientToEdit = item
                            isShowEditScreen = !isShowEditScreen
                        }
                    )

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
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMyIngredients(
    modifier: Modifier,
    searchTextValue: String,
    onSearch: (String) -> Unit
) {

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
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
            onSearch(it) // Call the onSearch callback when text changes
        },
        placeholder = {
            Text(
                modifier = modifier.padding(top = 3.dp),
                text = "Search my ingredients",
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
                      },
        leadingIcon = {
            IconButton(
                onClick = {}
            ){
                Icon(
                    painterResource(id = R.drawable.search_icon_ingredients),
                    contentDescription = "",
                )
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
    Surface(
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White
                )
        ) {
            TitlesSection(modifier)
            SwipeableItemsLazyColumn(
                modifier = modifier,
                productsList = productsList,
                onEditClicked = onEditClicked,
                onAddDateClicked = onAddDateClicked
            )
        }
    }
}

@Composable
fun TitlesSection(modifier: Modifier){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(start = 15.dp, end = 15.dp, top = 35.dp, bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Name",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 19.5.sp,
            fontFamily = Montserrat,
            color = Color.Black
        )
        Text(
            modifier = modifier.padding(start = 15.dp),
            text = "Quantity",
            fontWeight = FontWeight(500),
            fontSize = 16.sp,
            lineHeight = 19.5.sp,
            fontFamily = Montserrat,
            color = Color.Gray
        )
        Text(
            text = "Expiration Date",
            fontWeight = FontWeight(500),
            fontSize = 16.sp,
            lineHeight = 19.5.sp,
            fontFamily = Montserrat,
            color = Color.Gray
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableItemsLazyColumn(
    modifier: Modifier,
    productsList: List<Ingredient>,
    onEditClicked: (Ingredient) -> Unit,
    onAddDateClicked: () -> Unit
){
    LazyColumn(
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp)
            .background(Color.White),
    ){
        itemsIndexed(productsList){index, ingredient ->
            var notSwiped by remember { mutableStateOf(false) }
            val dismissState = rememberDismissState(
                confirmValueChange = { dismiss ->
                    if (dismiss == DismissValue.DismissedToEnd) notSwiped =
                        !notSwiped
                    dismiss != DismissValue.DismissedToEnd
                }
            )

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                LaunchedEffect(key1 = true) {
                    // TODO delete the ingredient
                    dismissState.reset()
                }
            } else {
                LaunchedEffect(key1 = true) {
                    dismissState.reset()
                }
            }

            Spacer(Modifier.height(8.dp))
            if (index != 0){
                Divider(thickness = 1.dp, modifier = modifier.alpha(0.5f), color = LightGray)
            }
            Spacer(Modifier.height(8.dp))


            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.White
                            DismissValue.DismissedToEnd -> Color.White
                            DismissValue.DismissedToStart -> Color.Red
                        }, label = ""
                    )
                    val alignment = Alignment.CenterEnd
                    val icon = Icons.Default.Delete

                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
                        label = ""
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp)
                        ,
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Localized description",
                            modifier = Modifier.scale(scale),
                            tint = Color.White
                        )
                    }
                },
                dismissContent = {
                    SingleIngredientItem(
                        modifier,
                        ingredient,
                        onAddDateClicked,
                        onEditClicked
                    )
                }
            )
        }
    }
}

@Composable
fun SingleIngredientItem(
    modifier: Modifier,
    item: Ingredient,
    onAddDateClicked: () -> Unit,
    onEditClicked: (Ingredient) -> Unit
) {
    val title = item.type.split(",").first().trim()
    val quantity = item.quantity.toString()
    val unit = "g" // for now

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Column(modifier = modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(48.dp)
                        .width(48.dp)
                        .clip(CircleShape)
                )
                Text(
                    modifier =modifier.padding(start = 6.dp),
                    text = title,
                    fontWeight = FontWeight(500),
                    lineHeight = 19.5.sp,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
        Column(modifier = modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier =modifier.padding(start = 6.dp),
                    text = quantity+unit,
                    fontWeight = FontWeight(500),
                    fontSize = 16.sp,
                    lineHeight = 19.5.sp,
                    fontFamily = Montserrat,
                    color = Color.Gray
                )
            }
        }
        Column(modifier = modifier.weight(1f)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = modifier
                        .clickable {
                            onAddDateClicked()
                        },
                    text = "Add Date",
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    lineHeight = 19.5.sp,
                    fontFamily = Montserrat,
                    color = Color.Gray
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(foodClubGreen)
                        .clickable {
                            onEditClicked(item)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun EditIngredientView(ingredient: Ingredient){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ingredient.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
        val pickerValues = remember {
            mutableStateOf((1..10).map {
                Pair(
                    it,
                    (it * 100).toString()+ValueParser.quantityUnitToString(ingredient.unit)
                )
            })
        }

        val quantity = pickerValues.value.map { it.first }
        val grammage = pickerValues.value.map { it.second }
        val types = listOf("Pint","Jar","Cup","Bottle","Bag","Sack","Can")

        Row(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, start = 20.dp)) {
            Text(
                text = "Quantity:",
                fontSize = 22.sp,
                fontWeight = FontWeight(600),
                color = Color.Black,
                fontFamily = Montserrat
                )
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp, bottom = 20.dp),
            ) {
                EditIngredientQuantityPicker(
                    quantity = quantity,
                    grammage = grammage,
                    types = types
                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            2.dp,
                            Color(126, 198, 11, 255),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(RoundedCornerShape(15.dp))
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.White
                    ), contentPadding = PaddingValues(15.dp),
                    onClick = {
                        // TODO impl delete ingredient
                    }
                ) {
                    Text(
                        text = "Remove",
                        color = Color(126, 198, 11, 255),
                        fontFamily = Montserrat,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 24.38.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color(126, 198, 11, 255),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(RoundedCornerShape(15.dp))
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(126, 198, 11, 255),
                        contentColor = Color.White
                    ), contentPadding = PaddingValues(15.dp),
                    onClick = {
                        // TODO impl save changes
                    }
                ) {
                    Text(
                        text = "Save",
                        color = Color.White,
                        fontFamily = Montserrat,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 24.38.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

