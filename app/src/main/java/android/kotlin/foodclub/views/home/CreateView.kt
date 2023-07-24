package com.example.foodclub.views.home

import android.kotlin.foodclub.viewmodels.home.CreateViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CreateView() {
    val viewModel: CreateViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    Text(text = title)
}
