package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.utils.composables.Picker
import android.kotlin.foodclub.utils.composables.rememberPickerState
import android.kotlin.foodclub.viewmodels.home.CreateRecipeViewModel
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntSize
import java.util.Collections.copy

@Stable
data class Colors(
    val statusBarColor: Color,
    val navBarColor: Color,
) {
    private val animationSpec: AnimationSpec<Color> = tween(durationMillis = 1500)

    @Composable
    private fun animateColor(
        targetValue: Color,
        finishedListener: ((Color) -> Unit)? = null
    ) = animateColorAsState(targetValue = targetValue, animationSpec = animationSpec).value

    @Composable
    fun switch() = copy(
        statusBarColor = animateColor(statusBarColor),
        navBarColor = animateColor(navBarColor),
    )
}

val montserratFamily = FontFamily(Font(R.font.montserratregular, FontWeight.Normal))

enum class DrawerContentState {
    IngredientListContent,
    IngredientAmountSelection
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCategories(onDismiss: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 150.dp
    var searchText by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val rows = listOf(
        listOf("fzfe", "fefez", "fzeffezfze"),
        listOf("Button", "Button", "fzefze"),
        listOf("Button", "fzefe", "Button"),
        listOf("Button", "fzefezfzf", "Button"),
        listOf("fzefezfez", "Button", "Button")
    )
    val selectedButtonsState = remember { mutableStateListOf(*BooleanArray(rows.size * 3) { false }.toTypedArray()) }

    ModalBottomSheet(
        containerColor = Color.Black,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

        Box( modifier = Modifier.fillMaxWidth().height(screenHeight) ) {
            Box(modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 17.dp),
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
                    Text(text = "Categories", color = Color.White, fontFamily = montserratFamily)
                }
            }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(1),
                            contentPadding = PaddingValues(8.dp),
                            modifier = Modifier.padding(top = 50.dp, start = 25.dp, end = 25.dp)
                        )  {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp)
                                ) {
                                    TextField(
                                        value = searchText,
                                        onValueChange = { searchText = it },
                                        placeholder = {
                                            Text(text = "Search here", fontSize = 15.sp)
                                        },
                                        shape = RoundedCornerShape(12.dp),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Done
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onDone = { }
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
                                        color = Color(android.graphics.Color.parseColor("#545454")),
                                        modifier = Modifier.clickable { searchText = "" }
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                }
                            }
                            items(rows.size) { rowIndex ->
                                val row = rows[rowIndex]

                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    row.forEachIndexed { buttonIndex, buttonText ->
                                        val flatIndex = rowIndex * 3 + buttonIndex
                                        val isSelected = selectedButtonsState[flatIndex]

                                        Button(
                                            modifier = Modifier
                                                .border(1.dp, Color.White, shape = RoundedCornerShape(15.dp))
                                                .wrapContentWidth()
                                                .clip(RoundedCornerShape(15.dp)),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (isSelected) Color(126, 198, 11, 255) else Color.Transparent,
                                                contentColor = Color.White
                                            ), contentPadding = PaddingValues(15.dp),
                                            shape = RoundedCornerShape(15.dp),

                                            onClick = {
                                                selectedButtonsState[flatIndex] = !isSelected
                                            }
                                        ) {
                                            val displayText = if (buttonText.length >= 10) {
                                                buttonText.substring(0, 8) + ".."
                                            } else {
                                                buttonText
                                            }
                                            Text(text = displayText, modifier = Modifier.padding(start = 1.dp, end = 1.dp))
                                        }
                                        Spacer(modifier = Modifier.width(20.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(70.dp))
                            }
                        }
                }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetIngredients(onDismiss: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 150.dp
    var searchText by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var contentState = remember { mutableStateOf(DrawerContentState.IngredientListContent) }
    val values = remember { (1..99).map { (it * 10).toString() + "g" } }
    val valuesPickerState = rememberPickerState()

    ModalBottomSheet(
        containerColor = Color.Black,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        AnimatedContent(targetState = contentState, transitionSpec = {
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

        }
        ) { contentState ->
            when (contentState.value) {
                DrawerContentState.IngredientListContent -> {
                    Box( modifier = Modifier.fillMaxWidth().height(screenHeight) ) {
                        Box(modifier = Modifier.fillMaxWidth().padding(start = 17.dp, end = 17.dp),
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
                                Text(text = "Add items", color = Color.White, fontFamily = montserratFamily)
                            }
                        }
                        LazyColumn( modifier = Modifier.padding(top = 30.dp)) {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                                ) {
                                    TextField(
                                        value = searchText,
                                        onValueChange = { searchText = it },
                                        placeholder = {
                                            Text(text = "Search here", fontSize = 15.sp)
                                        },
                                        shape = RoundedCornerShape(12.dp),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Done
                                        ),
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
                                        color = Color(android.graphics.Color.parseColor("#545454")),
                                        modifier = Modifier.clickable { searchText = "" }
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                }
                            }
                            items(5) {
                                AddItemComposable("Tomato paste", R.drawable.story_user, onClick = {
                                    contentState.value = DrawerContentState.IngredientAmountSelection
                                })
                            }
                        }
                    }
                }
                DrawerContentState.IngredientAmountSelection -> {
                    Box( modifier = Modifier.fillMaxWidth().height(screenHeight) ) {
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
                                    modifier = Modifier.size(24.dp).align(Alignment.CenterStart).clickable(onClick = {
                                        contentState.value = DrawerContentState.IngredientListContent
                                    })
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Add items",
                                    color = Color.White,
                                    fontFamily = montserratFamily,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxSize().padding(top = 30.dp, bottom = 40.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.tomato_ingredient),
                                        contentDescription = "Circular Image",
                                        modifier = Modifier
                                            .size(130.dp)
                                            .clip(CircleShape)
                                    )
                                    Spacer(modifier = Modifier.height(35.dp))
                                    Picker(
                                        state = valuesPickerState,
                                        items = values,
                                        visibleItemsCount = 3,
                                        modifier = Modifier.weight(0.3f),
                                        textModifier = Modifier.padding(8.dp),
                                        textStyle = TextStyle(fontSize = 20.sp, color = Color(android.graphics.Color.parseColor("#545454")))
                                    )
                                    Button(
                                        shape = RectangleShape,
                                        modifier = Modifier
                                            .border(1.dp, Color(126, 198, 11, 255), shape = RoundedCornerShape(15.dp))
                                            .clip(RoundedCornerShape(15.dp))
                                            .fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(126, 198, 11, 255),
                                            contentColor = Color.White
                                        ), contentPadding = PaddingValues(15.dp),
                                        onClick = {}
                                    ) {
                                        Text("Save", color = Color.White, fontFamily = montserratFamily, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                }
                            }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeView() {
    val viewModel: CreateRecipeViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    val ingredientList = listOf("Tomato paste", "Potato wedges", "Pasta")
    val systemUiController = rememberSystemUiController()
    var showSheet by remember { mutableStateOf(false) }
    var showCategorySheet by remember { mutableStateOf(false) }
    val codeTriggered = remember { mutableStateOf(false) }
    /*val systemBarsColorsLight = Colors(
        statusBarColor = Color.White,
        navBarColor = Color.White,
    )
    val systemBarsColorsDark = Colors(
        statusBarColor = Color(android.graphics.Color.parseColor("#ACACAC")),
        navBarColor = Color.Black,
    )*/

    //val animatedColors = (if (showSheet) systemBarsColorsLight else systemBarsColorsDark).switch()

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
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 15.dp, top = 0.dp, end = 15.dp, bottom = 70.dp)
    ) {
        if (showSheet) {
            BottomSheetIngredients(triggerBottomSheetModal)
        }
        if (showCategorySheet) {
            BottomSheetCategories(triggerCategoryBottomSheetModal)
        }

        if (ingredientList != null) {
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
                            onClick = {}
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
                        "New Recipe",
                        modifier = Modifier.padding(start = 8.dp),
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                }
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(
                            "Add a caption", fontFamily = montserratFamily, fontSize = 14.sp,
                            color = Color(android.graphics.Color.parseColor("#B3B3B3"))
                        )
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.story_user),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(10.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color(android.graphics.Color.parseColor("#E8E8E8"))
                    )
                )
                SectionItem(
                    title = "Add Categories",
                    action = "Vegan",
                    icon = Icons.Default.KeyboardArrowDown,
                    onClick = triggerCategoryBottomSheetModal
                )
                Divider(
                    color = Color(android.graphics.Color.parseColor("#E8E8E8")),
                    thickness = 1.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Add Serving Size", fontFamily = montserratFamily, fontSize = 14.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(50.dp)
                                .padding(end = 15.dp)
                        )
                        Text(
                            "1",
                            color = Color.Black,
                            fontFamily = montserratFamily,
                            fontSize = 14.sp
                        )
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(50.dp)
                                .padding(start = 15.dp)
                        )
                    }
                }
                Divider(
                    color = Color(android.graphics.Color.parseColor("#E8E8E8")),
                    thickness = 1.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Ingredients", fontFamily = montserratFamily, fontSize = 14.sp)
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
                            .width(145.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(126, 198, 11, 255)
                        ), contentPadding = PaddingValues(15.dp),
                        onClick = {
                            triggerBottomSheetModal()
                        }
                    ) {
                        Text(
                            "Add items +",
                            color = Color(126, 198, 11, 255),
                            fontFamily = montserratFamily,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(ingredientList) { ingredient ->
                Ingredient(ingredient)
            }
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
            onClick = {}
        ) {
            Text("Share Recipe", color = Color.White, fontFamily = montserratFamily, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun Ingredient(ingredient: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .border(
                1.dp,
                Color(android.graphics.Color.parseColor("#E8E8E8")),
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.story_user),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .width(130.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Box(
            modifier = Modifier
                .padding(start = 140.dp, top = 10.dp)
                .fillMaxSize()
        ) {
            Box ( ) {
                Text(
                    text = ingredient,
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    fontWeight = FontWeight.Normal
                )
            }
            Box ( modifier = Modifier.align(Alignment.BottomEnd) ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 15.dp)
                    )
                    Text(
                        "200g",
                        color = Color.Black,
                        fontFamily = montserratFamily,
                        fontSize = 14.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = 15.dp)
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
fun AddItemComposable(
    ingredientName: String,
    ingredientImage: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth().height(70.dp)
            .padding(start = 20.dp, top = 20.dp).clickable(onClick = { onClick() })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = ingredientImage),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = ingredientName,
                color = Color.White,
                fontFamily = montserratFamily
            )
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
            .height(80.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(title, fontFamily = montserratFamily, fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Row( modifier = Modifier.clickable { onClick() } ) {
                Text(action, color = actionColor, fontFamily = montserratFamily, fontSize = 14.sp)
                icon?.let {
                    Icon(
                        it,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
}