package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.widget.SearchView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodclub.views.home.TabHomeDiscover
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(navController: NavController){


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 40.dp, horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .background(Color(218, 218, 218, 1))
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(),

            placeholder = {
                Text(text = "Search..", color = Color(218, 218, 218, 228))
            },

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(218, 218, 218, 128),
                unfocusedBorderColor = Color(218, 218, 218, 110)
            )

        )

        val users = listOf(
            "Users","Recipes"
        )

        Row() {

            var tabIndex by remember { mutableStateOf(0) }


            TabRow(
                selectedTabIndex = tabIndex, modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White)
                    .padding(10.dp),
                containerColor = Color.White,
                contentColor = Color.White,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        height = 2.dp,
                        color = Color.Black
                    )
                }
            ) {
                users.forEachIndexed { index, data ->
                    val selected = tabIndex == index

                    Tab(selected = selected,
                        modifier = Modifier.background(Color.White),
                        selectedContentColor = Color.Black,
                        onClick = {
                            tabIndex = index
                        }, text = {
                            Text(text = data,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                color = if (selected) Color.Black else Color(android.graphics.Color.parseColor("#C2C2C2")))
                        })

                }
            }


//            LazyRow(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceAround){
//                items(items = users){ item->
//                    Button(shape = RectangleShape,
//                        modifier = Modifier
//                            .border(
//                                1.dp, Color(126, 198, 11, 255), shape = RoundedCornerShape(5.dp)
//                            )
//                            .clip(RoundedCornerShape(5.dp))
//                            .width(125.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.White, contentColor = Color(126, 198, 11, 255)
//                        ),
//                        contentPadding = PaddingValues(15.dp),
//                        onClick = {
//
//                        }) {
//                        Text(
//                            item,
//                            fontSize = 13.sp,
//                            fontFamily = android.kotlin.foodclub.views.home.montserratFamily,
//                            color = Color(126, 198, 11, 255),
//                        )
//                    }
//
//                }
//            }
        }



    }




}