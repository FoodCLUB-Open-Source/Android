package android.kotlin.foodclub.views.home.createRecipe

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.utils.composables.CustomSlider
import android.kotlin.foodclub.utils.composables.IngredientsBottomSheet
import android.kotlin.foodclub.utils.helpers.ValueParser
import android.kotlin.foodclub.viewModels.home.createRecipe.CreateRecipeEvents
import android.kotlin.foodclub.viewModels.home.createRecipe.CreateRecipeViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BottomSheetCategories(
    onDismiss: () -> Unit,
    events: CreateRecipeEvents,
    state: CreateRecipeState
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 150.dp
    var searchText by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val categories = state.categories
    var displayedCategories by remember { mutableStateOf(categories) }
    val selectedCategories = state.chosenCategories

    LaunchedEffect(searchText) {
        if(searchText.isEmpty()) {
            displayedCategories = categories
            return@LaunchedEffect
        }
        displayedCategories = categories.filter {
            it.name.lowercase().contains(searchText.lowercase())
        }
    }

    ModalBottomSheet(
        containerColor = Color.Black,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .height(screenHeight)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.dim_30),
                    end = dimensionResource(id = R.dimen.dim_17)
                ),
                contentAlignment = Alignment.CenterStart) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = "Left Arrow",
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dim_24))
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_8)))

                    Text(
                        text = "Categories",
                        color = Color.White,
                        fontFamily = Montserrat
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dim_20))
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = {
                        Text(
                            text = "Search here",
                            fontSize = dimensionResource(id = R.dimen.fon_15).value.sp
                        ) },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)),
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
                        .height(dimensionResource(id = R.dimen.dim_51))
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_20)))
                Text(
                    text = "Clear",
                    color = Color.White,
                    modifier = Modifier
                        .alpha(0.4f)
                        .clickable { searchText = "" }
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_20)))
            }
            FlowRow {
                displayedCategories.forEachIndexed { _, category ->
                    val isSelected = selectedCategories.contains(category)

                    Card(
                        shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_16)),
                        modifier = Modifier
                            .padding(
                                vertical = dimensionResource(id = R.dimen.dim_12),
                                horizontal = dimensionResource(id = R.dimen.dim_12)
                            )
                            .height(dimensionResource(id = R.dimen.dim_46)),
                        colors = if (isSelected)
                                CardDefaults.cardColors(foodClubGreen)
                            else
                                CardDefaults.cardColors(Color.Transparent),
                        onClick = {
                            if(isSelected) events.unselectCategory(category)
                            else events.selectCategory(category)
                        },
                        border = BorderStroke(dimensionResource(id = R.dimen.dim_1), containerColor)
                    ) {
                        Text(
                            text = category.name,
                            fontFamily = Montserrat,
                            fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                            color = Color.White,
                            letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
                            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.dim_12), horizontal = dimensionResource(id = R.dimen.dim_24)),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateRecipeView(
    navController: NavController,
    events: CreateRecipeEvents,
    state: CreateRecipeState
) {
    val systemUiController = rememberSystemUiController()
    var showSheet by remember { mutableStateOf(false) }
    var showCategorySheet by remember { mutableStateOf(false) }
    val codeTriggered = remember { mutableStateOf(false) }
    var servingSize by remember { mutableStateOf(0) }
    var recipeName by remember { mutableStateOf("") }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - dimensionResource(id = R.dimen.dim_240)
    var recipeDescription by remember { mutableStateOf("") }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 240.dp
    val categories = viewModel.chosenCategories.collectAsState()
    val rows = listOf(
        stringArrayResource(id = R.array.quantity_list).toList(),
        stringArrayResource(id = R.array.discover_sub_tabs).toList(),
    ) // TODO remove placeholder data

    var isSmallScreen by remember { mutableStateOf(false) }

    if (screenHeight <= dimensionResource(id = R.dimen.dim_440)) {
        isSmallScreen = true
    }
    LaunchedEffect(key1 = codeTriggered.value) {
        systemUiController.setSystemBarsColor(
                color = Color.White,
                darkIcons = true
        )
        if (!codeTriggered.value) {
            codeTriggered.value = true
        }
    }

    val triggerCategoryBottomSheetModal: () -> Unit = {
        showCategorySheet = !showCategorySheet
        systemUiController.setNavigationBarColor(
            color = if (showSheet) Color.Black else Color.White,
            darkIcons = true
        )
    }
    val triggerBottomSheetModal: () -> Unit = {
        showSheet = !showSheet
        systemUiController.setStatusBarColor(
            color = if (showSheet) Color(android.graphics.Color.parseColor("#ACACAC")) else Color.White,
            darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = if (showSheet) Color.Black else Color.White,
            darkIcons = true
        )
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                start = dimensionResource(id = R.dimen.dim_15),
                top = dimensionResource(id = R.dimen.dim_0),
                end = dimensionResource(id = R.dimen.dim_15),
                bottom = dimensionResource(id = R.dimen.dim_70)
            )
    ) {
        if (showSheet) {
            IngredientsBottomSheet(
                onDismiss = triggerBottomSheetModal,
                productsData = state.products,
                loadMoreObjects = { searchText, onLoadCompleted ->
                    events.fetchMoreProducts(searchText, onLoadCompleted) },
                onListUpdate = { events.fetchProductsDatabase(it) },
                onSave = { events.addIngredient(it) }
            )
        }
        if (showCategorySheet) {
            BottomSheetCategories(
                onDismiss = triggerCategoryBottomSheetModal,
                events = events,
                state = state
            )
        }


        LazyColumn(
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.dim_70), start = 8.dp, end = 8.dp)
                .background(Color.White)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 55.dp, bottom = 20.dp)
                            .height(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .background(Color.Transparent),
                            contentAlignment = Alignment.Center,
                        ) {
                            Button(
                                shape = RectangleShape,
                                modifier = Modifier
                                    .border(
                                        1.dp,
                                        Color(0xFFB8B8B8),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .clip(RoundedCornerShape(15.dp))
                                    .align(Alignment.BottomCenter)
                                    .width(40.dp)
                                    .height(40.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFB8B8B8),
                                    contentColor = Color.White
                                ), contentPadding = PaddingValues(5.dp),
                                onClick = { navController.navigateUp() }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                                    contentDescription = "Back",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)
                                )
                            }
                        }
                        Text(
                            "My New Recipe",
                            modifier = Modifier.padding(start = 8.dp),
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = TextUnit(-1.12f, TextUnitType.Sp),
                            fontSize = 28.sp
                        )
                    }
                    OutlinedTextField(
                        value = recipeName,
                        onValueChange = { recipeName = it },
                        placeholder = {
                            Text(
                                "Add recipe’s name", fontFamily = Montserrat, fontSize = 15.sp,
                                color = Color(0xFFB3B3B3)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color(0xFFE8E8E8)
                        )
                    )
                    OutlinedTextField(
                        value = recipeDescription,
                        onValueChange = { recipeDescription = it },
                        placeholder = {
                            Text(
                                "Add a description", fontFamily = Montserrat, fontSize = 15.sp,
                                color = Color(0xFFB3B3B3)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color(0xFFE8E8E8)
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Serving Size",
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = TextUnit(-0.72f, TextUnitType.Sp),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        CustomSlider(
                            sliderWidth = if (isSmallScreen) 150.dp else 200.dp,
                            maxValue = 10f,
                            onValueChange = { servingSize = it }
                        )
                    }
                    SectionItem(
                        title = "Categories",
                        action = "Vegan",
                        icon = Icons.Default.KeyboardArrowDown,
                        onClick = triggerCategoryBottomSheetModal
                    )
                    FlowRow {
                        categories.value.forEachIndexed { _, content ->
                            Card(
                                shape = RoundedCornerShape(30.dp),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .height(32.dp),
                                colors = CardDefaults.cardColors(foodClubGreen),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(
                                        text = content.name,
                                        fontFamily = Montserrat,
                                        fontSize = 12.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(8.dp),
                                        maxLines = 1
                                    )
                                    Button(
                                        modifier = Modifier
                                            .border(2.dp, Color.White, shape = CircleShape)
                                            .size(22.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent,
                                            contentColor = Color.White
                                        ), contentPadding = PaddingValues(0.dp),
                                        shape = CircleShape,
                                        onClick = { viewModel.unselectCategory(content) }
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.baseline_clear_24),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(15.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Divider(
                        color = Color(0xFFE8E8E8),
                        thickness = 1.dp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Ingredients", fontFamily = Montserrat, fontSize = 14.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            shape = RectangleShape,
                            modifier = Modifier
                                .border(1.dp, foodClubGreen, RoundedCornerShape(20.dp))
                                .clip(RoundedCornerShape(20.dp))
                                .width(120.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(126, 198, 11, 255)
                            ), contentPadding = PaddingValues(15.dp),

                            onClick = { triggerBottomSheetModal() }
                        ) {
                            Text(
                                "Add +",
                                color = Color(126, 198, 11, 255),
                                fontFamily = Montserrat,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                }

            }
            items(items = state.ingredients, key = { it.id }) { ingredient ->
                Ingredient(
                    ingredient = ingredient,
                    isRevealed = state.revealedIngredientId == ingredient.id,
                    onExpand = { events.onIngredientExpanded(ingredient.id) },
                    onCollapse = { events.onIngredientCollapsed(ingredient.id) },
                    onDelete = { events.onIngredientDeleted(ingredient) }
                    )
            }
        }

        Button(
            shape = RectangleShape,
            modifier = Modifier
                .border(dimensionResource(id = R.dimen.dim_1), Color(126, 198, 11, 255), shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
                .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(126, 198, 11, 255),
                contentColor = Color.White
            ), contentPadding = PaddingValues( dimensionResource(id = R.dimen.dim_15)),
            onClick = { /* Create Recipe */ }
        ) {
            Text(
                text = stringResource(id = R.string.share_recipe),
                color = Color.White,
                fontFamily = Montserrat,
                fontSize = dimensionResource(id = R.dimen.fon_16).value.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun Ingredient(
    ingredient: Ingredient,
    isRevealed: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onDelete: () -> Unit
) {
    val ingredientXOffset = remember { mutableStateOf(0f) }
    var showItem by remember { mutableStateOf(true) }

    val transitionState = remember { MutableTransitionState(isRevealed).apply { targetState = !isRevealed }}
    val transition = updateTransition(transitionState, label = "")
    val offsetTransition by transition.animateFloat(
        label = stringResource(id = R.string.ingredient_offset_transitions),
        transitionSpec = { tween(durationMillis = 500) },
        targetValueByState = { if (isRevealed) (-ingredientXOffset.value - 200f) else -ingredientXOffset.value }
    )

    var quantity by remember { mutableStateOf(ingredient.quantity) }
    val type by remember { mutableStateOf(ingredient.type) }
    val unit by remember { mutableStateOf(ingredient.unit) }


    AnimatedVisibility(
        visible = showItem,
        exit = shrinkOut(shrinkTowards = Alignment.TopCenter)
    ) {
        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dim_100))
            .padding(end = dimensionResource(id = R.dimen.dim_15))) {
            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .border(dimensionResource(id = R.dimen.dim_1), containerColor, shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_22)))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_22)))
                    .width(dimensionResource(id = R.dimen.dim_50))
                    .height(dimensionResource(id = R.dimen.dim_50)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = containerColor,
                    contentColor = Color.White
                ), contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_5)),
                onClick = { showItem = false }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.delete_bin_5_line__2_),
                    contentDescription = stringResource(id = R.string.go_back),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dim_20))
                        .height(dimensionResource(id = R.dimen.dim_20))
                )
            }
        }
        Column {
            Box(modifier = Modifier
                .offset {
                    IntOffset((ingredientXOffset.value + offsetTransition).roundToInt(), 0)
                }
                .pointerInput(key1 = "") {
                    detectHorizontalDragGestures { change, dragAmount ->
                        val original = Offset(ingredientXOffset.value, 0f)
                        val summed = original + Offset(x = dragAmount, y = 0f)
                        val newValue = Offset(summed.x.coerceIn(-200f, 0f), 0f)
                        if (newValue.x <= -20f) {
                            onExpand()
                            return@detectHorizontalDragGestures
                        } else if (newValue.x >= 0) {
                            onCollapse()
                            return@detectHorizontalDragGestures
                        }
                        if (change.positionChange() != Offset.Zero) change.consume()
                        ingredientXOffset.value = newValue.x
                    }
                }
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dim_100))
                .border(
                    dimensionResource(id = R.dimen.dim_1), Color(0xFFE8E8E8), shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))
                )
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
                .background(Color.White)
                .padding(dimensionResource(id = R.dimen.dim_10))
            ) {
                AsyncImage(
                    model = ingredient.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dim_80))
                        .width(dimensionResource(id = R.dimen.dim_80))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12))))
                Box(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.dim_95))
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.dim_70)),
                    contentAlignment = Alignment.Center
                ) {
                    Box ( modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dim_105))
                        .align(Alignment.CenterStart) ) {
                        Text(
                            text = type,
                            modifier = Modifier.align(Alignment.TopStart),
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Box ( modifier = Modifier.align(Alignment.CenterEnd) ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size( dimensionResource(id = R.dimen.dim_35)).padding(end =dimensionResource(id = R.dimen.dim_5))
                                    .clickable {
                                        ingredient.decrementQuantity(5)
                                        quantity = ingredient.quantity
                                    }
                            )
                            Text(
                                text = quantity.toString() + ValueParser.quantityUnitToString(unit),
                                color = Color.Black,
                                fontFamily = Montserrat,
                                fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                            )
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size( dimensionResource(id = R.dimen.dim_35)).padding(start =dimensionResource(id = R.dimen.dim_5))
                                    .clickable {
                                        ingredient.incrementQuantity(5)
                                        quantity = ingredient.quantity
                                    }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
        }
    }

    LaunchedEffect(showItem) {
        if(!showItem) {
            delay(800)
            onDelete()
        }
    }
}

