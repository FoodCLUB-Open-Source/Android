package android.kotlin.foodclub.views.authentication

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewModels.authentication.TermsAndConditionsViewModel
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@SuppressLint("UnrememberedMutableInteractionSource", "StateFlowValueCalledInComposition")
@Composable
fun TermsAndConditions(
    navController: NavHostController,
    viewModel: TermsAndConditionsViewModel
) {

    val checkedState = remember { mutableStateOf(false) }

    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Asset(R.raw.tandc),
        isZoomEnable = true
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(50.dp)) {
        VerticalPDFReader(
            state = pdfState,
            modifier = Modifier
                .height(500.dp)
                .background(color = Color.White)
                .border(1.dp, Color.Black, RectangleShape)
                .padding(5.dp)

        )
        Row (modifier = Modifier.padding(top = 20.dp)){
            Text(text = "Accept terms and conditions",modifier = Modifier.padding(top = 15.dp, end = 5.dp))
            Checkbox(checked = checkedState.value,
                onCheckedChange = { checkedState.value = !checkedState.value},
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF7EC60B)
                )
            )
        }

        val context = LocalContext.current
        
        Button(onClick = {

            if(checkedState.value == true)
                    viewModel.onChecked(checkedState.value,navController)
            else
                    Toast.makeText(context,"Please accept terms and conditions to proceed",Toast.LENGTH_SHORT).show()

        }, modifier = Modifier.fillMaxWidth().padding(20.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7EC60B),
                disabledContainerColor = Color(0xFFC9C9C9),
                disabledContentColor = Color.White,
                contentColor = Color.White
            )
        ) {
                Text(text = "Proceed")
        }

    }
    


}

