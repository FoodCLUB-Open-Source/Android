package com.example.foodclub.views.home

import android.kotlin.foodclub.R
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodclub.navigation.graphs.OnBoardingScreen
import com.example.foodclub.navigation.graphs.RootNavigationGraph
import com.example.foodclub.viewmodels.home.ProfileViewModel





@OptIn(ExperimentalFoundationApi::class)
@Composable


fun ProfileView(navController: NavController) {

    val viewModel:ProfileViewModel = viewModel()
    viewModel.getData()


    val montserratFamily = FontFamily(

        Font(R.font.montserratregular, FontWeight.Normal),
        Font(R.font.montserratsemibold, FontWeight.SemiBold),

        )

    val pagerState = rememberPagerState();
    val animals= arrayOf(R.drawable.profilepicture, R.drawable.login_with)
    val pages = viewModel.getPages();

    Row(modifier = Modifier
        .fillMaxSize()
        .padding(top = 60.dp, start = 100.dp), horizontalArrangement = Arrangement.spacedBy(55.dp,Alignment.CenterHorizontally)) {
        Image(
            painterResource(id = R.drawable.profilepicture),
            contentDescription = "profile_picture",
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)

        )
        Button(shape = CircleShape,
            modifier = Modifier
                .clip(CircleShape)
                .height(53.dp)
                .width(53.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(255, 255, 255, 255),

                ),
            contentPadding = PaddingValues(),

            onClick = {
                navController.navigate("SETTINGS")
            }

        ) {

            Image(
                painter = painterResource(id = R.drawable.vector_1_),
                contentDescription = "",
                )
        }

    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 190.dp, start = 4.dp, end = 4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {






        Text(
            fontFamily =montserratFamily ,
            text = "User Name",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 5.dp),
            letterSpacing = -1.sp
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(70.dp, Alignment.CenterHorizontally)

        ) {

            ClickableText(
                text = AnnotatedString("2.5K"),
                onClick = {
                            viewModel.showFollowers();
                          },
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )


            )


            ClickableText(
                text = AnnotatedString("100"),
                onClick = {


//                        navController.popBackStack()
//                        navController.navigate(route = ProfileNavigationScreens.FOLLOWER_VIEW.route)


                          },
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )


            )




        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                ,

            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)


        ) {

            Text(
                fontFamily =montserratFamily ,
                text = "Followers",
                fontSize = 16.sp,
                color = Color(127, 147, 141,255),
                fontWeight = FontWeight.Light

            )


            Text(

                fontFamily =montserratFamily ,
                text = "Following",
                fontSize = 16.sp,
                color = Color(127, 147, 141,255),
                fontWeight = FontWeight.Light

            )

        }

//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 30.dp)
//              ,
//
//            horizontalArrangement = Arrangement.spacedBy(55.dp, Alignment.CenterHorizontally)
//
//
//        ) {
//
//            ClickableText(
//
//               onClick = {
//                    viewModel.changeMyRecipeSliderColor()
//                      viewModel.reverseBookmarkedSliderColor()
//
//               },
//
//                text =  AnnotatedString("My Recipes"),
//                style = TextStyle(
//                color = viewModel.myRecipeSliderColor,
//                fontFamily = montserratFamily,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 16.sp,
//                textDecoration = viewModel.myRecipeTextDecoration,
//            )
//
//            )
//
//
//            ClickableText(
//                onClick = {
//                    viewModel.changeBookmarkedSliderColor()
//                    viewModel.reverseMyRecipeSliderColor()
//                },
//
//                text =  AnnotatedString("Bookmarked"),
//                style = TextStyle(
//
//                    color = viewModel.bookmarkedSliderColor,
//                    fontFamily = montserratFamily,
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 16.sp,
//                    textDecoration = viewModel.bookmarkedTextDecoration,
//
//            ))
//
//        }

//        val tabItems = listOf(
//            "Hello",
//            "Bye"
//        )


//        var pagerState by remember{ mutableStateOf(0) }
//        val scope = rememberCoroutineScope();
//
//        TabRow(selectedTabIndex = pagerState) {
//            tabItems.forEachIndexed{
//                index,item ->
//                Tab(selected = pagerState == index, onClick = { pagerState = index }, modifier = Modifier.padding(20.dp)
//                    ) {
////                    Column(modifier = Modifier.fillMaxWidth().background(Color.Yellow), horizontalAlignment = CenterHorizontally) {
////                        Text(text = item)
////                    }
//
//                    Text(
//
//                       text =  AnnotatedString("My Recipes"),
//                        style = TextStyle(
//                            color = viewModel.myRecipeSliderColor,
//                            fontFamily = montserratFamily,
//                            fontWeight = FontWeight.SemiBold,
//                            fontSize = 16.sp,
//                            textDecoration = viewModel.myRecipeTextDecoration,
//                        )
//
//                    )
//
//                }
//
//            }
//        }

        TabHome(selectedTabInd = 0)

        HorizontalPager(pageCount = 2, modifier = Modifier.fillMaxSize(), key = {pages[it]}) {
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
                    GridItem(dataItem)
                }


            }

        }

        }



    }



}

val tabItems = listOf(
    "Hello",
    "Bye"
)


@Composable
fun TabHome(selectedTabInd:Int){
    TabRow(selectedTabIndex = selectedTabInd ) {
        tabItems.forEachIndexed{
                index,item ->
            Tab(selected = selectedTabInd == index, onClick = {   }, modifier = Modifier.padding(20.dp), text = {
                Text(

                    text =  AnnotatedString("My Recipes"),
                    style = TextStyle(
                        //color = viewModel.myRecipeSliderColor,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                       // textDecoration = viewModel.myRecipeTextDecoration,
                    )

                )
            }
            ) 

        }
    }
}

@Composable
fun GridItem(item: MyRecipeModel){
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
        ,shape = RoundedCornerShape(15.dp)) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()){
            Image(painter = painterResource(id = R.drawable.imagecard), contentDescription = "",
                Modifier.fillMaxSize(), contentScale = ContentScale.FillHeight)
            Text(text = "${item.likeCount}" ,modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp), color = Color.White)
        }

    }
}

//@Composable
//@Preview
//fun ProfileViewPreview() {
//
//
//    ProfileView(rememberba)
//}



