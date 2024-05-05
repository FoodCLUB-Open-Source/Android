package live.foodclub.utils.composables.customComponents

import live.foodclub.R
import live.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale


/**
 * Customised date picker
 *
 * @param modifier Modifier which is imposed on [DatePickerDialog]
 * @param datePickerState [DatePickerState]
 * @param onDismiss Function called to hide/close the dialog. This is called both for "Cancel" and
 * "Save" buttons
 * @param onSave Function which is called when save button is clicked. String passed to the function
 * is a date in format "dd MMM yyyy".
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomDatePicker(
    modifier: Modifier,
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onSave: (String?) -> Unit
) {
    val datePickerDialogColors = DatePickerDefaults.colors(
        containerColor = Color.White,
        titleContentColor = Color.White,
        headlineContentColor = Color.White,
    )
    val datePickerColors = DatePickerDefaults.colors(
        weekdayContentColor = Color.Gray,
        selectedDayContainerColor = foodClubGreen,
        todayDateBorderColor = foodClubGreen,
        todayContentColor = foodClubGreen
    )

    DatePickerDialog(
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_6)),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                shape = RectangleShape,
                modifier = Modifier
                    .border(
                        dimensionResource(id = R.dimen.dim_1),
                        foodClubGreen,
                        shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))
                    )
                    .clip(
                        RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = foodClubGreen,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues( dimensionResource(id = R.dimen.dim_15)),
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
                Text(text = stringResource(id = R.string.save))
            }
        },
        dismissButton = {
            TextButton(
                shape = RectangleShape,
                modifier = Modifier
                    .border(
                        dimensionResource(id = R.dimen.dim_1),
                        Color.Gray,
                        shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))
                    )
                    .clip(RoundedCornerShape( dimensionResource(id = R.dimen.dim_15))),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues( dimensionResource(id = R.dimen.dim_15)),
                onClick = {
                    onDismiss()
                })
            {
                Text( text = stringResource(id = R.string.cancel))
            }
        },
        colors = datePickerDialogColors
    ) {
        DatePicker(
            state = datePickerState,
            colors = datePickerColors,
            dateValidator = { timestamp ->
                val today = LocalDate
                    .now()
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()
                    .toEpochMilli()
                timestamp >= today
            }
        )
    }
}
fun convertMillisToDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = Date(millis)
    return sdf.format(date)
}
