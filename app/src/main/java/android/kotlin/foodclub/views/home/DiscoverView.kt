package com.example.foodclub.views.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodclub.viewmodels.home.DiscoverViewModel

@Composable
fun DiscoverView() {
    val viewModel: DiscoverViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    Text(text = title)
}
