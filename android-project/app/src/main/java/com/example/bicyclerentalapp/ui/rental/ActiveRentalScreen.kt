package com.example.bicyclerentalapp.ui.rental

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bicyclerentalapp.ui.components.wrapper.CardsWrapper
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper

@Composable
fun ActiveRentalScreen(
    onFinishClick: () -> Unit = {},
    rentalViewModel: RentalViewModel
) {
    val station by rentalViewModel.selectedStation.collectAsState()
    val bike by rentalViewModel.selectedBike.collectAsState()

    val seconds by rentalViewModel.secondsActive.collectAsState()

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
                Text(
                    text = "#B214",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        )
    }
}