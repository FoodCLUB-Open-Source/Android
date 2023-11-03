package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.defaultButtonColors
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.others.OnBoardingItems
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun OnBoardingView() {
    val items = OnBoardingItems.getData()
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()
    val modifier = Modifier

    Column(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            count = 4,
            state = pageState,
            modifier = modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
        ) { page ->
            //OnBoardingItem(modifier = modifier, item = items[page])
            if (page == 0) {
                OnBoardingScreen1()
            }
            if (page == 1) {
                OnBoardingScreen2()
            }
            if (page == 2) {
                OnBoardingScreen3()
            }
            if (page == 3) {
                OnBoardingScreen4()
            }

        }

        BottomSection(
            size = 4,
            index = pageState.currentPage,
            onLeftButtonClick = {
                if (pageState.currentPage - 1 >= 0) scope.launch {
                    pageState.scrollToPage(pageState.currentPage - 1)
                }
            },
            onRightButtonClick = {
                if (pageState.currentPage + 1 < 4) scope.launch {
                    pageState.scrollToPage(pageState.currentPage + 1)
                }
            }
        )
    }
}

@Composable
fun OnBoardingScreen1() {
    Column(
        Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = "FoodCLUB",
            fontFamily = Montserrat,
            fontWeight = FontWeight.Black,
            fontSize = 35.sp
        )
        Text(
            text = "The Art of Food is Here",
            fontFamily = Montserrat,
            fontSize = 20.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun OnBoardingScreen2() {
    Column(
        Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "Don't follow,",
            color = Color(0xFF3A7CA8),
            fontSize = 45.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "create.",
            color = foodClubGreen,
            fontSize = 80.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun OnBoardingScreen3() {
    Column(
        Modifier
            .padding(30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            fontFamily = Montserrat,
            fontWeight = FontWeight.ExtraBold,
            text = "Our Mission",
            fontSize = 40.sp
        )
        Text(
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            text = "Did you know?",
            fontSize = 27.5.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        InfoRow(
            imageID = R.drawable.carbon_footprint,
            text = "25% of climate change and 70% of fresh water is linked to food production"
        )
        InfoRow(
            imageID = R.drawable.sad_heart,
            text = "70% of disease and illness are caused by nutrition"
        )
        InfoRow(
            imageID = R.drawable.globe,
            text = "We produce food across the glob to wipe out global hunger - but 40% of that food goes to waste"
        )
        InfoRow(imageID = R.drawable._00m, text = "1 in 10 people have a food disorder")
        InfoRow(
            imageID = R.drawable.loneliness,
            text = "Social media has left us unconnected in our communities and our cultures"
        )

        Text(
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            text = "You're now part of our mission.",
            fontSize = 21.5.sp,
            modifier = Modifier.align(Alignment.Start)
        )
    }
}

@Composable
fun OnBoardingScreen4() {
    Column(
        Modifier
            .padding(30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            text = "Join Our Discord VIP Community",
            fontSize = 40.sp
        )

        InfoRow(
            imageID = R.drawable.crown,
            text = "Join our community for exclusive content and insight into the tech and business world"
        )
        Divider()
        InfoRow(imageID = R.drawable.crown, text = "Engage in lively discussions and events.")
        Divider()
        InfoRow(
            imageID = R.drawable.innovation,
            text = "Shape the future of food: help decide new features and innovation."
        )
        Divider()
        InfoRow(
            imageID = R.drawable.giveaways,
            text = "Participate in special contests and giveaways."
        )
        Divider()
        InfoRow(
            imageID = R.drawable.gold_medal,
            text = "Be the first to savor insights into foodSNAPS, foodMAPS, foodSCHOOL, and our detectable AI-driven innovations."
        )

        val link = "https://discord.gg/dA83mnQMqr"

        val localUriHandler = LocalUriHandler.current

        Button(
            onClick = { localUriHandler.openUri(link) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A7CA8)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Join the VIP Discord Server",
                fontFamily = Montserrat,
                color = Color.White,
                fontSize = 24.sp
            )
        }

    }
}

@Composable
fun InfoRow(imageID: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
    {
        Image(
            painter = painterResource(id = imageID),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(55.dp)
        )
        Text(
            text = text, fontFamily = Montserrat, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), color = Color.Gray, fontSize = 16.sp
        )
    }
}

@Composable
fun OnBoardingItem(item: OnBoardingItems, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(modifier = modifier.fillMaxWidth()) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.onboarding_background),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .zIndex(0f),
                    contentScale = ContentScale.FillWidth
                )
            }
            Box {
                Image(
                    painter = painterResource(id = item.image),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .zIndex(1f),
                    contentScale = ContentScale.FillWidth
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(top = 60.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text(
                text = item.title,
                color = Color.Black,
                fontSize = 56.sp,
                fontFamily = Montserrat,
                lineHeight = 68.26.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight(600)
            )

            Text(
                text = item.descr,
                modifier = modifier.padding(top = 30.dp),
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                fontFamily = Montserrat,
                lineHeight = 32.sp,
                fontWeight = FontWeight(400)
            )
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .height(16.dp)
            .width(16.dp)
            .clip(CircleShape)
            .background(
                color = if (isSelected) foodClubGreen else Color.Gray
            )
    )
}

@Composable
fun BottomSection(
    size: Int,
    index: Int,
    onRightButtonClick: () -> Unit = {},
    onLeftButtonClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        // Indicators
        //Indicators(size, index)
        NavArrows(
            size = size,
            index = index,
            onLeftButtonClick = onLeftButtonClick,
            onRightButtonClick = onRightButtonClick
        )
    }
}

@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        modifier = Modifier.align(Alignment.BottomCenter)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }

    }
}

@Composable
fun BoxScope.NavArrows(
    size: Int,
    index: Int,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
    ) {
        Button(
            onClick = onLeftButtonClick,
            colors = defaultButtonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            enabled = index != 0
        ) {
            if (index != 0) {
                Image(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }

        }


        Button(
            onClick = onRightButtonClick,
            colors = defaultButtonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            enabled = index != size - 1
        ) {

            if (index != size - 1) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }
        }

    }
}

@Composable
@Preview
fun OnBoardingPreview() {
    OnBoardingView()
}