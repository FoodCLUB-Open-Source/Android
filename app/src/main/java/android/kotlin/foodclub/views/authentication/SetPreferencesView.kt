package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.PlusJakartaSans
import android.kotlin.foodclub.views.home.montserratFamily
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SetPreferencesView(navController: NavHostController) {
    Box(modifier = Modifier.background(Color.White))
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp, top = 80.dp, bottom = 50.dp)
            ,
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            SetPreferencesTopLayout()//modifier = Modifier.weight(1F))
        }
    }
}

@Composable
fun SetPreferencesTopLayout(
    navController: NavHostController? = null,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxHeight(0.2f)) {
        Column {
            Image(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = "go back",
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .offset(x = (-8).dp)
                    .clickable { navController?.popBackStack() }
            )
            Box(modifier = Modifier.padding(top = 32.dp)) {
                Column() {
                    Text(
                        text = "Tell us what you like!", textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold, fontSize = 30.sp, fontFamily = PlusJakartaSans
                    )
                    Text(
                        text = "So we can bring the latest recipes!",
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        fontFamily = Montserrat,
                        color = Color.LightGray
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPreferencesMainLayout()
{
    val preferencesOptions = arrayListOf("Italian", "Mexican", "Indian", "Chinese", "Vegan", "Vegetarian"
        , "Paleo", "Japanese", "low-carb", "Vietnamese", "Thai", "Gluten-free")
    val selectedPreferences = remember{mutableListOf<String>()}

    Column {
        Box()
        {
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 80.dp))
            {
                items(preferencesOptions){

                    val (selectedOption, onOptionSelected) = remember {
                        mutableStateOf(false)
                    }

                    Card(shape = RoundedCornerShape(5.dp), modifier = Modifier.padding(5.dp), colors = if (selectedOption) CardDefaults.cardColors(Color(0xFF9922DD)) else CardDefaults.cardColors(
                        Color(0xFFEEEEEE)), onClick = {
                        onOptionSelected(!selectedOption)
                    },
                        border = BorderStroke(1.dp, Color.LightGray)
                        ) {
                        Text(text = it, fontFamily = montserratFamily,fontSize = 13.sp, color = if (selectedOption) Color.White else Color.Gray, modifier = Modifier.padding(5.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PreferenceOption()
{

}

@Composable
@Preview
fun SetPreferencesPreview() {
    Box(modifier = Modifier.background(Color.White))
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp, top = 80.dp, bottom = 50.dp)
            //,verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SetPreferencesTopLayout()//modifier = Modifier.weight(1F))
            SetPreferencesMainLayout()
        }
    }

}
