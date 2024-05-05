package live.foodclub.views.authentication.forgotPassword

import live.foodclub.utils.composables.AuthLayout
import live.foodclub.utils.composables.customComponents.ConfirmButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import live.foodclub.R

@Composable
fun EmailSentView(
    onClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    AuthLayout(
        header = stringResource(id = R.string.password_changed),
        onBackButtonClick = { onBackButtonClick() }
    ) {
            ConfirmButton(
                enabled = true,
                text = stringResource(id = R.string.log_in)
            ) { onClick() }
    }
}