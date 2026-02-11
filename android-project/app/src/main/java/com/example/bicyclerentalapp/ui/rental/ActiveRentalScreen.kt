package com.example.bicyclerentalapp.ui.rental

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.ui.components.wrapper.CardsWrapper
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.map.MapViewModel
import com.example.bicyclerentalapp.ui.map.OpenStreetMap
import com.example.bicyclerentalapp.ui.rental.components.BottomInfoCard
import com.example.bicyclerentalapp.ui.rental.components.DefaultLocationClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun ActiveRentalScreen(
    onFinishClick: () -> Unit = {},
    rentalViewModel: RentalViewModel,
    mapViewModel: MapViewModel
) {
    val bike by rentalViewModel.selectedBike.collectAsState()
    val seconds by rentalViewModel.secondsActive.collectAsState()
    val station by rentalViewModel.selectedStation.collectAsState()

    val parkingZones by mapViewModel.uiStateParking.collectAsState()

    val context = LocalContext.current
    var mapReference by remember { mutableStateOf<MapView?>(null) }
    val userMarker = remember { mutableStateOf<Marker?>(null) }

    val locationClient = remember {
        DefaultLocationClient(context, LocationServices.getFusedLocationProviderClient(context))
    }

    LaunchedEffect(mapReference) {
        val map = mapReference ?: return@LaunchedEffect

        locationClient.getLocationUpdates(2000L).collect { location ->
            val geoPoint = GeoPoint(location.latitude, location.longitude)
            map.controller.animateTo(geoPoint)

            if (map.zoomLevelDouble < 10.0) {
                map.controller.setZoom(18.0)
            }

            if (userMarker.value == null) {
                val marker = Marker(map)
                marker.icon = context.getDrawable(android.R.drawable.ic_menu_mylocation)
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                userMarker.value = marker
                map.overlays.add(marker)
            }
            userMarker.value?.position = geoPoint
            map.invalidate()
        }
    }
    ScreenWrapper(title = "Active Rental") {
        Column(modifier = Modifier.fillMaxSize()) {
            // INFO KARTICA (Donji deo ekrana)
            CardsWrapper(
                onClick = {},
                buttonText = "Finish",
                buttonClick = {
                    onFinishClick()
                    rentalViewModel.stopRental()
                },
                buttonActive = true,
                topContent = {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        OpenStreetMap(
                            modifier = Modifier.fillMaxSize(),
                            onMapLoad = { map ->
                                mapReference = map
                                map.controller.setZoom(18.0)

                            }
                        )
                    }
                    Text(
                        text = rentalViewModel.formatSecondsToMinutes(seconds),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(text = "Riding from ${station?.name}", color = Color.White)
                    Text(text = "Bike ID: #${bike?.idBike}", color = Color.White)
                },
                bottomContent = {
                    BottomInfoCard(bike?.isElectro == true, station)
                }
            )
        }
    }
}