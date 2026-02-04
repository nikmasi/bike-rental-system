package com.example.bicyclerentalapp.ui.rental

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bicyclerentalapp.ui.components.wrapper.CardsWrapper
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.rental.components.QrCodeScannerScreen

@Composable
fun RentABikeScreen(
    bikeName: String = "Mountain Pro X",
    price: String = "5€/hr",
    distance: String = "150m",
    location: String = "Trg Slobode 5",
    onScanClick: () -> Unit = {},
    onQrCodeScanned: (String) -> Unit
) {
    ScreenWrapper(title = "Rent a Bike") {

        CardsWrapper(
            onClick = onScanClick,
            topContent = {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        QrCodeScannerScreen { result ->
                            onQrCodeScanned(result)
                        }
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Scan QR Code",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            bottomContent = {
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
                                    text = "ELECTRO",
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
                                    text = "Central Station",
                                    modifier = Modifier.padding(start = 8.dp),
                                    color = Color.LightGray,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Column {
                                //Text("Price", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                                Text("250 RSD/h", color = Color.White, style = MaterialTheme.typography.titleSmall)
                            }

                            Text(text = "1000m away", color= Color.White, style = MaterialTheme.typography.bodySmall)
                        }

                        Box(modifier = Modifier
                            .size(120.dp, 80.dp)
                            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.DirectionsBike,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        )
    }
}