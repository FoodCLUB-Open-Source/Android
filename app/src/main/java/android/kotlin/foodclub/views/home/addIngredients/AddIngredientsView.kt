package android.kotlin.foodclub.views.home.addIngredients

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.utils.composables.ActionType
import android.kotlin.foodclub.utils.composables.CustomDatePicker
import android.kotlin.foodclub.utils.composables.EditIngredientBottomModal
import android.kotlin.foodclub.utils.composables.IngredientsList
import android.kotlin.foodclub.viewModels.home.discover.DiscoverEvents
import android.kotlin.foodclub.views.home.discover.DiscoverState
import android.kotlin.foodclub.views.home.discover.MyIngredientsSearchBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientsView(
    state: DiscoverState,
    events: DiscoverEvents,
    navController: NavController
) {
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isDialogOpen by remember { mutableStateOf(false) }

    val topTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_left),
                        contentDescription = stringResource(id = R.string.go_back),
                        Modifier.size(dimensionResource(id = R.dimen.dim_20))
                    )
                }

                Text(
                    text = stringResource(id = R.string.add_ingredients),
                    fontSize = dimensionResource(
                        id = R.dimen.fon_20
                    ).value.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight(600)
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                if (isSheetOpen) {
                    EditIngredientBottomModal(
                        ingredient = state.ingredientToEdit!!,
                        onDismissRequest = { boolean->
                            isSheetOpen = boolean },
                        onEdit = { item ->
                            events.updateIngredient(item)
                            if (state.userIngredients.none { ingredient->
                                    ingredient.id == item.id
                            }) {
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
                                    if (state.userIngredients.none { ingredient->
                                        ingredient.id == state.ingredientToEdit.id
                                    }) {
                                        events.addToUserIngredients(state.ingredientToEdit)
                                    }
                                    events.updateIngredient(state.ingredientToEdit)
                                }
                            }
                        )
                    }
                }


                if (topTabIndex == 0) {
                    MyIngredientsSearchBar(
                        navController = navController,
                        searchTextValue = state.ingredientSearchText,
                        onSearch = { input ->
                            events.onAddIngredientsSearchTextChange(input)
                        },
                        actionType = ActionType.ADD_INGREDIENTS_VIEW
                    )
                }

                IngredientsList(
                    Modifier,
                    events = events,
                    productsList = state.productsData.productsList,
                    userIngredientsList = state.userIngredients,
                    onEditQuantityClicked = {item ->
                        val ingredient = state.userIngredients.find {it.id == item.id}
                        if (ingredient != null)
                        {
                            events.updateIngredient(ingredient)
                        }
                        else
                        {
                            events.updateIngredient(item)
                        }
                        /*
                        state.userIngredients.find {it.id == item.id}
                            ?.let { it1 -> events.updateIngredient(it1) }
                            */
                        isSheetOpen = true
                    },
                    onDateClicked = {item ->
                        val ingredient = state.userIngredients.find {it.id == item.id}
                        if (ingredient != null)
                        {
                            events.updateIngredient(ingredient)
                        }
                        else
                        {
                            events.updateIngredient(item)
                        }
                        /*
                        state.userIngredients.find {it.id == item.id}
                            ?.let { it1 -> events.updateIngredient(it1) }
                            */
                        isDatePickerVisible = true
                    },
                    onIngredientAdd = { ingredientToAdd->
                        events.addToUserIngredients(ingredientToAdd)
                        isDialogOpen = false
                    },
                    onDeleteIngredient = { ingredientToDelete->
                        events.deleteIngredientFromList(ingredientToDelete)
                    },
                    actionType = ActionType.ADD_INGREDIENTS_VIEW
                )
            }
        }
    )
}