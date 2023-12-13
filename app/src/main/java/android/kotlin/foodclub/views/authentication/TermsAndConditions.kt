package android.kotlin.foodclub.views.authentication

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.disabledContainerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.viewModels.authentication.termsAndConditions.TermsAndConditionsEvents
import android.kotlin.foodclub.viewModels.authentication.termsAndConditions.TermsAndConditionsViewModel
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@SuppressLint("UnrememberedMutableInteractionSource", "StateFlowValueCalledInComposition")
@Composable
fun TermsAndConditions(
    navController: NavHostController,
    events: TermsAndConditionsEvents
) {

    val checkedState = remember { mutableStateOf(false) }

    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Asset(R.raw.tandc),
        isZoomEnable = true
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dim_50))) {
        VerticalPDFReader(
            state = pdfState,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dim_500))
                .background(color = Color.White)
                .border(dimensionResource(id = R.dimen.dim_1), Color.Black, RectangleShape)
                .padding(dimensionResource(id = R.dimen.dim_5))

        )
        Row (modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_20))){
            Text(
                text = stringResource(id = R.string.accept_terms),
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_15), end =dimensionResource(id = R.dimen.dim_5))
            )
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = !checkedState.value},
                colors = CheckboxDefaults.colors(
                    checkedColor = foodClubGreen
                )
            )
        }

        val context = LocalContext.current
        
        Button(onClick = {

            if(checkedState.value)
                    events.onChecked(checkedState.value,navController)
            else
                    Toast.makeText(context,"Please accept terms and conditions to proceed",Toast.LENGTH_SHORT).show()

        }, modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dim_20)),

            colors = ButtonDefaults.buttonColors(
                containerColor = foodClubGreen,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = Color.White,
                contentColor = Color.White
            )
        ) {
                Text(text = stringResource(id = R.string.proceed))
        }

    }
    


}

