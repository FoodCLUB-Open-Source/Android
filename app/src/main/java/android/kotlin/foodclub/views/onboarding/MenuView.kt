package com.example.foodclub.views.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.foodclub.viewmodels.onboarding.MenuViewModel

@Composable
fun MenuView(navController: NavHostController) {
    val viewModel: MenuViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            navController.navigate("auth_graph")
        }) {
            Text(text = "Navigate to Auth")
        }
    }
}
