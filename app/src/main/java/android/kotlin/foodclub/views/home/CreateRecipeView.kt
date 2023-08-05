package android.kotlin.foodclub.views.home

import android.graphics.Paint.Align
import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.home.CreateRecipeViewModel
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

val montserratFamily = FontFamily(Font(R.font.montserratregular, FontWeight.Normal))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeView() {
    val viewModel: CreateRecipeViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    val scrollState = rememberScrollState()

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true
        )
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(start = 15.dp, top = 60.dp, end = 15.dp, bottom = 70.dp)
    ) {
        Column(
            modifier = Modifier.padding(bottom = 70.dp).background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.padding(end = 10.dp).background(Color.Transparent),
                    contentAlignment = Alignment.Center,
                ) {
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(1.dp, Color(0xFFB8B8B8), shape = RoundedCornerShape(15.dp))
                            .clip(RoundedCornerShape(15.dp))
                            .align(Alignment.BottomCenter).width(40.dp)
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
                Text("New Recipe", modifier = Modifier.padding(start = 8.dp), fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            }
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Add a caption", fontFamily = montserratFamily, fontSize = 14.sp,
                color = Color(android.graphics.Color.parseColor("#B3B3B3"))) },
                leadingIcon = { Image(
                    painter = painterResource(id = R.drawable.story_user),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(100.dp).padding(10.dp).clip(RoundedCornerShape(12.dp))
                ) },
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
                icon = Icons.Default.KeyboardArrowDown
            )
            Divider(color = Color(android.graphics.Color.parseColor("#E8E8E8")), thickness = 1.dp)
            Row ( modifier = Modifier.fillMaxWidth().height(80.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Add Serving Size", fontFamily = montserratFamily, fontSize = 14.sp)
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(50.dp).padding(end = 15.dp)
                    )
                    Text("1", color = Color.Black, fontFamily = montserratFamily, fontSize = 14.sp)
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(50.dp).padding(start = 15.dp)
                    )
                }
            }
            Divider(color = Color(android.graphics.Color.parseColor("#E8E8E8")), thickness = 1.dp)
            Row ( modifier = Modifier.fillMaxWidth().height(80.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Ingredients", fontFamily = montserratFamily, fontSize = 14.sp)
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(1.dp, Color(126, 198, 11, 255), shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .width(145.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(126, 198, 11, 255)
                    ), contentPadding = PaddingValues(15.dp),
                    onClick = {}
                ) {
                    Text("Share Recipe +", color = Color(126, 198, 11, 255), fontFamily = montserratFamily, fontSize = 14.sp)
                }
            }
            LazyColumn {
                // TODO: Replace with actual data
                items(10) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }
        Button(
            shape = RectangleShape,
            modifier = Modifier
                .border(1.dp, Color(126, 198, 11, 255), shape = RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth().align(Alignment.BottomCenter),
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
fun SectionItem(
    title: String,
    action: String,
    icon: ImageVector?,
    actionColor: Color = Color.Black
) {
        Row ( modifier = Modifier.fillMaxWidth().height(80.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(title, fontFamily = montserratFamily, fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Row() {
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