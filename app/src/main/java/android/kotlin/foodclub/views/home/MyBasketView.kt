package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.utils.composables.IngredientsBottomSheet
import android.kotlin.foodclub.utils.helpers.ValueParser
import android.kotlin.foodclub.viewModels.home.MyBasketViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.delay


@Composable
fun MyBasketView(
    viewModel: MyBasketViewModel
) {
    val systemUiController = rememberSystemUiController()
    var showSheet by remember { mutableStateOf(false) }
    val productsList = viewModel.productsList.collectAsState()
    val selectedProductsIds = viewModel.selectedProductsList.collectAsState()
    var deleteSelected by remember { mutableStateOf(false) }

    val triggerBottomSheetModal: () -> Unit = {
        showSheet = !showSheet
        systemUiController.setStatusBarColor(color = Color(0x00ACACAC), darkIcons = true)
        systemUiController.setNavigationBarColor(color = Color.Black, darkIcons = true)
    }
    SideEffect {
        if (!showSheet) {
            systemUiController.setSystemBarsColor(
                color = Color.White,
                darkIcons = true
            )
        }
    }

    if (showSheet) {
        IngredientsBottomSheet(
            onDismiss = triggerBottomSheetModal,
            productsDataFlow = viewModel.productsDatabase,
            loadMoreObjects = { searchText, onLoadCompleted ->
                viewModel.fetchMoreProducts(searchText, onLoadCompleted) },
            onListUpdate = { viewModel.fetchProductsDatabase(it) },
            onSave = { viewModel.addIngredient(it) }
        )

    }
    Column(
        modifier = Modifier.background(color = Color.White).fillMaxSize().padding(top = 60.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "My Basket",
                        fontSize = 25.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = TextStyle(letterSpacing = -1.sp)
                    )
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(
                                1.dp, Color(0xFFF5F5F5), shape = RoundedCornerShape(22.dp)
                            )
                            .clip(RoundedCornerShape(22.dp)).width(50.dp).height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF5F5F5),
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(5.dp),
                        onClick = { deleteSelected = true }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.delete_bin_5_line__2_),
                            contentDescription = "Back",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.width(20.dp).height(20.dp)
                        )
                    }
                }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 20.dp, start = 20.dp, bottom = 5.dp).height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            1.dp, Color(126, 198, 11), RoundedCornerShape(20.dp)
                        )
                        .clip(RoundedCornerShape(20.dp)).width(125.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(126, 198, 11)
                    ),
                    contentPadding = PaddingValues(15.dp),
                    onClick = { triggerBottomSheetModal() }
                ) {
                    Text(
                        "Add items +",
                        fontSize = 13.sp,
                        fontFamily = Montserrat,
                        color = Color(126, 198, 11),
                    )
                }
            }
            LazyColumn (modifier = Modifier.padding(end = 20.dp, start = 20.dp, bottom = 110.dp)) {
                items(
                    items = productsList.value,
                    key = { ingredient -> ingredient.id }
                ) { ingredient ->
                    val product = ingredient
                    BasketIngredient(
                        ingredient = product,
                        isShown = !selectedProductsIds.value.contains(product.id)||!deleteSelected,
                        onSelectionChange = {bool ->
                            if(bool) viewModel.selectIngredient(product.id)
                            else viewModel.unselectIngredient(product.id) },
                        onIngredientUpdate = { viewModel.saveBasket() }
                    )
                }
            }
        }

    }

    LaunchedEffect(deleteSelected) {
        if(deleteSelected) {
            delay(800)
            viewModel.deleteSelectedIngredients()
            deleteSelected = false
        }
    }
}

@Composable
fun BasketIngredient(ingredient: Ingredient, isShown: Boolean,
                     onSelectionChange: (isSelected: Boolean) -> Unit,
                     onIngredientUpdate: () -> Unit) {
    var isSelected by remember { mutableStateOf(false) }

    var quantity by remember { mutableStateOf(ingredient.quantity) }
    val type by remember { mutableStateOf(ingredient.type) }
    val unit by remember { mutableStateOf(ingredient.unit) }

    var showItem by remember { mutableStateOf(true) }
    if(!isShown) {
        showItem = false
    }


    AnimatedVisibility(visible = showItem, exit = shrinkOut(shrinkTowards = Alignment.TopCenter)) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().height(140.dp)
                    .border(1.dp, Color(0xFFE8E8E8), RoundedCornerShape(15.dp))
                    .clip(RoundedCornerShape(10.dp)).background(Color.White).padding(10.dp)
            ) {
                AsyncImage(
                    model = ingredient.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(200.dp).width(130.dp).clip(RoundedCornerShape(12.dp))
                )
                Box(
                    modifier = Modifier.size(35.dp).align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            if (isSelected) Color(0xFF7EC60B)
                            else Color(0xFFECECEC)
                        )
                        .clickable {
                            isSelected = !isSelected
                            onSelectionChange(isSelected)
                        }
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
                Box(modifier = Modifier.padding(start = 140.dp, top = 10.dp).fillMaxSize()) {
                    Box ( modifier = Modifier.width(115.dp) ) {
                        Text(
                            text = type,
                            lineHeight = 18.sp,
                            modifier = Modifier.align(Alignment.TopStart),
                            fontWeight = FontWeight.Normal,
                            fontFamily = Montserrat
                        )
                    }
                    Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                                contentDescription = "Profile Image",
                                modifier = Modifier.size(50.dp).padding(end = 15.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable {
                                        ingredient.decrementQuantity(5)
                                        quantity = ingredient.quantity
                                        onIngredientUpdate()
                                    }
                            )
                            Text(
                                quantity.toString() + ValueParser.quantityUnitToString(unit),
                                color = Color.Black,
                                fontFamily = Montserrat,
                                fontSize = 14.sp
                            )
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                                contentDescription = "Profile Image",
                                modifier = Modifier.size(50.dp).padding(start = 15.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable {
                                        ingredient.incrementQuantity(5)
                                        quantity = ingredient.quantity
                                        onIngredientUpdate()
                                    }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
