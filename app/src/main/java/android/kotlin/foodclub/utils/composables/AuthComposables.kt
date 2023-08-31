package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.ui.theme.PlusJakartaSans
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomAuthButton(enabled: Boolean, title: String, onClick: () -> Unit){
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .height(56.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7EC60B),
            disabledContainerColor = Color(0xFFC9C9C9),
            disabledContentColor = Color.White,
            contentColor = Color.White
        )
    ) {
        Text(text = title, fontSize = 16.sp)
    }
}

@Composable
fun CustomCodeTextField(onFillCallback: (Boolean, String) -> Unit) {
    var text by remember { mutableStateOf("") }
    BasicTextField(modifier = Modifier.fillMaxWidth(),
        value = text,
        singleLine = true,
        onValueChange = {
            if (it.length <= 6) { /* TODO */
                text = it
            }
            onFillCallback(it.length >= 6, text) /* TODO */
        },
        enabled = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        keyboardActions = KeyboardActions(),
        decorationBox = {
            Row(
                modifier = Modifier.size(352.dp, 72.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(5) { index ->
                    Box(
                        modifier = Modifier
                            .size(64.dp, 72.dp)
                            .border(
                                1.dp,
                                color = if(text.length == index) Color(0xFF7EC60B)
                                else Color(0xFF000000).copy(alpha = 0.3f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text.getOrNull(index)?.toString() ?: "",
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontFamily = PlusJakartaSans, fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        })
}