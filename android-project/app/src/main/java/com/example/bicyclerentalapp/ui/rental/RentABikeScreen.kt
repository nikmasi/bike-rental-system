package com.example.bicyclerentalapp.ui.rental

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bicyclerentalapp.ui.components.wrapper.CardsWrapper
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper

@Composable
fun RentABikeScreen(
    bikeName: String = "Mountain Pro X",
    price: String = "5€/hr",
    distance: String = "150m",
    location: String = "Trg Slobode 5",
    onScanClick: () -> Unit = {}
) {
    ScreenWrapper(title = "Rent a Bike") {

        CardsWrapper(
            onClick = onScanClick,
            topContent = {
                // icon for QR
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = "Scan QR",
                    tint = Color.Cyan,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Scan QR Code",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Scan the code on the handlebar",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            },
            bottomContent = {
                Text(
                    text = bikeName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = price, color = Color.Gray, fontSize = 16.sp)
                    Text(text = " • ", color = Color.Gray)
                    Text(text = distance, color = Color.Cyan, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = location,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        )
    }
}