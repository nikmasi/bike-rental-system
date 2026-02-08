package com.example.bicyclerentalapp.ui.rental

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bicyclerentalapp.ui.components.wrapper.CardsWrapper
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.rental.components.BottomInfoCard
import com.example.bicyclerentalapp.ui.rental.components.QrCodeScannerScreen

@Composable
fun RentABikeScreen(
    stationId: Int,
    isElectro: Boolean,
    onScanClick: () -> Unit = {},
    onQrCodeScanned: (String) -> Unit,
    rentalViewModel: RentalViewModel
) {
    val station by rentalViewModel.selectedStation.collectAsState()

    // flag because camera not turn off in that moment when qr scan
    var isScanned by remember { mutableStateOf(false) }

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
                            if (!isScanned) {
                                isScanned = true
                                onQrCodeScanned(result)
                            }
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
                BottomInfoCard(isElectro,station)
            }
        )
    }
}