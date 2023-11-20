package android.kotlin.foodclub.views.authentication.forgotPassword

import android.kotlin.foodclub.utils.composables.AuthLayout
import android.kotlin.foodclub.utils.composables.ConfirmButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import android.kotlin.foodclub.R

@Composable
fun EmailSentView(onClick: () -> Unit, onBackButtonClick: () -> Unit) {
    AuthLayout(
        header = stringResource(id = R.string.password_changed),
        onBackButtonClick = { onBackButtonClick() }) {
            ConfirmButton(
                enabled = true,
                text = stringResource(id = R.string.log_in)
            ) { onClick() }
    }
}