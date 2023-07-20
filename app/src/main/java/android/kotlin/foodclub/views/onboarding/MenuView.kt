package com.example.foodclub.views.onboarding

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.foodclub.viewmodels.onboarding.MenuViewModel

@Composable
fun MenuView(navController: NavHostController) {
    val viewModel: MenuViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    Button(onClick = { navController.navigate("auth_graph") }) {
        Text(text = "Navigate to Auth")
    }

}
