package android.kotlin.foodclub.views.authentication

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.disabledContainerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.viewModels.authentication.TermsAndConditionsViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
        resource = ResourceType.Asset(R.raw.termsandcondition),
        isZoomEnable = true
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding
                    (
                    top = dimensionResource(id = R.dimen.dim_30),
                    start = dimensionResource(id = R.dimen.dim_40)
                )

        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.dim_25))
                    .height(50.dp)
                    .width(50.dp)


            )
            Text(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                text = stringResource(id = R.string.toc_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.dim_20),
                        end = dimensionResource(id = R.dimen.dim_40)
                    )
                    .align(Alignment.CenterVertically),
                color = Color.Black
            )


        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    dimensionResource(id = R.dimen.dim_50),
                    top = dimensionResource(id = R.dimen.dim_90),
                    end = dimensionResource(id = R.dimen.dim_50)
                )

        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.dim_10),
                        end = dimensionResource(id = R.dimen.dim_10),
                        top = dimensionResource(id = R.dimen.dim_10),
                        bottom = dimensionResource(id = R.dimen.dim_60)
                    )
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())

            ) {
                Text(
                    text = stringResource(id = R.string.termsandconditiontext),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                )
            }

        }

        val context = LocalContext.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.dim_730),
                    start = dimensionResource(id = R.dimen.dim_46),
                    end = dimensionResource(id = R.dimen.dim_46)
                )
        ) {
            // DECLINE BUTTON
            Button(
                onClick = {
                    /*
                    TO DO IN THE FUTURE
                     */
                },
                modifier = Modifier
                    .weight(1f)
                    .height(dimensionResource(id = R.dimen.dim_50))
                    .border(
                        width = 1.5.dp,
                        color = foodClubGreen,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_6)),
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = foodClubGreen,
                    disabledContainerColor = disabledContainerColor,
                    disabledContentColor = Color.White,
                )
            ) {
                Text(text = stringResource(id = R.string.decline_terms),
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,)
            }


            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_25)))

            // PROCEED BUTTON
            Button(
                onClick = {
                    /*
                    TO DO IN THE FUTURE
                     */

                }, modifier = Modifier
                    .weight(1f)
                    .height(dimensionResource(id = R.dimen.dim_50)),
                shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_6)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = foodClubGreen,
                    disabledContainerColor = disabledContainerColor,
                    disabledContentColor = Color.White,
                    contentColor = Color.White
                )

            ) {
                Text(text = stringResource(id = R.string.accept),
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TermsAndConditionsPreview() {
    val navController = rememberNavController()
    val viewModel = remember { TermsAndConditionsViewModel() }
    TermsAndConditions(navController = navController, viewModel = viewModel)
}

