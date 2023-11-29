package android.kotlin.foodclub.views.authentication

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.navigation.auth.AuthScreen
import android.kotlin.foodclub.utils.composables.TermsAndConditionsInfoFooter
import android.kotlin.foodclub.viewModels.authentication.MainLogInAndSignUpViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun MainLogInAndSignUp(
    navController: NavHostController,
    viewModel: MainLogInAndSignUpViewModel
) {

    var interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()


    if (!isPressed) {
        viewModel.reverseButtonUi()
    }

    var interactionSource1 = remember { MutableInteractionSource() }
    val isPressed1 by interactionSource1.collectIsPressedAsState()


    if (!isPressed1) {
        viewModel.reverseButtonUi()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier
                .weight(7F)
                .fillMaxSize()
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            Image(
                painterResource(id = R.drawable.welcome_logo),
                contentDescription = stringResource(id = R.string.app_logo),
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
            Image(
                painterResource(id = R.drawable.foodclub),
                contentDescription = stringResource(id = R.string.app_title),
                modifier = Modifier.height(40.dp)

            )
        }
        Column(
            Modifier
                .weight(8F)
                .fillMaxSize()
                .padding(horizontal = 48.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(126, 198, 11, 255),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(15.dp),
                    onClick = {
                        navController.navigate(AuthScreen.TermsAndConditions.route)
                    }
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.sign_up),
                        fontSize = 14.sp,
                        fontFamily = Montserrat
                    )
                }
            }

            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    color = Color.Black,
                    text = stringResource(id = R.string.already_have_account),
                    fontFamily = Montserrat,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 5.dp)
                )

                ClickableText(
                    text = AnnotatedString(stringResource(id = R.string.login_arrow)),
                    onClick = {
                        navController.navigate(route = AuthScreen.Login.route)
                    },
                    style = TextStyle(
                        color = Color(126, 198, 11, 255),
                        fontFamily = Montserrat,
                        fontSize = 14.sp
                    )
                )
            }
        }
        Box(
            Modifier
                .weight(5F)
                .fillMaxSize()
                .padding(vertical = 32.dp)
        ) {
            TermsAndConditionsInfoFooter() { viewModel.termsAndConditions() }
        }
    }
}

@Composable
@Preview
fun MainLogInAndSignUp() {
    MainLogInAndSignUp(
        rememberNavController(),
        MainLogInAndSignUpViewModel()
    )
}
