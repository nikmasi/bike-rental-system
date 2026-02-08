package com.example.bicyclerentalapp.ui.components.wrapper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.ui.components.AppButton

@Composable
fun LargeCardsWrapper(
    topContent: @Composable ColumnScope.() -> Unit,
    buttonActive: Boolean = false,
    buttonText:String = "",
    buttonClick: () -> Unit={},
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            color = Color(0xFF1E1E1E),
            onClick = onClick
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                topContent()
                Spacer(modifier = Modifier.weight(1f))

                if (buttonActive) {
                    AppButton(
                        onClick = buttonClick,
                        text = buttonText,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}