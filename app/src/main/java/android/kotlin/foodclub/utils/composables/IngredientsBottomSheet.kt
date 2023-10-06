package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.Ingredient
import android.kotlin.foodclub.data.models.ProductsData
import android.kotlin.foodclub.ui.theme.Montserrat
import android.kotlin.foodclub.utils.enums.DrawerContentState
import android.kotlin.foodclub.utils.enums.QuantityUnit
import android.kotlin.foodclub.utils.helpers.ValueParser
import android.kotlin.foodclub.views.home.montserratFamily
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun IngredientsBottomSheet(onDismiss: () -> Unit, productsDataFlow: StateFlow<ProductsData>,
                           onSave: (ingredient: Ingredient) -> Unit = {}) {

    var editedIngredient by remember { mutableStateOf<Ingredient?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 150.dp

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val contentState = remember { mutableStateOf(DrawerContentState.IngredientListContent) }
    var savedSearchText by remember { mutableStateOf("") }


    ModalBottomSheet(
        containerColor = Color.Black,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        AnimatedContent(
            targetState = contentState, transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState.value == DrawerContentState.IngredientListContent) {
                                keyframes {
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }

            }, label = ""
        ) { contentState ->
            when (contentState.value) {
                DrawerContentState.IngredientListContent -> {
                    IngredientListView(
                        screenHeight = screenHeight,
                        savedSearchText = savedSearchText,
                        productsDataFlow = productsDataFlow,
                        onDismiss = {
                            coroutineScope.launch {
                                bottomSheetState.hide()
                                onDismiss()
                            }
                        },
                        onIngredientSelect = { ingredient, searchText ->
                            editedIngredient = ingredient
                            savedSearchText = searchText
                            contentState.value = DrawerContentState.IngredientAmountSelection
                        }
                    )
                }

                DrawerContentState.IngredientAmountSelection -> {
                    if(editedIngredient != null) {
                        IngredientSelectedView(
                            screenHeight = screenHeight,
                            selectedIngredient = editedIngredient!!,
                            onDismiss = {
                                contentState.value = DrawerContentState.IngredientListContent
                            },
                            onSave = {
                                onSave(it)
                                coroutineScope.launch {
                                    bottomSheetState.hide()
                                    onDismiss()
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun IngredientSelectedView(
    screenHeight: Dp, selectedIngredient: Ingredient,
    onDismiss: () -> Unit, onSave: (ingredient: Ingredient) -> Unit
) {
    val valuesPickerState = rememberPickerState()
    val pickerValues = remember { mutableStateOf((1..99).map { (it * 10).toString() +
            ValueParser.quantityUnitToString(selectedIngredient.unit) } ) }

    Box(
        modifier = Modifier.fillMaxWidth().height(screenHeight)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 17.dp, end = 17.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier.height(50.dp).fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                    contentDescription = "Left Arrow",
                    modifier = Modifier.size(24.dp).align(Alignment.CenterStart)
                        .clickable(onClick = { onDismiss() })
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Add items",
                    color = Color.White,
                    fontFamily = Montserrat,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize().padding(top = 30.dp, bottom = 40.dp)
            ) {
                AsyncImage(
                    model = selectedIngredient.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(130.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(35.dp))
                Picker(
                    state = valuesPickerState,
                    items = pickerValues.value,
                    visibleItemsCount = 3,
                    modifier = Modifier.weight(0.3f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        color = Color(android.graphics.Color.parseColor("#545454"))
                    )
                )
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color(126, 198, 11, 255),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(RoundedCornerShape(15.dp)).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(126, 198, 11, 255),
                        contentColor = Color.White
                    ), contentPadding = PaddingValues(15.dp),
                    onClick = {
                        onSave(
                            Ingredient(
                                selectedIngredient.id,
                                selectedIngredient.type,
                                ValueParser.quantityStringToInt(
                                    valuesPickerState.selectedItem,
                                    selectedIngredient.unit
                                ),
                                selectedIngredient.unit,
                                selectedIngredient.imageUrl
                            )
                        )
                    }
                ) {
                    Text(
                        text = "Save",
                        color = Color.White,
                        fontFamily = Montserrat,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientListView(
    screenHeight: Dp,
    savedSearchText: String,
    productsDataFlow: StateFlow<ProductsData>,
    onDismiss: () -> Unit,
    onIngredientSelect: (ingredient: Ingredient, searchText: String) -> Unit
) {
    val productsData = productsDataFlow.collectAsState()
    var searchText by remember { mutableStateOf(savedSearchText) }
    var showingList by remember { mutableStateOf(
        if(searchText.length > 3) {
            productsData.value.productsList.filter { it.type.lowercase().contains(searchText) }
        } else {
            productsData.value.productsList
        }
    ) }


    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.clickable { onDismiss() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                    contentDescription = "Close Sheet",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Add items",
                    color = Color.White,
                    fontFamily = Montserrat
                )
            }
        }

        LazyColumn(modifier = Modifier.padding(top = 30.dp)) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text(text = "Search here", fontSize = 15.sp) },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { /* Handle onDone event if needed */ }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(51.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Clear",
                        color = Color(0x00545454),
                        modifier = Modifier.clickable { searchText = "" }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            items(
                items = showingList,
                key = { it.id }
            ) {
                if(showingList.contains(it)){
                    IngredientComposable(
                        ingredient = it,
                        onClick = { ingredient -> onIngredientSelect(ingredient, searchText) }
                    )
                }

            }
        }
    }

    LaunchedEffect(searchText) {
        delay(1500)
        showingList = if(searchText.length > 3) {
            productsData.value.productsList.filter { it.type.lowercase().contains(searchText) }
        } else {
            productsData.value.productsList
        }
    }
}

@Composable
fun IngredientComposable(
    ingredient: Ingredient,
    onClick: (Ingredient) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 20.dp, top = 20.dp)
            .clickable(onClick = { onClick(ingredient) })
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = ingredient.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = ingredient.type,
                color = Color.White,
                fontFamily = montserratFamily
            )
        }
    }
}
