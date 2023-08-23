package com.example.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.DiscoverViewRecipeModel
import android.kotlin.foodclub.data.models.MyRecipeModel
import android.kotlin.foodclub.views.home.BottomSheetIngredients
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodclub.viewmodels.home.DiscoverViewModel
import com.example.foodclub.viewmodels.home.ProfileViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.logging.Logger


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiscoverView(navController: NavController) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White, darkIcons = true
        )
    }


    val montserratFamily1 = FontFamily(

        Font(R.font.montserratbold, FontWeight.Bold),
        Font(R.font.montserratmedium, FontWeight.Medium)

    )


    val viewModel: DiscoverViewModel = viewModel()
    val pages = viewModel.getPages();

    viewModel.getData()
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, end = 20.dp, start = 20.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.Start)
        ) {

            Column() {


                Text(
                    color = Color.Black,
                    text = "Hi Emily,",

                    fontFamily = montserratFamily1,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 7.dp),
                    style = TextStyle(letterSpacing = -1.sp)
                )

                Text(
                    color = Color.Black,
                    text = "Let's get cooking!",
                    fontFamily = montserratFamily1,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(letterSpacing = -1.sp)
                )
            }



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Button(shape = CircleShape,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(53.dp)
                        .width(53.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(245, 245, 245, 255),

                        ),
                    contentPadding = PaddingValues(),

                    onClick = {


                    }

                ) {

                    Image(
                        painter = painterResource(id = R.drawable.frame_1171275072),
                        contentDescription = "",
                    )

                }

                Button(shape = CircleShape,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(53.dp)
                        .width(53.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(245, 245, 245, 255),

                        ),
                    contentPadding = PaddingValues(),

                    onClick = {

                        navController.navigate("BASKET_VIEW")

                    }

                ) {

                    Image(

                        painter = painterResource(id = R.drawable.vector__1_),
                        contentDescription = "",

                        )

                }

            }
        }


        val pagerState1 = rememberPagerState();

        val scope = rememberCoroutineScope();


        val tabItems1 = listOf(
            "Categories", "World", "My Fridge"
        )

        val tabItems2 = listOf(
            "Proteins", "Carbs", "Plant Based", "Drinks"
        )

        val tabItems3 = listOf(
            "England", "Italy", "France", "Germany"
        )

        var tabIndex by remember { mutableStateOf(0) }

        TabRow(
            selectedTabIndex = tabIndex, modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            tabItems1.forEachIndexed { index, data ->
                val selected = tabIndex == index

                Tab(selected = selected, onClick = {
                    tabIndex = index
                }, text = {
                    Text(text = data)
                })

            }
        }




        Spacer(modifier = Modifier.height(10.dp))

        if (tabIndex == 0 || tabIndex == 2)
            TabHomeDiscover(tabItems2, pagerState1, scope, 12.sp)
        else if (tabIndex == 1)
            TabHomeDiscover(tabItems3, pagerState1, scope, 12.sp)



        Spacer(modifier = Modifier.height(20.dp))

        val systemUiController = rememberSystemUiController()
        var showSheet by remember { mutableStateOf(false) }

        val triggerBottomSheetModal: () -> Unit = {
            showSheet = !showSheet
            systemUiController.setStatusBarColor(
                color = Color(android.graphics.Color.parseColor("#ACACAC")), darkIcons = true
            )
            systemUiController.setNavigationBarColor(
                color = Color.Black, darkIcons = true
            )
        }

        SideEffect {
            if (!showSheet) {
                systemUiController.setSystemBarsColor(
                    color = Color.White, darkIcons = true
                )
            }
        }

        if (showSheet) {
            BottomSheetIngredients(triggerBottomSheetModal)
        }


        if (tabIndex == 2) {


            Button(shape = RectangleShape,
                modifier = Modifier
                    .border(
                        1.dp, Color(126, 198, 11, 255), shape = RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .width(125.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, contentColor = Color(126, 198, 11, 255)
                ),
                contentPadding = PaddingValues(15.dp),
                onClick = {
                    triggerBottomSheetModal()
                }) {
                Text(
                    "Add items +",
                    fontSize = 13.sp,
                    fontFamily = android.kotlin.foodclub.views.home.montserratFamily,
                    color = Color(126, 198, 11, 255),
                )
            }
        }

        HorizontalPager(
            pageCount = 4,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp),
            state = pagerState1
        ) { index ->

            Box(
                Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp, start = 15.dp, end = 15.dp)

            ) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                ) {

                    items(5) { dataItem ->
                        GridItem2(navController)
                    }


                }

            }

        }

    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabHomeDiscover(
    tabItems: List<String>, pagerState1: PagerState, scope: CoroutineScope, fontSize: TextUnit
) {

    TabRow(
        selectedTabIndex = pagerState1.currentPage
    ) {
        tabItems.forEachIndexed { index, item ->
            Tab(selected = pagerState1.currentPage == index,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    scope.launch {
                        pagerState1.animateScrollToPage(index)
                    }

                },

                text = {
                    Text(

                        text = AnnotatedString(item), style = TextStyle(
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = fontSize,
                            textAlign = TextAlign.Center
                        )


                    )
                })

        }
    }
}

@Composable
fun GridItem2(navController: NavController) {

    val satoshiFamily = FontFamily(
        Font(R.font.satoshi, FontWeight.Medium)
    )


    Card(
        modifier = Modifier
            .height(272.dp)
            .width(178.dp)
            .padding(10.dp), shape = RoundedCornerShape(15.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.imagecard),
                contentDescription = "",
                Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate("DELETE_RECIPE") },
                contentScale = ContentScale.FillHeight
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp), verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "fdg",
                    fontFamily = satoshiFamily,
                    color = Color.White,
                    fontSize = 18.sp
                )

                Text(
                    text = "gsd",
                    fontFamily = satoshiFamily,
                    fontSize = 14.sp,
                    color = Color(255, 255, 255, 200)
                )
            }
        }

    }
}