@Composable
fun SectionItem(
    title: String,
    action: String,
    icon: ImageVector?,
    actionColor: Color = Color.Black,
    onClick: () -> Unit
) {
        Row ( modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dim_50)), verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = title,
                fontFamily = Montserrat,
                fontSize = dimensionResource(id = R.dimen.fon_14).value.sp)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .border(
                        dimensionResource(id = R.dimen.dim_1),
                        Color(126, 198, 11, 255),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_20))
                    )
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
                    .width(dimensionResource(id = R.dimen.dim_120)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(126, 198, 11, 255)
                ),
                contentPadding = PaddingValues( dimensionResource(id = R.dimen.dim_15)),
                onClick = {
                    onClick()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.add_items_plus),
                    color = Color(126, 198, 11, 255),
                    fontFamily = Montserrat,
                    fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                )
            }
        }
}

@Composable
fun CreateRecipe(viewModel: CreateRecipeViewModel) {
    var recipeTitle by remember { mutableStateOf("") }
    val recipeDescription by remember { mutableStateOf("") }
    val preparationTime by remember { mutableStateOf("") }
    val servingSize by remember { mutableStateOf("") }
    val category by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding( dimensionResource(id = R.dimen.dim_16))
    ) {
        TextField(
            value = recipeTitle,
            onValueChange = { newValue -> recipeTitle = newValue },
            label = { Text(text = stringResource(id = R.string.recipe_title)) }
        )


        Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dim_16)))

        Button(
            onClick = {
//                val recipe = Recipe(
//                    title = recipeTitle,
//                    description = recipeDescription,
//                    preparationTime = preparationTime.toIntOrNull() ?: 0,
//                    servingSize = servingSize.toIntOrNull() ?: 0,
//                    category = category
//                )
//
//                coroutineScope.launch {
//                    val success = viewModel.createRecipe(recipe, "user_id_here")
//
//                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.create_a_recipe))
        }
    }
}

fun onRecipeSubmit(
    recipeTitle: String,
    recipeDescription: String,
    preparationTime: String,
    servingSize: String,
    category: String
) {

//    val newRecipe = Recipe(
//        title = recipeTitle,
//        description = recipeDescription,
//        preparationTime = preparationTime.toIntOrNull() ?: 0,
//        servingSize = servingSize.toIntOrNull() ?: 0,
//        category = category,
//
//        )
}

@Composable
@Preview
fun CreateRecipeViewPreview() {
    val navController = rememberNavController()
    val viewModel: CreateRecipeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    CreateRecipeView(
        navController,
        viewModel,
        state.value
    )
}