package com.example.bicyclerentalapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bicyclerentalapp.ui.auth.AuthViewModel
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.map.MapViewModel
import com.example.bicyclerentalapp.ui.rental.components.DefaultLocationClient
import com.example.bicyclerentalapp.ui.theme.BicycleRentalAppTheme
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint

@Composable
fun HomeScreen(
    viewModel: AuthViewModel,
    mapViewModel: MapViewModel,
    onBikeClick: (Int) -> Unit
) {
    val user by viewModel.logInResult.collectAsState()
    val stations by mapViewModel.uiState.collectAsState()

    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<GeoPoint?>(null) }

    // start location
    LaunchedEffect(Unit) {
        val locationClient =
            DefaultLocationClient(context, LocationServices.getFusedLocationProviderClient(context))
        locationClient.getLocationUpdates(5000L).collect { location ->
            userLocation = GeoPoint(location.latitude, location.longitude)
        }
    }

    val nearestStations = remember(stations, userLocation) {
        if (userLocation == null) stations
        else {
            stations.sortedBy { station ->
                mapViewModel.calculateDistance(
                    userLocation!!.latitude, userLocation!!.longitude,
                    station.latitude, station.longitude
                )
            }.take(5) // take first 5
        }
    }

    ScreenWrapper(title = "Welcome, ${user?.firstname ?: ""}!") {

        Text(
            text = "Nearest Stations",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, top = 24.dp )
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(nearestStations) { station ->
                val distance = userLocation?.let {
                    mapViewModel.calculateDistance(it.latitude, it.longitude, station.latitude, station.longitude)
                } ?: 0f

                StationHomeCard(
                    station = station,
                    distance = distance,
                    onClick = { }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    BicycleRentalAppTheme {
        //HomeScreen()
    }
}