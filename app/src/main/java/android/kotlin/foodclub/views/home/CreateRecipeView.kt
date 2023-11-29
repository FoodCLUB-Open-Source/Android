package android.kotlin.foodclub.views.home

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.utils.composables.IngredientsBottomSheet
import android.kotlin.foodclub.utils.helpers.ValueParser
import android.kotlin.foodclub.viewModels.home.CreateRecipeViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
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
import androidx.compose.runtime.Stable
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
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BottomSheetCategories(onDismiss: () -> Unit, viewModel: CreateRecipeViewModel) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 150.dp
    var searchText by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val categories = viewModel.categories.collectAsState()
    var displayedCategories by remember { mutableStateOf(categories.value) }
    val selectedCategories = viewModel.chosenCategories.collectAsState()

    LaunchedEffect(searchText) {
        if(searchText.isEmpty()) {
            displayedCategories = categories.value
            return@LaunchedEffect
        }
        displayedCategories = categories.value.filter {
            it.name.lowercase().contains(searchText.lowercase())
        }
    }

    ModalBottomSheet(
        containerColor = Color.Black,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(Modifier.fillMaxWidth().height(screenHeight)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 17.dp),
                contentAlignment = Alignment.CenterStart) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = "Left Arrow",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Categories", color = Color.White, fontFamily = Montserrat)
                }
            }
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
                    color = Color.White,
                    modifier = Modifier
                        .alpha(0.4f)
                        .clickable { searchText = "" }
                )
                Spacer(modifier = Modifier.width(20.dp))
            }
            FlowRow {
                displayedCategories.forEachIndexed { _, category ->
                    val isSelected = selectedCategories.value.contains(category)
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 12.dp)
                            .height(46.dp),
                        colors = if (isSelected)
                                CardDefaults.cardColors(Color(0xFF7EC60B))
                            else
                                CardDefaults.cardColors(Color.Transparent),
                        onClick = {
                            if(isSelected) viewModel.unselectCategory(category)
                            else viewModel.selectCategory(category)
                        },
                        border = BorderStroke(1.dp, Color(0xFFF5F5F5))
                    ) {
                        Text(
                            text = category.name,
                            fontFamily = Montserrat,
                            fontSize = 16.sp,
                            color = Color.White,
                            letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
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
fun CreateRecipeView(navController: NavController, viewModel: CreateRecipeViewModel) {
    val title = viewModel.title.value ?: "Loading..."
    val ingredientList by viewModel.ingredients.collectAsState()
    val revealedIngredientId by viewModel.revealedIngredientId.collectAsState()
    val systemUiController = rememberSystemUiController()
    var showSheet by remember { mutableStateOf(false) }
    var showCategorySheet by remember { mutableStateOf(false) }
    val codeTriggered = remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(0f) }
    var recipeName by remember { mutableStateOf("") }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 240.dp
    val categories = viewModel.chosenCategories.collectAsState()
    val rows = listOf(
        listOf("fzfe", "fefez", "fzeffezfze"),
        listOf("Button", "Button"),
    )

    var isSmallScreen by remember { mutableStateOf(false) }

    if (screenHeight <= 440.dp) {
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

    Box(Modifier.fillMaxSize().background(Color.White)
        .padding(start = 15.dp, top = 0.dp, end = 15.dp, bottom = 70.dp)) {
        if (showSheet) {
            IngredientsBottomSheet(
                onDismiss = triggerBottomSheetModal,
                productsDataFlow = viewModel.productsDatabase,
                loadMoreObjects = { searchText, onLoadCompleted ->
                    viewModel.fetchMoreProducts(searchText, onLoadCompleted) },
                onListUpdate = { viewModel.fetchProductsDatabase(it) },
                onSave = { viewModel.addIngredient(it) }
            )
        }
        if (showCategorySheet) {
            BottomSheetCategories(triggerCategoryBottomSheetModal, viewModel)
        }


        LazyColumn(
            modifier = Modifier
                .padding(bottom = 70.dp)
                .background(Color.White)
        ) {
            item {
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
                                .border(1.dp, Color(0xFFB8B8B8), shape = RoundedCornerShape(15.dp))
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
                            "Add my recipe’s name", fontFamily = Montserrat, fontSize = 15.sp,
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
                        .height(80.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Serving Size: ${sliderPosition.toInt()}",
                        fontFamily = Montserrat,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Slider(
                        modifier = Modifier
                            .width(if (isSmallScreen) 150.dp else 200.dp),
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        valueRange = 0f..10f,
                        steps = 10,
                        colors = SliderDefaults.colors(
                            thumbColor = foodClubGreen,
                            activeTrackColor = Color(0xFFD9D9D9),
                            inactiveTrackColor = Color(0xFFD9D9D9)
                        ),
                    )
                }
                Divider(
                    color = Color(0xFFE8E8E8),
                    thickness = 1.dp
                )
                Spacer(modifier = Modifier.height(15.dp))
                SectionItem(
                    title = "Categories",
                    action = "Vegan",
                    icon = Icons.Default.KeyboardArrowDown,
                    onClick = triggerCategoryBottomSheetModal
                )
                val purpleColor = Color(0xFFA059D9)
                FlowRow {
                    categories.value.forEachIndexed { _, content ->
                        Card(
                            shape = RoundedCornerShape(30.dp),
                            modifier = Modifier
                                .padding(5.dp)
                                .height(32.dp),
                            colors = CardDefaults.cardColors(purpleColor),
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
                Spacer(modifier = Modifier.height(10.dp))
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
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(items = ingredientList, key = { it.id }) { ingredient ->
                Ingredient(ingredient = ingredient,
                    isRevealed = revealedIngredientId == ingredient.id,
                    onExpand = { viewModel.onIngredientExpanded(ingredient.id) },
                    onCollapse = { viewModel.onIngredientCollapsed(ingredient.id) },
                    onDelete = { viewModel.onIngredientDeleted(ingredient) }
                    )
            }
        }

        Button(
            shape = RectangleShape,
            modifier = Modifier
                .border(1.dp, Color(126, 198, 11, 255), shape = RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(126, 198, 11, 255),
                contentColor = Color.White
            ), contentPadding = PaddingValues(15.dp),
            onClick = { /* Create Recipe */ }
        ) {
            Text("Share Recipe", color = Color.White, fontFamily = Montserrat, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun Ingredient(ingredient: Ingredient, isRevealed: Boolean, onExpand: () -> Unit, onCollapse: () -> Unit,
               onDelete: () -> Unit) {
    val ingredientXOffset = remember { mutableStateOf(0f) }
    var showItem by remember { mutableStateOf(true) }

    val transitionState = remember { MutableTransitionState(isRevealed).apply { targetState = !isRevealed }}
    val transition = updateTransition(transitionState, label = "")
    val offsetTransition by transition.animateFloat(
        label = "ingredientOffsetTransition",
        transitionSpec = { tween(durationMillis = 500) },
        targetValueByState = { if (isRevealed) (-ingredientXOffset.value - 200f) else -ingredientXOffset.value }
    )

    var quantity by remember { mutableStateOf(ingredient.quantity) }
    val type by remember { mutableStateOf(ingredient.type) }
    val unit by remember { mutableStateOf(ingredient.unit) }


    AnimatedVisibility(visible = showItem, exit = shrinkOut(shrinkTowards = Alignment.TopCenter)) {
        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(end = 15.dp)) {
            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .border(1.dp, Color(0xFFF5F5F5), shape = RoundedCornerShape(22.dp))
                    .clip(RoundedCornerShape(22.dp))
                    .width(50.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF5F5F5),
                    contentColor = Color.White
                ), contentPadding = PaddingValues(5.dp),
                onClick = { showItem = false }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.delete_bin_5_line__2_),
                    contentDescription = "Back",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }
        }
        Column {
            Box(modifier = Modifier
                .offset {
                    IntOffset((ingredientXOffset.value + offsetTransition).roundToInt(), 0)
                }
                .pointerInput("") {
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
                .height(100.dp)
                .border(
                    1.dp, Color(0xFFE8E8E8), shape = RoundedCornerShape(15.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(10.dp)
            ) {
                AsyncImage(
                    model = ingredient.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .clip(RoundedCornerShape(12.dp)))
                Box(
                    modifier = Modifier
                        .padding(start = 95.dp)
                        .fillMaxWidth()
                        .height(70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box ( modifier = Modifier
                        .width(105.dp)
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
                                contentDescription = "",
                                modifier = Modifier
                                    .size(35.dp).padding(end = 5.dp)
                                    .clickable {
                                        ingredient.decrementQuantity(5)
                                        quantity = ingredient.quantity
                                    }
                            )
                            Text(
                                quantity.toString() + ValueParser.quantityUnitToString(unit),
                                color = Color.Black,
                                fontFamily = Montserrat,
                                fontSize = 14.sp
                            )
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(35.dp).padding(start = 5.dp)
                                    .clickable {
                                        ingredient.incrementQuantity(5)
                                        quantity = ingredient.quantity
                                    }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
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
            .height(50.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(title, fontFamily = Montserrat, fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .border(
                        1.dp,
                        Color(126, 198, 11, 255),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .width(120.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(126, 198, 11, 255)
                ), contentPadding = PaddingValues(15.dp),
                onClick = {
                    onClick()
                }
            ) {
                Text(
                    "Add +",
                    color = Color(126, 198, 11, 255),
                    fontFamily = Montserrat,
                    fontSize = 14.sp
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
            .padding(16.dp)
    ) {
        TextField(
            value = recipeTitle,
            onValueChange = { newValue -> recipeTitle = newValue },
            label = { Text("Recipe Title") }
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val recipe = Recipe(
                    title = recipeTitle,
                    description = recipeDescription,
                    preparationTime = preparationTime.toIntOrNull() ?: 0,
                    servingSize = servingSize.toIntOrNull() ?: 0,
                    category = category
                )

                coroutineScope.launch {
                    val success = viewModel.createRecipe(recipe, "user_id_here")

                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Recipe")
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

    val newRecipe = Recipe(
        title = recipeTitle,
        description = recipeDescription,
        preparationTime = preparationTime.toIntOrNull() ?: 0,
        servingSize = servingSize.toIntOrNull() ?: 0,
        category = category,

        )
}

@Composable
@Preview
fun CreateRecipeViewPreview() {
    val navController = rememberNavController()
    val viewModel: CreateRecipeViewModel = hiltViewModel()
    CreateRecipeView(navController, viewModel)
}