package com.example.foodclub.views.authentication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodclub.viewmodels.authentication.ForgotPasswordViewModel

@Composable
fun ForgotPasswordView() {
    val viewModel: ForgotPasswordViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    Text(text = title)
}
