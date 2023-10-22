package android.kotlin.foodclub.views.authentication.forgotPassword

import android.kotlin.foodclub.utils.composables.AuthLayout
import android.kotlin.foodclub.utils.composables.ConfirmButton
import androidx.compose.runtime.Composable

@Composable
fun EmailSentView(onClick: () -> Unit, onBackButtonClick: () -> Unit) {
    AuthLayout(header = "Password Changed",
        onBackButtonClick = { onBackButtonClick() }) {
        ConfirmButton(
            enabled = true,
            text = "Log in"
        ) { onClick() }
    }
}