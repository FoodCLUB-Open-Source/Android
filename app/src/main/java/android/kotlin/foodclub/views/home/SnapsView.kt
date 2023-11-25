package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SnapsView(
    memoriesModel: MemoriesModel,
    modifier: Modifier,
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = Color.White
        )
    }
    val snapPagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ){
        memoriesModel.stories.size
    }
    val snapPagerFling = PagerDefaults.flingBehavior(
        state = snapPagerState, lowVelocityAnimationSpec = tween(
            easing = LinearEasing, durationMillis = 300
        )
    )
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var snapUserIndex by remember {
        mutableStateOf(0)
    }
    var isDownloaded by remember {
        mutableStateOf(false)
    }
    VerticalPager(
        state = snapPagerState,
        flingBehavior = snapPagerFling,
        beyondBoundsPageCount = 1
    ) {

        Box{
            AsyncImage(
                model = memoriesModel.stories[it].imageUrl,
                contentDescription = "",
                contentScale= ContentScale.Crop,
                modifier=Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(15.dp)
            ) {
                Column(
                    modifier=Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 15.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.story_user),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(35.dp)
                                .clip(CircleShape)
                                .alpha(0.7f)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            memoriesModel.stories[it].snapAuthor.username, color = Color.White,
                            fontFamily = Montserrat, fontSize = 18.sp,
                            modifier = Modifier
                                .padding(2.dp)
                                .alpha(0.7f)
                        )
                    }
                    Text(
                        memoriesModel.stories[it].dateTime, color = Color.White,
                        fontFamily = Montserrat, fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(2.dp)
                            .alpha(0.7f)
                    )
                }

                Row(
                    modifier=Modifier.align(Alignment.CenterEnd)
                ) {
                    Box (
                        modifier= Modifier
                            .size(44.dp)
                            .alpha(0.4f)
                            .background(
                                color = Color(0xFF735029),
                                shape = RoundedCornerShape(22.dp)
                            )
                            .clickable {
                                scope.launch {
                                    showBottomSheet = true
                                    snapUserIndex = it
                                }
                            }
                    ){
                        Image(painter = painterResource(id = R.drawable.handwave), contentDescription = "download", modifier = Modifier.align(Alignment.Center))

                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Box (
                        modifier= Modifier
                            .size(44.dp)
                            .alpha(0.4f)
                            .background(
                                color = Color(0xFF6D4E2E),
                                shape = RoundedCornerShape(22.dp)
                            )
                            .clickable {
                                isDownloaded = !isDownloaded
                            }
                    ){
                        Image(painter = painterResource(id = if(isDownloaded)R.drawable.download_green else R.drawable.download_svg), contentDescription = "download", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }


        }
        val userReactions = memoriesModel.stories[snapUserIndex].userReactions

        if(showBottomSheet){
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet=false},
                sheetState=sheetState,
                modifier = Modifier.height((LocalConfiguration.current.screenHeightDp/2).dp)
            ) {
                SnapBottomSheetLayout(userReactions)
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SnapBottomSheetLayout(userReactions:
                          Map<SimpleUserModel, Reactions>) {

    val reactions = Reactions.values().toList()
    val state = rememberPagerState(
        initialPage = 0
    ) {
        reactions.size
    }
    val flingBehavior = PagerDefaults.flingBehavior(state = state, lowVelocityAnimationSpec = tween(
        easing = LinearEasing, durationMillis = 300
    ))
    val scope = rememberCoroutineScope()
    Column {
        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            items(reactions){reaction->
                Box(modifier = Modifier.clickable {
                    scope.launch {
                        state.scrollToPage(reaction.ordinal,0f)
                    }
                }){
                    if(reaction != Reactions.ALL){
                        Column (modifier = Modifier.width(IntrinsicSize.Max)){
                            Row {
                                Image(painter = painterResource(id = reaction.drawable), contentDescription = "")
                                Text(text = "${userReactions.filter {
                                    it.value == reaction
                                }.count()}",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontFamily = Montserrat,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if(state.currentPage != reaction.ordinal) Color(0xFF949494) else Color(0xFF7EC60B)
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                            if(state.currentPage == reaction.ordinal){
                                Spacer(modifier = Modifier
                                    .height(2.dp)
                                    .fillMaxSize()
                                    .background(color = Color(0xFF7EC60B)))
                            }
                        }
                    }
                    else{
                        Column (modifier = Modifier
                            .width(IntrinsicSize.Max)
                        ){
                            Text(
                                text = "ALL ${userReactions.count()}",
                                style = TextStyle(
                                    color = if(state.currentPage != 0) Color(0xFF949494) else Color(0xFF7EC60B),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                ),
                                modifier = Modifier.height(19.88.dp)
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            if(state.currentPage == 0){
                                Spacer(modifier = Modifier
                                    .height(2.dp)
                                    .fillMaxSize()
                                    .background(color = Color(0xFF7EC60B)))
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(color = Color.Black)
            .alpha(0.6f)
            )
    }

    HorizontalPager(
        state = state,
        beyondBoundsPageCount = 1,
        verticalAlignment = Alignment.Top,
        flingBehavior=flingBehavior,

    ) {idx->
        LazyColumn{
            val list =if(reactions[idx]!=Reactions.ALL) userReactions.filter { x->
                x.value == reactions[idx]
            }.keys.toList()
            else userReactions.keys.toList()
            items(list.size){
                    BottomSheetItemView(snapUserModel = list[it])
            }
        }
    }
}

@Composable
fun BottomSheetItemView(
    snapUserModel:SimpleUserModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start){
            Spacer(modifier = Modifier.size(20.dp))
            AsyncImage(model = snapUserModel.profilePictureUrl, contentDescription = "dp", contentScale = ContentScale.Crop, modifier = Modifier
                .size(45.dp)
                .clip(
                    RoundedCornerShape(100)
                ))
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = snapUserModel.username,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row {
            Spacer(modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(color = Color.Black)
                .alpha(0.6f)
                .padding(horizontal = 14.dp))
        }
    }
}
