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
import androidx.compose.foundation.layout.fillMaxHeight
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
//import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavController
import java.util.Collections.copy


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBasketView(navController: NavController) {
    val montserratFamily1 = FontFamily(

        Font(R.font.montserratbold, FontWeight.Bold),
        Font(R.font.montserratmedium, FontWeight.Medium)

    )

    val systemUiController = rememberSystemUiController()
    var showSheet by remember { mutableStateOf(false) }

    val triggerBottomSheetModal: () -> Unit = {
        showSheet = !showSheet
        systemUiController.setStatusBarColor(
            color = Color(android.graphics.Color.parseColor("#ACACAC")),
            darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = Color.Black,
            darkIcons = true
        )
    }
    SideEffect {
        if (!showSheet) {
            systemUiController.setSystemBarsColor(
                color = Color.White,
                darkIcons = true
            )
        }
    }

    if (showSheet) {
        BottomSheetIngredients(triggerBottomSheetModal)
    }
    Column(
        modifier = Modifier
            .background(color = Color.White)//Remove when doing navigation
            .fillMaxSize().padding(top = 60.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "My Basket",
                        fontSize = 25.sp,
                        fontFamily = montserratFamily1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = TextStyle(letterSpacing = -1.sp)
                    )
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
                        onClick = {  }
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
            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(end = 20.dp, start = 20.dp, bottom = 5.dp)
                    .height(80.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(1.dp, Color(126, 198, 11, 255), shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .width(125.dp),
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
                        fontSize = 13.sp,
                        fontFamily = montserratFamily,
                        color = Color(126, 198, 11, 255),
                    )
                }
            }
            LazyColumn (modifier = Modifier.padding(end = 20.dp, start = 20.dp, bottom = 110.dp)) {
                items(6) {
                    BasketIngredient("Broccoli oil", R.drawable.salad_ingredient)
                }
            }
        }

    }
}

@Composable
fun BasketIngredient(ingredientTitle: String, ingredientImage : Int) {
    var isSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
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
            painter = painterResource(id = ingredientImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .width(130.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Box(
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(30.dp))
                .background(if (isSelected) Color(android.graphics.Color.parseColor("#7EC60B"))
                else Color(android.graphics.Color.parseColor("#ECECEC")))
                .clickable { isSelected = !isSelected }
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.check),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 140.dp, top = 10.dp)
                .fillMaxSize()
        ) {
            Box ( modifier = Modifier.width(115.dp) ) {
                Text(
                    text = ingredientTitle,
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    fontWeight = FontWeight.Normal,
                    fontFamily = montserratFamily
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
                            .clip(RoundedCornerShape(20.dp))
                            .clickable {  }
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
                            .clip(RoundedCornerShape(20.dp))
                            .clickable {  }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}