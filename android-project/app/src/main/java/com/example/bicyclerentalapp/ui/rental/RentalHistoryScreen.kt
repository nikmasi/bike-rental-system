package com.example.bicyclerentalapp.ui.rental

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.rental.components.RentalHistoryCard
import com.example.bicyclerentalapp.ui.theme.BicycleRentalAppTheme

@Composable
fun RentalHistoryScreen(
    onBikeClick: (Int) -> Unit,
    rentalViewModel: RentalViewModel
) {
    val rentalsWithReport by rentalViewModel.allRentalsWithReports.collectAsState()
    ScreenWrapper(title = "Rental History") {

        Text(
            text = "All Bike Rentals",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, top = 24.dp )
        )

        // rental list
        if (rentalsWithReport.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.DirectionsBike,
                        contentDescription = null,
                        tint = Color.Gray.copy(alpha = 0.5f),
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No rentals yet.",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Your past trips will appear here.",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(rentalsWithReport) { rentalWithReport ->
                    RentalHistoryCard(rentalWithReport,{})
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RentalHistoryScreenPreview() {
    BicycleRentalAppTheme {
        //RentalHistoryScreen()
    }
}