package com.example.bicyclerentalapp.ui.rental.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.model.Station

@Composable
fun BottomInfoCard(
    isElectro: Boolean,
    station: Station?
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {

                Surface(
                    color = Color(0xFF2E2E2E),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = if(isElectro) "ELECTRO" else "CLASSIC",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF00E676)
                    )
                }
            }
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(20.dp)
                    .background(Color.DarkGray, RoundedCornerShape(50))
            ) {
                Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFF2196F3),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.size(20.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("P", color = Color.White, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    Text(
                        text = station?.name ?: "Error",
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color.LightGray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column {

                    //Text("Price", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                    Text(if(isElectro) station?.electroPrice.toString()
                    else station?.classicPrice.toString() + " euro/h", color = Color.White, style = MaterialTheme.typography.titleSmall)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}