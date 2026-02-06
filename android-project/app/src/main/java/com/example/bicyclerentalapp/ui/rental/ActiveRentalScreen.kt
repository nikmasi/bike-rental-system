package com.example.bicyclerentalapp.ui.rental

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.bicyclerentalapp.ui.components.wrapper.CardsWrapper
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.rental.components.BottomInfoCard

@Composable
fun ActiveRentalScreen(
    onFinishClick: () -> Unit = {},
    rentalViewModel: RentalViewModel
) {
    val bike by rentalViewModel.selectedBike.collectAsState()

    val seconds by rentalViewModel.secondsActive.collectAsState()

    val station by rentalViewModel.selectedStation.collectAsState()

    ScreenWrapper(title = "Active Rental") {
        CardsWrapper(onClick = {}, buttonText = "Finish", buttonClick = {
            onFinishClick()
            rentalViewModel.stopRental()
        }, buttonActive = true,
            topContent = {

                Text(
                    text = rentalViewModel.formatSecondsToMinutes(seconds),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(text = "Riding from ${station?.name}",color = Color.White)
                Text(text = "Bike ID: #${bike?.idBike}",color = Color.White)
            },
            bottomContent = {
                BottomInfoCard(bike?.isElectro == true,station)
            }
        )
    }
}