@file:JvmName("HomeViewKt")

package com.example.foodclub.views.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodclub.viewmodels.home.HomeViewModel

@Composable
fun HomeView() {
    val viewModel: HomeViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    Text(text = title)
}