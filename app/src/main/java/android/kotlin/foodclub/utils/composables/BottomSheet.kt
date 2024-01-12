package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.disabledContainerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.models.others.BottomSheetItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(itemList: List<BottomSheetItem>, sheetTitle: String,
                modifier: Modifier = Modifier, enableDragHandle: Boolean = false,
                onDismiss: () -> Unit = {}, containerColor:Color, titleSpace:Boolean) {
    ModalBottomSheet(
        containerColor = containerColor,
        onDismissRequest = { onDismiss() },
        dragHandle = if(enableDragHandle) { { BottomSheetDefaults.DragHandle() } } else null,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if(titleSpace) {
                Text(
                    text = sheetTitle,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    modifier = if (!enableDragHandle) Modifier.padding(top =  dimensionResource(id = R.dimen.dim_36)) else Modifier
                )
                Divider(
                    color = Color.Gray,
                    thickness = dimensionResource(id = R.dimen.dim_0pt8),
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.dim_16))
                )
            }else{
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_25)))
            }
            itemList.forEach {
                BottomSheetItem(
                    icon = it.resourceId,
                    text = it.title,
                    onDismiss = onDismiss,
                    onClick = it.onClick
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_25)))
            if(!titleSpace){
                Button(
                    onClick = { onDismiss()},
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
                    modifier = Modifier.height(dimensionResource(id = R.dimen.dim_56)).clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10))).padding(dimensionResource(id = R.dimen.dim_10),dimensionResource(id = R.dimen.dim_0),dimensionResource(id = R.dimen.dim_10),dimensionResource(id = R.dimen.dim_0)).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = foodClubGreen,
                        disabledContainerColor = disabledContainerColor,
                        disabledContentColor = Color.White,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Cancel ", fontSize = dimensionResource(id = R.dimen.fon_16).value.sp)
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_25)))
        }

    }
}

