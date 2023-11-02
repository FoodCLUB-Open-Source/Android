package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.domain.models.others.BottomSheetItem
import android.kotlin.foodclub.config.ui.Montserrat
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
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue.Hidden
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Custom Material Design modal bottom sheet.
 *
 * Modal bottom sheets are used as an alternative to inline menus or simple dialogs on mobile,
 * especially when offering a long list of action items, or when items require longer descriptions
 * and icons. Like dialogs, modal bottom sheets appear in front of app content, disabling all other
 * app functionality when they appear, and remaining on screen until confirmed, dismissed, or a
 * required action has been taken.
 *
 * ![Bottom sheet image](https://developer.android.com/images/reference/androidx/compose/material3/bottom_sheet.png)
 *
 * @param itemList List of items included in the bottom sheet column
 * @param sheetTitle Header visible on the top of the bottom sheet
 * @param modifier Optional [Modifier] for the bottom sheet.
 * @param enableDragHandle Optional visual marker to swipe the bottom sheet.
 * @param onDismiss Executes when the user clicks outside of the bottom sheet, after sheet
 *  * animates to [Hidden].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(itemList: List<BottomSheetItem>, sheetTitle: String,
                      modifier: Modifier = Modifier, enableDragHandle: Boolean = false,
                      onDismiss: () -> Unit = {}) {
    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        dragHandle = if(enableDragHandle) { { BottomSheetDefaults.DragHandle() } } else null,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = sheetTitle,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                modifier = if(!enableDragHandle) Modifier.padding(top = 36.dp) else Modifier
            )
            Divider(
                color = Color.Gray,
                thickness = 0.8.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            itemList.forEach {
                BottomSheetItem(
                    icon = it.resourceId,
                    text = it.title,
                    onClick = { it.onClick(); onDismiss() }
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
        }

    }
}

/**
 * Custom Material Design modal bottom sheet item.
 *
 * Modal bottom sheets are used as an alternative to inline menus or simple dialogs on mobile,
 * especially when offering a long list of action items, or when items require longer descriptions
 * and icons. Like dialogs, modal bottom sheets appear in front of app content, disabling all other
 * app functionality when they appear, and remaining on screen until confirmed, dismissed, or a
 * required action has been taken. Item when created can be put into the list with other items and
 * put into [CustomBottomSheet] as an argument. Then items in the list are displayed in a column
 * with layout predefined in [CustomBottomSheet].
 *
 * @param icon Id of an icon resource
 * @param text Text describing the element
 * @param onClick Executes when user clicks on the given element and animates sheet to [Hidden]
 */
@Composable
fun BottomSheetItem(icon: Int, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(16.dp))
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold
        )
    }
}