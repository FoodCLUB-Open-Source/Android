package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.data.models.BottomSheetItem
import android.kotlin.foodclub.ui.theme.Montserrat
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
                    modifier = if (!enableDragHandle) Modifier.padding(top = 36.dp) else Modifier
                )
                Divider(
                    color = Color.Gray,
                    thickness = 0.8.dp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }else{
                Spacer(modifier = Modifier.height(25.dp))
            }
            itemList.forEach {
                BottomSheetItem(
                    icon = it.resourceId,
                    text = it.title,
                    onDismiss = onDismiss,
                    onClick = it.onClick
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            if(!titleSpace){
                Button(
                    onClick = {  },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(56.dp).clip(RoundedCornerShape(10.dp)).padding(10.dp,0.dp,10.dp,0.dp).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7EC60B),
                        disabledContainerColor = Color(0xFFC9C9C9),
                        disabledContentColor = Color.White,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Cancel ", fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
        }

    }
}

@Composable
fun BottomSheetItem(icon: Int?, text: String,
                    onDismiss: () -> Unit, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick(); onDismiss() }
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(16.dp))
        icon?.let { painterResource(id = it) }?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold
        )
    }
}