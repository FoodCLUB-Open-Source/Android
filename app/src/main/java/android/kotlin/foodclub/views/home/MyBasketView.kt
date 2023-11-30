package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.dim_60)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = dimensionResource(id = R.dimen.dim_20), end = dimensionResource(id = R.dimen.dim_20)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.my_basket),
                        fontSize = dimensionResource(id = R.dimen.fon_25).value.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = TextStyle(letterSpacing = -1.sp)
                    )
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .border(
                                dimensionResource(id = R.dimen.dim_1), containerColor, shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_22))
                            )
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_22)))
                            .width(dimensionResource(id = R.dimen.dim_50))
                            .height(dimensionResource(id = R.dimen.dim_50)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_5)),
                        onClick = { deleteSelected = true }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.delete_bin_5_line__2_),
                            contentDescription = stringResource(id = R.string.go_back),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.width(dimensionResource(id = R.dimen.dim_20)).height(dimensionResource(id = R.dimen.dim_20))
                        )
                    }
                }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = dimensionResource(id = R.dimen.dim_20), start = dimensionResource(id = R.dimen.dim_20), bottom =dimensionResource(id = R.dimen.dim_5))
                    .height(dimensionResource(id = R.dimen.dim_80)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            dimensionResource(id = R.dimen.dim_1), Color(126, 198, 11), RoundedCornerShape(dimensionResource(id = R.dimen.dim_20))
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20))).width(dimensionResource(id = R.dimen.dim_125)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(126, 198, 11)
                    ),
                    contentPadding = PaddingValues( dimensionResource(id = R.dimen.dim_15)),
                    onClick = { triggerBottomSheetModal() }
                ) {
                    Text(
                        text = stringResource(id = R.string.add_items_plus),
                        fontSize = dimensionResource(id = R.dimen.fon_13).value.sp,
                        fontFamily = Montserrat,
                        color = Color(126, 198, 11),
                    )
                }
            }
            LazyColumn (modifier = Modifier.padding(end = dimensionResource(id = R.dimen.dim_20), start = dimensionResource(id = R.dimen.dim_20), bottom = dimensionResource(id = R.dimen.dim_110))) {
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
                modifier = Modifier.fillMaxWidth().height(dimensionResource(id = R.dimen.dim_140))
                    .border(dimensionResource(id = R.dimen.dim_1), Color(0xFFE8E8E8), RoundedCornerShape( dimensionResource(id = R.dimen.dim_15)))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10))).background(Color.White).padding(dimensionResource(id = R.dimen.dim_10))
            ) {
                AsyncImage(
                    model = ingredient.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(dimensionResource(id = R.dimen.dim_200)).width(dimensionResource(id = R.dimen.dim_130)).clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)))
                )
                Box(
                    modifier = Modifier.size( dimensionResource(id = R.dimen.dim_35)).align(Alignment.TopEnd)
                        .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_30)))
                        .background(
                            if (isSelected) foodClubGreen
                            else Color(0xFFECECEC)
                        )
                        .clickable {
                            isSelected = !isSelected
                            onSelectionChange(isSelected)
                        }
                        .padding(dimensionResource(id = R.dimen.dim_4)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
                Box(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dim_140), top = dimensionResource(id = R.dimen.dim_10)).fillMaxSize()) {
                    Box ( modifier = Modifier.width(dimensionResource(id = R.dimen.dim_115)) ) {
                        Text(
                            text = type,
                            lineHeight = dimensionResource(id = R.dimen.fon_18).value.sp,
                            modifier = Modifier.align(Alignment.TopStart),
                            fontWeight = FontWeight.Normal,
                            fontFamily = Montserrat
                        )
                    }
                    Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                                contentDescription = stringResource(id = R.string.profile_picture),
                                modifier = Modifier.size(dimensionResource(id = R.dimen.dim_50)).padding(end = dimensionResource(id = R.dimen.dim_15))
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
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
                                fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                            )
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                                contentDescription = stringResource(id = R.string.profile_picture),
                                modifier = Modifier.size(dimensionResource(id = R.dimen.dim_50)).padding(start = dimensionResource(id = R.dimen.dim_15))
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
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
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))
        }
    }
}
