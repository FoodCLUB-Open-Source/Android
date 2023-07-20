package com.example.foodclub.views.authentication

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.foodclub.viewmodels.authentication.LoginViewModel

@Composable
fun LoginView(navController: NavHostController) {
    val viewModel: LoginViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    Button(onClick = {
        navController.popBackStack()
        navController.navigate("home_graph") }) {
        Text(text = "Navigate to Home")
    }

}
