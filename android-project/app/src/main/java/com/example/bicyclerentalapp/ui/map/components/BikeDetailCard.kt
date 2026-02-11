package com.example.bicyclerentalapp.ui.map.components

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
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.bicyclerentalapp.ui.components.AppButton

@Composable
fun BikeDetailCard(
    modifier: Modifier = Modifier,
    station: Station,
    typeLabel: String,
    onClose: () -> Unit,
    distanceToParking: String,
    onRent: (Station, Boolean) -> Unit,
    buttonCond: Boolean = true
) {
    val price = if (typeLabel=="ELECTRO") station.electroPrice.toString() + " euro/h" else station.classicPrice.toString() + " euro/h"
    val typeColor = if (typeLabel=="ELECTRO") Color(0xFF00E676) else Color(0xFF2196F3)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
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
                        color = typeColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = typeLabel,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = typeColor
                        )
                    }
                }
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(15.dp)
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
                            text = station.name,
                            modifier = Modifier.padding(start = 8.dp),
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Column {
                        //Text("Price", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                        Text(price, color = Color.White, style = MaterialTheme.typography.titleSmall)

                        val count = if (typeLabel=="ELECTRO") station.electroBikesCount else station.classicBikesCount
                        Text(text = "$count available", color = Color.Gray,style = MaterialTheme.typography.titleSmall)

                        Text(text = "${distanceToParking} away", color= Color.White, style = MaterialTheme.typography.titleSmall)
                    }

                }

                Box(modifier = Modifier
                    .size(100.dp, 70.dp)
                    .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.DirectionsBike,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(38.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (buttonCond){
                AppButton(onClick = {
                    val hasBikes = if (typeLabel=="ELECTRO") station.electroBikesCount > 0 else station.classicBikesCount > 0
                    if (hasBikes) {
                        onRent(station,typeLabel=="ELECTRO")
                    }
                }, text= "Rent ${if (typeLabel=="ELECTRO") "Electro" else "Classic"}", height = 40.dp)
            }
        }
    }
}