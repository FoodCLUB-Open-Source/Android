package live.foodclub.views.authentication

import live.foodclub.R
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.navigation.auth.AuthScreen
import live.foodclub.utils.composables.customComponents.TermsAndConditionsInfoFooter
import live.foodclub.viewModels.authentication.mainLogin.MainLogInAndSignUpViewModel
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun MainLogInAndSignUp(
    navController: NavHostController,
    viewModel: MainLogInAndSignUpViewModel
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    if (!isPressed) {
        viewModel.reverseButtonUi()
    }

    val interactionSource1 = remember { MutableInteractionSource() }
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
                .weight(13F)
                .fillMaxSize()
                .padding(top = dimensionResource(id = R.dimen.dim_120)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_36))
        ) {
            Image(
                painterResource(id = R.drawable.welcome_logo),
                contentDescription = stringResource(id = R.string.app_logo),
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_80))
                    .height(dimensionResource(id = R.dimen.dim_80))
            )
            Image(
                painterResource(id = R.drawable.foodclub),
                contentDescription = stringResource(id = R.string.app_title),
                modifier = Modifier.height(dimensionResource(id = R.dimen.dim_40))

            )
        }
        Column(
            Modifier
                .weight(5F)
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.dim_48),
                    vertical = dimensionResource(id = R.dimen.dim_40)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_18))
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_8))) {
                Button(
                    shape = RectangleShape,
                    modifier = Modifier
                        .border(
                            dimensionResource(id = R.dimen.dim_1),
                            Color.LightGray,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10))
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = foodClubGreen,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_15)),
                    onClick = {
                        navController.navigate(AuthScreen.TermsAndConditions.route)
                    }
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.sign_up),
                        fontSize = dimensionResource(id = R.dimen.fon_19_5).value.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            var overflowText by remember {
                mutableStateOf(false)
            }

            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        color = Color.Black,
                        text = stringResource(id = R.string.already_have_account),
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.dim_7))
                    )
                    if (overflowText) {

                        ClickableText(
                            text = AnnotatedString(stringResource(id = R.string.login_arrow)),
                            onClick = {
                                navController.navigate(route = AuthScreen.Login.route)
                            },
                            style = TextStyle(
                                color = colorResource(id = R.color.login_text_color),
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Medium,
                                fontSize = dimensionResource(id = R.dimen.fon_16).value.sp
                            )
                        )
                    }
                }

                if (!overflowText) {

                    ClickableText(
                        text = AnnotatedString(stringResource(id = R.string.login_arrow)),
                        onClick = {
                            navController.navigate(route = AuthScreen.Login.route)
                        },
                        style = TextStyle(
                            color = colorResource(id = R.color.login_text_color),
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium,
                            fontSize = dimensionResource(id = R.dimen.fon_16).value.sp
                        ),
                        onTextLayout = {
                            overflowText = it.didOverflowHeight
                        }
                    )
                }
            }
        }

        Box(
            Modifier
                .weight(3F)
                .fillMaxSize()
                .padding(vertical = dimensionResource(id = R.dimen.dim_30))
        ) { TermsAndConditionsInfoFooter() }
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
