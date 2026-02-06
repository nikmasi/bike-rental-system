package com.example.bicyclerentalapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bicyclerentalapp.model.Station

@Composable
fun StationHomeCard(station: Station, distance: Float, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFF1E1E1E),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF2196F3).copy(alpha = 0.2f),
                modifier = Modifier.size(50.dp)
            ) {
                Icon(Icons.Default.LocationOn,
                    contentDescription = null, tint = Color(0xFF2196F3), modifier = Modifier.padding(12.dp))
            }

            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text(station.name, color = Color.White, fontWeight = FontWeight.Bold)
                Text(
                    text = if (distance > 1000) "%.1f km away".format(distance/1000) else "${distance.toInt()} m away",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text("${station.classicBikesCount + station.electroBikesCount} bikes",
                    color = Color.Cyan, fontSize = 12.sp)
//                Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null,
//                    tint = Color.DarkGray, modifier = Modifier.size(14.dp))
            }
        }
    }
}