package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.BUSINESS
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.CLIMATE
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.GRAPHICS
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.HEALTH
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.INSTRUCTIONS
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.LOCALLY_PRODUCED
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.MOST_POPULAR
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.NUTRIENTS
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.ORGANIC
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.SEE_ALL
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.SPECIAL_DIETS
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.STYLE
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.TIPS
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.TITLE
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsConstants.WORLD
import android.kotlin.foodclub.utils.helpers.CreatorGraphicsDesignProvider
import android.kotlin.foodclub.utils.helpers.TabDrawableResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CreatorsViewDesigns(
    val icon: Int,
    val text: String
)

@Composable
fun GraphicsCreatorsView(){
    var isMenuExpanded by remember { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }
    var currentTabItems by remember { mutableStateOf<List<TabDrawableResources?>>(emptyList()) }
    var currentSelectedHeader by remember { mutableStateOf<String?>(null) }

    val listOfDesignsToApply = listOf(
        CreatorsViewDesigns(icon = R.drawable.title_icon, text = TITLE),
        CreatorsViewDesigns(icon = R.drawable.instructions_icon, text = INSTRUCTIONS),
        CreatorsViewDesigns(icon = R.drawable.nutrients_icon, text = NUTRIENTS),
        CreatorsViewDesigns(icon = R.drawable.tips_icon, text = TIPS),
        CreatorsViewDesigns(icon = R.drawable.climate_icon, text = CLIMATE),
        CreatorsViewDesigns(icon = R.drawable.world_icon, text = WORLD),
        CreatorsViewDesigns(icon = R.drawable.style_icon, text = STYLE),
        CreatorsViewDesigns(icon = R.drawable.health, text = HEALTH),
        CreatorsViewDesigns(icon = R.drawable.business_icon, text = BUSINESS),
    )

    val resourceProvider = CreatorGraphicsDesignProvider()

    Column(
        modifier = Modifier
            .padding(top = 20.dp, end = 10.dp)
            .clip(RoundedCornerShape(5.dp)),
        horizontalAlignment = Alignment.End
    ) {
        if (showSheet) {
            BottomSheet(
                currentSelectedHeader = currentSelectedHeader,
                currentTabItems = currentTabItems,
                onTabChanged = { _, tabName ->
                    currentTabItems = resourceProvider.getDrawableResources(tabName)
                    currentSelectedHeader = tabName
                },
                onDismiss = {
                    showSheet = false
                }
            )
        }

        IconButton(
            onClick = { isMenuExpanded = !isMenuExpanded },
            modifier = Modifier
                .size(55.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
        ) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        // Dropdown menu items
        if (isMenuExpanded) {
            listOfDesignsToApply.forEach { icon ->
                IconButton(
                    onClick = {
                        showSheet = true
                        isMenuExpanded = !isMenuExpanded
                        currentSelectedHeader = icon.text
                        currentTabItems = resourceProvider.getDrawableResources(icon.text)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray)
                        .size(55.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .padding(top = 5.dp),
                            painter = painterResource(id = icon.icon),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(text = icon.text, color = Color.White, fontSize = 8.sp)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    currentSelectedHeader: String?,
    currentTabItems: List<TabDrawableResources?>,
    onTabChanged: (Int, String) -> Unit,
    onDismiss: () -> Unit
){
    val bottomSheetState = rememberModalBottomSheetState()

    val headerColors = listOf(
        Color(0xFF346b69),
        Color(0xFF2c4905),
    )

    val contentColors = listOf(
        Color(0xFF214e6c),
        Color(0xFF335104),
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = {
            HeaderSection(
                currentSelectedHeader = currentSelectedHeader,
                colors = headerColors,
                onTabChanged = { index, tabName ->
                    onTabChanged(index, tabName)
                }
            )
        },
        contentColor = Color.Transparent,
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = contentColors,
                        tileMode = TileMode.Decal
                    )
                )
                .shadow(5.dp, ambientColor = Color(0xFF214e6c), spotColor = Color(0xFF214e6c))
        ){
            Image(
                painter = painterResource(id = R.drawable.niose),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            ModalContent(
                currentSelectedHeader = currentSelectedHeader,
                currentTabItems = currentTabItems
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderSection(
    currentSelectedHeader: String?,
    colors: List<Color>,
    onTabChanged: (Int, String) -> Unit
){
    var selectedHeaderIndex by remember { mutableIntStateOf(0) }
    val mainTabItemsList = listOf(
        TITLE,
        INSTRUCTIONS,
        NUTRIENTS,
        TIPS,
        CLIMATE,
        WORLD,
        STYLE,
        HEALTH,
        BUSINESS
    )
    // Find the index of the selected header
    if (currentSelectedHeader != null) {
        selectedHeaderIndex = mainTabItemsList.indexOf(currentSelectedHeader)
    }

    Box(
        modifier = Modifier
            .background(brush = Brush.linearGradient(colors))
    ) {
        Image(
            modifier = Modifier.matchParentSize(),
            painter = painterResource(id = R.drawable.niose),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                BottomSheetDefaults.DragHandle(
                    color = Color(0xFF136130)
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = GRAPHICS,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                TitlesRow(
                    tabsList = mainTabItemsList,
                    selectedIndex = selectedHeaderIndex,
                    onTabChanged = { index, tabName ->
                        selectedHeaderIndex = index
                        onTabChanged(index, tabName)
                    }
                )
            }
            Divider(color = Color.Transparent)
        }
    }
}

@Composable
fun TitlesRow(
    tabsList: List<String>,
    selectedIndex: Int,
    onTabChanged: (Int, String) -> Unit
) {
    var mainTabIndex by remember { mutableIntStateOf(selectedIndex) }

    LazyRow {
        itemsIndexed(tabsList) { index, data ->
            val isSelected = index == mainTabIndex

            Text(
                text = data,
                modifier = Modifier
                    .clickable {
                        onTabChanged(index, data)
                        mainTabIndex = index
                    }
                    .drawBehind {
                        if (isSelected) {
                            val strokeWidthPx = 2.dp.toPx()
                            val topPaddingPx = 10.dp.toPx()
                            val underlineHeight = 2.dp.toPx()
                            val verticalOffset = size.height - (underlineHeight / 2) + topPaddingPx
                            drawLine(
                                color = Color(0xFF58962d),
                                strokeWidth = strokeWidthPx,
                                start = Offset(0f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        }
                    }
                    .padding(horizontal = 8.dp),
                fontWeight = FontWeight(400),
                color = if (isSelected) Color(0xFF58962d) else Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ModalContent(
    currentSelectedHeader: String?,
    currentTabItems: List<TabDrawableResources?>
) {
    when (currentSelectedHeader) {
        TITLE -> {
            TitleSection(currentTabItems = currentTabItems, padding = 10.dp)
        }
        INSTRUCTIONS -> {
            InstructionSection(currentTabItems = currentTabItems, padding = 10.dp)
        }
        CLIMATE -> {
            ClimateSection(currentTabItems = currentTabItems, padding = 10.dp)
        }
        NUTRIENTS -> {
            NutrientsSection(currentTabItems)
        }
        WORLD -> {
            WorldSection(currentTabItems)
        }
        STYLE -> {
            StyleSection(currentTabItems)
        }
        HEALTH -> {
            HealthSection(currentTabItems)
        }
        else -> {}
    }
}

@Composable
fun TitleSection(
    currentTabItems: List<TabDrawableResources?>,
    padding: Dp
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        content = {
            currentTabItems[0]?.titleSection?.strings?.let { list ->
                itemsIndexed(list) { _, id ->
                    Image(
                        modifier = Modifier.padding(padding),
                        painter = painterResource(id = id),
                        contentDescription = "",
                    )
                }
            }
        }
    )
}

@Composable
fun InstructionSection(
    currentTabItems: List<TabDrawableResources?>,
    padding: Dp
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 20.dp),
        columns = GridCells.Fixed(2),
        content = {
            currentTabItems[0]?.instructionSection?.instructions?.let { list ->
                itemsIndexed(list) { _, id ->
                    Image(
                        modifier = Modifier.padding(padding),
                        painter = painterResource(id = id),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    )
}

@Composable
fun ClimateSection(
    currentTabItems: List<TabDrawableResources?>,
    padding: Dp
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 20.dp),
        columns = GridCells.Fixed(2),
        content = {
            currentTabItems[0]?.climateSection?.designs?.let { list ->
                itemsIndexed(list) { _, id ->
                    Image(
                        modifier = Modifier.padding(padding),
                        painter = painterResource(id = id),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    )
}

@Composable
fun NutrientsSection(currentTabItems: List<TabDrawableResources?>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        Column(verticalArrangement = Arrangement.Top) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                content = {
                    val list = currentTabItems[0]?.nutrientsSection?.badges
                    list?.let {
                        itemsIndexed(it) { index, id ->
                            if (index != list.lastIndex){
                                Image(
                                    modifier = Modifier.padding(10.dp),
                                    painter = painterResource(id = id),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            )
            val vitamins = currentTabItems[0]?.nutrientsSection?.vitamins?.get(0)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth(),
                    painter = painterResource(id = vitamins!!),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun WorldSection(currentTabItems: List<TabDrawableResources?>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ){
        item {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = MOST_POPULAR,
                fontSize = 16.sp,
                fontWeight = FontWeight(700),
                color = Color.White,
                lineHeight = 16.48.sp
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(end = 5.dp)
                ) {
                    val worldDesigns = currentTabItems[0]?.worldSectionDesigns?.world?.take(4)
                    worldDesigns!!.forEach {
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Column {
                    val flagDesigns = currentTabItems[0]?.worldSectionDesigns?.world?.drop(4)
                    flagDesigns!!.forEach { id ->
                        Image(
                            modifier = Modifier.size(112.dp),
                            painter = painterResource(id = id),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            ReusableRow(title = LOCALLY_PRODUCED, onClick = {})
            val locallyProducedDesigns = currentTabItems[0]?.worldSectionDesigns?.locallyProducedBadges
            LazyVerticalGrid(
                modifier = Modifier.height(400.dp),
                columns = GridCells.Fixed(3),
                content = {
                    itemsIndexed(locallyProducedDesigns!!) { _, id->
                        Image(
                            modifier = Modifier.padding(10.dp),
                            painter = painterResource(id = id),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun StyleSection(currentTabItems: List<TabDrawableResources?>) {
    Column {
        ReusableRow(title = SPECIAL_DIETS, onClick = {})
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 20.dp),
            columns = GridCells.Fixed(2),
            content = {
                currentTabItems[0]?.styleSectionDesigns?.style?.let { list ->
                    itemsIndexed(list) { _, id ->
                        Image(
                            modifier = Modifier.padding(10.dp),
                            painter = painterResource(id = id),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun HealthSection(currentTabItems: List<TabDrawableResources?>){
    val hotMeter = currentTabItems[0]?.healthSectionDesigns?.hotMeter
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 20.dp)) {
        item {
            ReusableRow(title = HEALTH, onClick =  {})
        }
        item {
            hotMeter!!.forEach { id->
                Image(
                    modifier= Modifier
                        .width(380.dp)
                        .height(75.dp),
                    painter = painterResource(id = id),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
        }
        item {
            LazyVerticalGrid(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                columns = GridCells.Fixed(3),
                content = {
                    currentTabItems[0]?.healthSectionDesigns?.dietaryBadges?.let { list ->
                        itemsIndexed(list) { _, id ->
                            Image(
                                modifier = Modifier.padding(5.dp),
                                painter = painterResource(id = id),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            )
        }
        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = ORGANIC,
                    fontWeight = FontWeight(700),
                    fontSize = 16.sp,
                    color = Color.White,
                    lineHeight = 16.48.sp
                )
            }
        }
        item {
            LazyVerticalGrid(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                columns = GridCells.Fixed(3),
                content = {
                    currentTabItems[0]?.healthSectionDesigns?.organic?.let { list ->
                        itemsIndexed(list) { _, id ->
                            Image(
                                modifier = Modifier.padding(15.dp),
                                painter = painterResource(id = id),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ReusableRow(
    title: String,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            color = Color.White,
            lineHeight = 16.48.sp,
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier
                .padding(10.dp)
                .clickable { onClick() },
            text = SEE_ALL,
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF7EC60B),
            lineHeight = 14.42.sp,
            textAlign = TextAlign.End
        )
    }
}