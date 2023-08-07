package com.example.foodclub.views.authentication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodclub.viewmodels.authentication.SignupViewModel

@Composable
fun SignupView() {
    val viewModel: SignupViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    Text(text = title)
}