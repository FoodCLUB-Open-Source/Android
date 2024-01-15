package android.kotlin.foodclub.views.home.scan

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.BottomBarScreenObject
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.LoadingProgressBar
import android.kotlin.foodclub.viewModels.home.discover.DiscoverEvents
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanResultView(
    navController: NavController,
    events: DiscoverEvents,
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

    var loading by rememberSaveable { mutableStateOf(false) }
    BackHandler {
        navController.popBackStack()

    }
    Box(modifier =Modifier.fillMaxSize() ) {
        if(loading) {
            LoadingProgressBar(
                text= stringResource(id = R.string.uploading),
                route = BottomBarScreenObject.Play.route
                , navController = navController
            )
        }else{
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.scan_results_list),
                                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = dimensionResource(id = R.dimen.fon_48).value.sp,
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
                                    events.addScanListToUserIngredients(state.scanResultItemList)

                                }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.next),
                                    color = foodClubGreen,
                                    fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
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
                            topBarTitleText = stringResource(id = R.string.edit_item)
                            EditIngredientView(
                                ingredient = state.ingredientToEdit!!,
                                onEditIngredient = { ingr ->
                                    events.updateIngredient(ingr)
                                },
                                onDeleteIngredient = {ingr ->
                                    //TODO add delete ingredient functionality here if needed
                                }
                            )
                        }else{
                            topBarTitleText = stringResource(id = R.string.all_my_ingredients)
                            SearchResultIngredients(
                                modifier = Modifier,
                                searchTextValue = searchText,
                                onSearch = { input->
                                    events.onAddIngredientsSearchTextChange(input)
                                }
                            )
                            Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dim_15)))

                            ScanResultList(
                                modifier = modifier,
                                productsList = state.scanResultItemList,
                                onAddDateClicked = { ingredient->
                                    events.updateIngredient(ingredient)
                                    isDatePickerVisible = true
                                                   },
                                onEditClicked = {
                                        item->
                                    events.updateIngredient(item)
                                    isShowEditScreen = false
                                },
                                view = stringResource(id = R.string.digitalPantry)
                            )

                            if (isDatePickerVisible) {
                                Box(
                                    modifier = Modifier,
                                    contentAlignment = Alignment.Center
                                ) {
                                    CustomDatePicker(
                                        modifier = Modifier.shadow(dimensionResource(id = R.dimen.dim_5)),
                                        datePickerState = datePickerState,
                                        onDismiss = {
                                            isDatePickerVisible = false
                                            datePickerState.setSelection(null)
                                        },
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
    onAddDateClicked: (Ingredient) -> Unit,
    onEditClicked: (Ingredient) -> Unit,
    view: String
) {
    Surface(
        shadowElevation = dimensionResource(id = R.dimen.dim_8),
        shape = RoundedCornerShape(topStart = dimensionResource(id = R.dimen.dim_18), topEnd = dimensionResource(id = R.dimen.dim_18))
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
                    color = Color.White
                )
        ) {
            TitlesSection(modifier, view)
            SwipeableItemsLazyColumn(
                modifier = modifier,
                height = Int.MAX_VALUE,
                productsList = productsList,
                onEditClicked = onEditClicked,
                onAddDateClicked = {
                    //TODO impl add-update data functionality if needed
                },
                onDeleteIngredient = {
                    //TODO impl delete functionality if needed
                }
            )
        }
    }
}

@Composable
fun SearchResultIngredients(
    modifier: Modifier,
    searchTextValue: String,
    onSearch: (String) -> Unit
) {

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dim_10))
            .clip(
                RoundedCornerShape(dimensionResource(id = R.dimen.dim_15))
            ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        value = searchTextValue,
        onValueChange = {
            onSearch(it) 
        },
        placeholder = {
            Text(
                modifier = modifier.padding(top =dimensionResource(id = R.dimen.dim_3)),
                text = stringResource(id = R.string.search_my_ingredients),
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