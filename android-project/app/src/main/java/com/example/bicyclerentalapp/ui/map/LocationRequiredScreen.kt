package com.example.bicyclerentalapp.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.ui.components.AppButton

@Composable
fun LocationRequiredScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Location is required to use the bike map.", color = Color.DarkGray)
        Spacer(modifier = Modifier.size(16.dp))
        AppButton(
            onClick = onRetry,
            text = "Check again",
            height = 40.dp,
        )
    }
}