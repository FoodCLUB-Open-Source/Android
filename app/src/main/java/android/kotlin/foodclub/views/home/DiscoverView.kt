package com.example.foodclub.views.home

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.DiscoverViewRecipeModel
import android.kotlin.foodclub.data.models.MyRecipeModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodclub.viewmodels.home.DiscoverViewModel
import com.example.foodclub.viewmodels.home.ProfileViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiscoverView() {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true
        )
    }

    val montserratFamily1 = FontFamily(

        Font(R.font.montserratbold, FontWeight.Bold),
        Font(R.font.montserratmedium, FontWeight.Medium)

        )



    val viewModel: DiscoverViewModel = viewModel()
    val pages = viewModel.getPages();

    viewModel.getData()
    Column(modifier = Modifier.fillMaxSize()) {


        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp, end = 20.dp, start = 20.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp,Alignment.Start)) {

            Column() {


                Text(color = Color.Black,
                    text = "Hi Emily,",

                    fontFamily = montserratFamily1,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 7.dp),
                    style = TextStyle(letterSpacing = -1.sp)
                )

                Text(color = Color.Black,
                    text = "Let's get cooking!",
                    fontFamily = montserratFamily1,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(letterSpacing = -1.sp))
            }



            Row(modifier = Modifier
                .fillMaxWidth()
                ,horizontalArrangement = Arrangement.spacedBy(5.dp)) {

                Button(shape = CircleShape,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(53.dp)
                        .width(53.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor =  Color(245, 245, 245, 255),

                        ),
                    contentPadding = PaddingValues(),

                    onClick = {



                    }

                ) {

                    Image(
                        painter = painterResource(id = R.drawable.vector),
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



                    }

                ) {

                    Image(

                        painter = painterResource(id = R.drawable.vector__1_),
                        contentDescription = "",

                    )

                }

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
            ,

            horizontalArrangement = Arrangement.spacedBy(34.dp, Alignment.CenterHorizontally)


        ) {

            ClickableText(

                onClick = {
//                    viewModel.changeMyRecipeSliderColor()
//                    viewModel.reverseBookmarkedSliderColor()
                    viewModel.runUiChange(1);
                },

                text =  AnnotatedString("Categories"),
                style = TextStyle(
                    color = viewModel.categoriesColor,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    textDecoration = viewModel.categoriesDecoration ,

                )

            )


            ClickableText(
                onClick = {
//                    viewModel.changeBookmarkedSliderColor()
//                    viewModel.reverseMyRecipeSliderColor()
                    viewModel.runUiChange(2);
                },

                text =  AnnotatedString("World"),
                style = TextStyle(

                    color = viewModel.worldColor ,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    textDecoration =  viewModel.worldDecoration,


                    )
            )

            ClickableText(
                onClick = {
//                    viewModel.changeBookmarkedSliderColor()
//                    viewModel.reverseMyRecipeSliderColor()
                    viewModel.runUiChange(3);
                },

                text =  AnnotatedString("My Fridge!"),
                style = TextStyle(

                    color = viewModel.myFridgeColor,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    textDecoration = viewModel.myFridgeDecoration,


                    )
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
            ,

            horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally)


        ) {

            ClickableText(

                onClick = {
//                    viewModel.()
//                    viewModel.reverseBookmarkedSliderColor()

                },

                text =  AnnotatedString("Protein"),
                style = TextStyle(
                    color = viewModel.proteinColor,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    textDecoration = viewModel.proteinDecoration,

                )

            )


            ClickableText(
                onClick = {
//                    viewModel.changeBookmarkedSliderColor()
//                    viewModel.reverseMyRecipeSliderColor()
                },

                text =  AnnotatedString("Carbs"),
                style = TextStyle(

                    color = viewModel.carbsColor,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    textDecoration = viewModel.carbsDecoration,


                    )
            )

            ClickableText(
                onClick = {
//                    viewModel.changeBookmarkedSliderColor()
//                    viewModel.reverseMyRecipeSliderColor()
                },

                text =  AnnotatedString("Plant Based"),
                style = TextStyle(

                    color = viewModel.plantBasedColor,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    textDecoration = viewModel.plantBasedDecoration,


                    )
            )

            ClickableText(
                onClick = {
//                    viewModel.changeBookmarkedSliderColor()
//                    viewModel.reverseMyRecipeSliderColor()
                },

                text =  AnnotatedString("Drinks"),
                style = TextStyle(

                    color = viewModel.drinksColor,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    textDecoration = viewModel.drinksDecoration,


                    )
            )

        }

        val pagerState = rememberPagerState() { 2 };
//
//        TabRow(selectedTabIndex = pagerState.currentPage,
//                 indicator = {
//                     tabPositions ->
//                     TabRowDefaults.Indicator(
//                         Modifier.fillMaxWidth().pagerTabIndicatorOffset(pagerState,tabPositions)
//                     )
//                 }
//
//        ) {
//
//
//
//        }

        HorizontalPager(state = pagerState, key = {pages[it]},
            modifier = Modifier.fillMaxSize().padding(top = 15.dp)) {
                index->

            Box(
                Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp, start = 15.dp, end = 15.dp)

            ) {

                LazyVerticalGrid(columns =  GridCells.Fixed(2),
                ){

                    items(pages[index]){
                            dataItem ->
                        GridItem1(dataItem)
                    }


                }

            }
        }

//        HorizontalPager(pageCount = 2, modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 15.dp), state = pagerState, key = {pages[it]}) {
//                index->
//
//            Box(
//                Modifier
//                    .fillMaxSize()
//                    .padding(top = 5.dp, start = 15.dp, end = 15.dp)
//
//            ) {
//
//                LazyVerticalGrid(columns =  GridCells.Fixed(2),
//                ){
//
//                    items(pages[index]){
//                            dataItem ->
//                        GridItem1(dataItem)
//                    }
//
//
//                }
//
//            }
//
//        }


    }

}



@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun GridItem1(item: DiscoverViewRecipeModel){

    val satoshiFamily = FontFamily(


        Font(R.font.satoshi, FontWeight.Medium)

    )

    Card(modifier = Modifier
        .height(272.dp)
        .width(178.dp)
        .padding(10.dp)
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = rememberRipple(bounded = true, color = Color.Black),
            onClick = {
                //   After click
            }

        )
        ,shape = RoundedCornerShape(10.dp)) {

        Box(){
            Image(painter = painterResource(id = R.drawable.imagecard), contentDescription = "",
                Modifier.fillMaxSize(), contentScale = ContentScale.FillHeight)

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp),verticalArrangement = Arrangement.Bottom) {
                Text(text = "${item.userName}", fontFamily = satoshiFamily, color = Color.White, fontSize = 18.sp)

                Text(text = "${item.timeUploaded}", fontFamily = satoshiFamily, fontSize = 14.sp
                    , color = Color(255,255,255,200)
                )
            }

        }

    }
}


