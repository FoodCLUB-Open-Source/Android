package android.kotlin.foodclub.views.home.scan

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.BottomBarScreenObject
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.LoadingProgressBar
import android.kotlin.foodclub.viewModels.home.DiscoverViewModel
import android.kotlin.foodclub.views.home.discover.DiscoverState
import android.kotlin.foodclub.views.home.myDigitalPantry.EditIngredientView
import android.kotlin.foodclub.views.home.myDigitalPantry.SwipeableItemsLazyColumn
import android.kotlin.foodclub.views.home.myDigitalPantry.TitlesSection
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanResultView(
    navController: NavController,
    viewModel: DiscoverViewModel,
    state: DiscoverState
)
{

    val modifier = Modifier
    val userIngredients = state.userIngredients

    var isShowEditScreen by remember { mutableStateOf(false) }
    var topBarTitleText by remember { mutableStateOf("") }

    val searchText = state.ingredientSearchText

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

    var loading by rememberSaveable { mutableStateOf(false) }
    BackHandler {
        navController.popBackStack()

    }
    Box(modifier =Modifier.fillMaxSize() )
    {if(loading)
    {
        LoadingProgressBar(
            text="Uploading...",
            route = BottomBarScreenObject.Play.route
            , navController = navController
        )
    }
     else{Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Scan Results List",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 48.sp,
                        fontFamily = Montserrat
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ){
                        Icon(
                            painterResource(id = R.drawable.back_arrow),
                            contentDescription = "",
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            loading=!loading
                            viewModel.addScanListToUserIngredients(state.scanResultItemList)

                        }
                    ) {
                        Text(
                            text = "Next",
                            color = foodClubGreen,
                            fontSize = 20.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = Montserrat
                        )
                    }
                },
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
                    EditIngredientView(
                        ingredient = state.ingredientToEdit!!,
                        onEditIngredient = { ingr ->
                            viewModel.updateIngredient(ingr)
                        }
                    )
                }else{
                    topBarTitleText = "All My Ingredients"
                    SearchResultIngredients(
                        modifier = Modifier,
                        searchTextValue = searchText,
                        onSearch = { input->
                            viewModel.onSubSearchTextChange(input)
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    ScanResultList(
                        modifier = modifier,
                        productsList = state.scanResultItemList,
                        onAddDateClicked = { isDatePickerVisible = true },
                        onEditClicked = {
                                item->
                            viewModel.updateIngredient(item)
                            isShowEditScreen = false
                        },
                        view = "DigitalPantry"
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
    }

}


@Composable
fun ScanResultList(
    modifier: Modifier,
    productsList: List<Ingredient>,
    onAddDateClicked: () -> Unit,
    onEditClicked: (Ingredient) -> Unit,
    view: String
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
            TitlesSection(modifier, view)
            SwipeableItemsLazyColumn(
                modifier = modifier,
                height = Int.MAX_VALUE,
                productsList = productsList,
                onEditClicked = onEditClicked,
                onAddDateClicked = onAddDateClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultIngredients(
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