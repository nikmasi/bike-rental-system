package com.example.bicyclerentalapp.ui.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MapParking(modifier: Modifier, parkingChecked: Boolean, onCheckedChange: (Boolean) ->Unit){
    Row(
        modifier = modifier
            .padding(12.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = parkingChecked,
            onCheckedChange = onCheckedChange
        )
        Text("Parking", color = Color.Black, modifier = Modifier.padding(end = 8.dp))
    }
}