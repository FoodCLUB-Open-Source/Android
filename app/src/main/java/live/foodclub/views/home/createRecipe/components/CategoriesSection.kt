package live.foodclub.views.home.createRecipe.components

import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.categorySelectedBlue
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.domain.enums.Category
import live.foodclub.utils.composables.customComponents.SearchBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesSection(
    categories: List<Category>,
    onCategoryRemove: (Category) -> Unit,
    onCategoryAdd: (Category) -> Unit,
    onCategoriesClear: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.categories),
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = TextUnit(-0.72f, TextUnitType.Sp),
                fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
            )
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.clear_all)),
                onClick = { onCategoriesClear() },
                style = TextStyle(
                    color = foodClubGreen,
                    fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                    letterSpacing = TextUnit(-0.48f, TextUnitType.Sp),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_32)))
        SearchBar(
            onTextChange = { searchText = it },
            placeholder = stringResource(id = R.string.categories_search_placeholder)
        )
    }
    FlowRow {
        if (searchText.isNotEmpty() && categories.size < 3) {
            Category.entries.filter {
                it.displayName.startsWith(searchText.replaceFirstChar(Char::titlecase))
                        && !categories.contains(it)
            }.forEachIndexed { _, content ->
                Card(
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_30)),
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dim_5))
                        .height(dimensionResource(id = R.dimen.dim_32)),
                    colors = CardDefaults.cardColors(foodClubGreen),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(id = R.dimen.dim_8)
                        )
                    ) {
                        Text(
                            text = content.displayName,
                            fontFamily = Montserrat,
                            fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                            color = Color.White,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_8)),
                            maxLines = 1
                        )
                        Button(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_22)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(
                                dimensionResource(id = R.dimen.dim_0)
                            ),
                            shape = CircleShape,
                            onClick = { onCategoryAdd(content) }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_clear_24),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.dim_15))
                                    .clip(
                                        RoundedCornerShape(
                                            dimensionResource(
                                                id = R.dimen.dim_12
                                            )
                                        )
                                    )
                                    .rotate(45f)
                            )
                        }
                    }
                }
            }
        } else {
            categories.forEachIndexed { _, content ->
                Card(
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_30)),
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dim_5))
                        .height(dimensionResource(id = R.dimen.dim_32)),
                    colors = CardDefaults.cardColors(categorySelectedBlue),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(id = R.dimen.dim_8)
                        )
                    ) {
                        Text(
                            text = content.displayName,
                            fontFamily = Montserrat,
                            fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                            color = Color.White,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_8)),
                            maxLines = 1
                        )
                        Button(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_22)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_0)),
                            shape = CircleShape,
                            onClick = { onCategoryRemove(content) }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_clear_24),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.dim_15))
                                    .clip(
                                        RoundedCornerShape(dimensionResource(id = R.dimen.dim_12))
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}