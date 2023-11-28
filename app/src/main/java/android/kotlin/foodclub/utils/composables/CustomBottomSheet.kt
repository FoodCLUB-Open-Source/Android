package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.others.BottomSheetItem
import android.kotlin.foodclub.config.ui.Montserrat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue.Hidden
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
fun CustomBottomSheet(
    itemList: List<BottomSheetItem>, sheetTitle: String,
    modifier: Modifier = Modifier, enableDragHandle: Boolean = false,
    onDismiss: () -> Unit = {}, containerColor: Color = Color.White, titleSpace: Boolean = true
) {
    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        dragHandle = if (enableDragHandle) {
            { BottomSheetDefaults.DragHandle() }
        } else null,
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
                    onClick = { onDismiss()},
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
fun BottomSheetItem(
    icon: Int?,
    text: String,
    onDismiss: () -> Unit,
    onClick: () -> Unit
) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockReportView(modifier: Modifier = Modifier, enableDragHandle: Boolean = false,
                    onDismiss: () -> Unit = {}, containerColor:Color, text:String, type:String, userId:String, actionBlockReport:Any){

    ModalBottomSheet(
        containerColor = containerColor,
        onDismissRequest = { onDismiss() },
        dragHandle = if(enableDragHandle) { { BottomSheetDefaults.DragHandle() } } else null,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Fill the max width of the parent
                    .padding(horizontal = 25.dp), // Apply horizontal padding
                verticalAlignment = Alignment.CenterVertically, // Center items vertically
                horizontalArrangement = Arrangement.Start
            ){

                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .background(color = Color.Black, RoundedCornerShape(8.dp))
                        .size(35.dp),
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.back_icon),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                )


                Text(
                    text = "Are you sure you want to?",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f) // Text takes up the rest of the space
                        .padding(end = 16.dp)
                )

            }
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){

                Text(
                    text = "$type $userId?",
                    fontSize = 28.sp,
                    color = Color.White,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(25.dp))
                Image(
                    painterResource(id = R.drawable.story_user),
                    contentDescription = "profile_picture",
                    modifier = Modifier
                        .clip(RoundedCornerShape(60.dp))
                        .height(124.dp)
                        .width(124.dp))
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Fill the max width of the parent
                    .padding(16.dp), // Apply padding around the Row
                verticalAlignment = Alignment.CenterVertically, // Align items vertically in the center
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Button(
                    onClick = { onDismiss()},
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(56.dp).clip(RoundedCornerShape(10.dp)).padding(10.dp,0.dp,10.dp,0.dp).weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff333333),
                        disabledContainerColor = Color(0xFFC9C9C9),
                        disabledContentColor = Color.White,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Cancel", fontSize = 16.sp)
                }

                Button(
                    onClick = { actionBlockReport },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(56.dp).clip(RoundedCornerShape(10.dp)).padding(10.dp,0.dp,10.dp,0.dp).weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7EC60B),
                        disabledContainerColor = Color(0xFFC9C9C9),
                        disabledContentColor = Color.White,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "$type", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
        }

    }

}