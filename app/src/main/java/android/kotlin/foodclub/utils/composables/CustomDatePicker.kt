package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomDatePicker(
    modifier: Modifier,
    shape: RoundedCornerShape,
    datePickerState: DatePickerState,
    datePickerDialogColors: DatePickerColors,
    datePickerColors: DatePickerColors,
    onDismiss: () -> Unit,
    onSave: (String?) -> Unit
) {
    DatePickerDialog(
        modifier = modifier,
        shape = shape,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                shape = RectangleShape,
                modifier = Modifier
                    .border(
                        1.dp,
                        foodClubGreen,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .clip(RoundedCornerShape(15.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = foodClubGreen,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(15.dp),
                onClick = {
                    val selected = datePickerState.selectedDateMillis
                    if (selected != null){
                        onSave(convertMillisToDate(datePickerState.selectedDateMillis!!))
                    }else{
                        onSave(null)
                    }
                    onDismiss()
                })
            {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                shape = RectangleShape,
                modifier = Modifier
                    .border(
                        1.dp,
                        Color.Gray,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .clip(RoundedCornerShape(15.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(15.dp),
                onClick = {
                    onDismiss()
                })
            {
                Text("Cancel")
            }
        },
        colors = datePickerDialogColors
    ) {
        DatePicker(
            state = datePickerState,
            colors = datePickerColors,
            title = {
                Text(
                    "Select Expiration Date",
                    color = foodClubGreen
                )
            }
        )
    }
}
fun convertMillisToDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = Date(millis)
    return sdf.format(date)
}
