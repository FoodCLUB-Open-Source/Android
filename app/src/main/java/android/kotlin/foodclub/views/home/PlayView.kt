package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.viewmodels.home.PlayViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PlayView() {
    val viewModel: PlayViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."
    Text(text = title)
}
