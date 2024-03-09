package android.kotlin.foodclub.views.home.addIngredients

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.EditIngredientBottomModal
import android.kotlin.foodclub.utils.composables.IngredientsList
import android.kotlin.foodclub.utils.helpers.checkInternetConnectivity
import android.kotlin.foodclub.viewModels.home.discover.DiscoverEvents
import android.kotlin.foodclub.views.home.discover.DiscoverState
import android.kotlin.foodclub.views.home.discover.SubSearchBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientsView(state: DiscoverState, events: DiscoverEvents, navController: NavController) {

    val context = LocalContext.current;
    val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context));

    val screenHeight =
        LocalConfiguration.current.screenHeightDp.dp - dimensionResource(id = R.dimen.dim_160)
    var isSmallScreen by remember { mutableStateOf(false) }

    var inputText by remember { mutableStateOf("") }

    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isDialogOpen by remember { mutableStateOf(false) }

    val topTabIndex by remember { mutableIntStateOf(0) }

    if (screenHeight <= dimensionResource(id = R.dimen.dim_440)) {
        isSmallScreen = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        {
            Row(
                //horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dim_10))
                    .padding(
                        top = dimensionResource(
                            id = R.dimen.dim_20
                        )
                    )
            )
            {

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_left),
                        contentDescription = "Exit"
                    )
                }

                Text(
                    text = stringResource(id = R.string.add_ingredients),
                    fontSize = dimensionResource(
                        id = R.dimen.fon_25
                    ).value.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                )

            }

            if (isSheetOpen) {
                EditIngredientBottomModal(
                    ingredient = state.ingredientToEdit!!,
                    onDismissRequest = { isSheetOpen = it },
                    onEdit = {item ->
                        events.updateIngredient(item)
                        if (state.userIngredients.filter { it.id == item.id }.isEmpty())
                        {
                            events.addToUserIngredients(item)
                        }

                    }
                )
            }

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
                            if (date != null) {
                                selectedDate = date
                                state.ingredientToEdit!!.expirationDate = selectedDate
                                if (state.userIngredients.filter { it.id == state.ingredientToEdit.id }
                                        .isEmpty())
                                {
                                    events.addToUserIngredients(state.ingredientToEdit)
                                }
                                events.updateIngredient(state.ingredientToEdit)
                            }
                        }
                    )
                }
            }


            if (isInternetConnected) {

                if (topTabIndex == 0) {
                    SubSearchBar(
                        navController = navController,
                        searchTextValue = state.ingredientSearchText,
                        onSearch = { input ->
                            inputText = input
                            events.onAddIngredientsSearchTextChange(input)
                        }
                    )
                } else {
                    // TODO figure out what do show here
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_30)))
                }

            }

            if (isInternetConnected) {

                if (inputText.isBlank()) {

                    IngredientsList(
                        Modifier,
                        events = events,
                        productsList = state.userIngredients,
                        userIngredientsList = state.userIngredients,
                        onEditQuantityClicked = {
                            isSheetOpen = true
                        },
                        onDateClicked = {
                            isDatePickerVisible = true
                        },
                        onIngredientAdd = {},
                        onDeleteIngredient = {
                            events.deleteIngredientFromList(it)
                        }
                    )


                } else {
                    IngredientsList(
                        Modifier,
                        events = events,
                        productsList = state.productsData.productsList,
                        userIngredientsList = state.userIngredients,
                        onEditQuantityClicked = {
                            isSheetOpen = true
                        },
                        onDateClicked = {
                            isDatePickerVisible = true
                        },
                        onIngredientAdd = {
                            events.addToUserIngredients(it)
                            isDialogOpen = false
                        },
                        onDeleteIngredient = {
                            events.deleteIngredientFromList(it)
                        }
                    )
                }
            }


        }
    }
}