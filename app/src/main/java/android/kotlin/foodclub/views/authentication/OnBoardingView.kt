package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun OnBoardingView(){
    val items = OnBoardingItems.getData()
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()
    val modifier = Modifier

    Column(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            count = items.size,
            state = pageState,
            modifier = modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
        ) { page ->
            OnBoardingItem(modifier = modifier, item = items[page])
        }
        BottomSection(
            size = items.size,
            index = pageState.currentPage,
        ) {
            if (pageState.currentPage + 1 < items.size) scope.launch {
                pageState.scrollToPage(pageState.currentPage + 1)
            }
        }
    }
}

@Composable
fun OnBoardingItem(item: OnBoardingItems, modifier: Modifier){
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
    onButtonClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        // Indicators
        Indicators(size, index)
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
