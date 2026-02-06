package com.example.bicyclerentalapp.ui.rental

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.ui.components.wrapper.CardsWrapper
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.rental.components.BottomInfoCard

@Composable
fun FinishParkingRentalScreen(
    bitmap: Bitmap?, onConfirm: () -> Unit, rentalViewModel: RentalViewModel
) {
    val station by rentalViewModel.selectedStation.collectAsState()
    val bike by rentalViewModel.selectedBike.collectAsState()

    ScreenWrapper(title = "Finish Rental") {
        CardsWrapper(onClick = {}, buttonText = "Finish", buttonClick = onConfirm
        , buttonActive = true,
            topContent = {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Captured Bike",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            },
            bottomContent = {
                BottomInfoCard(bike?.isElectro == true, station)
            }
        )
    }
}