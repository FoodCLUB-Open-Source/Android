package com.example.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.MyRecipeModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavHostController
import com.example.foodclub.viewmodels.home.ProfileViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileView(navHostController: NavHostController) {

    val viewModel:ProfileViewModel = viewModel()
    viewModel.getData()


    val montserratFamily = FontFamily(

        Font(R.font.montserratregular, FontWeight.Normal),

        )

    val pagerState = rememberPagerState();
    val animals= arrayOf(R.drawable.profilepicture, R.drawable.login_with)


    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 70.dp, start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {


        Image(
            painterResource(id = R.drawable.profilepicture),
            contentDescription = "profile_picture",
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)

        )

        Text(
            fontFamily =montserratFamily ,
            text = "User Name",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterHorizontally)


        ) {

            ClickableText(
                text = AnnotatedString("2.5K"),
                onClick = {
                            viewModel.showFollowers();
                          },
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )


            )


            ClickableText(
                text = AnnotatedString("100"),
                onClick = {
//
                          navHostController.popBackStack()
                          navHostController.navigate("profile_graph")
                          },
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )


            )




        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),

            horizontalArrangement = Arrangement.spacedBy(22.dp, Alignment.CenterHorizontally)


        ) {

            Text(
                fontFamily =montserratFamily ,
                text = "Followers",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light

            )


            Text(
                fontFamily =montserratFamily ,
                text = "Following",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light

            )





        }


        Box(
            Modifier
                .fillMaxSize()

                .padding(top = 30.dp, start = 15.dp, end = 15.dp)

        ) {

            LazyVerticalGrid(columns =  GridCells.Fixed(2),
            ){

                items(viewModel.data){
                        dataitem ->
                    GridItem(dataitem)
                }


            }






        }




    }



}

@Composable
fun GridItem(item: MyRecipeModel){
    Card(modifier = Modifier
        .height(250.dp)
        .padding(10.dp)
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = rememberRipple(bounded = true, color = Color.Black),
            onClick = {
                //   After click
            }

        )
        ,shape = RoundedCornerShape(10.dp)) {

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



