package com.example.bicyclerentalapp.ui.map

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.model.Station
import com.example.bicyclerentalapp.ui.map.components.BikeDetailCard
import com.example.bicyclerentalapp.ui.map.components.MapFAP
import com.example.bicyclerentalapp.ui.map.components.MapParking
import com.example.bicyclerentalapp.ui.map.components.MapSearchBar
import com.example.bicyclerentalapp.ui.rental.RentalViewModel
import com.example.bicyclerentalapp.ui.rental.components.DefaultLocationClient
import com.example.bicyclerentalapp.ui.rental.components.LocationService
import com.example.bicyclerentalapp.ui.theme.BackgroundBlack
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.Locale

@Composable
fun MapScreen(
    onRent: (Station, Boolean) -> Unit,
    viewModel: MapViewModel,
    rentalViewModel: RentalViewModel
) {
    val stations by viewModel.uiState.collectAsState()

    var mapReference by remember { mutableStateOf<MapView?>(null) }
    var searchText by remember { mutableStateOf("") }
    var parkingChecked by remember { mutableStateOf(false) }

    var selectedStation by remember { mutableStateOf<Station?>(null) }
    var isElectroSelected by remember { mutableStateOf(false) }

    // marker user
    val userLocationMarker = remember { mutableStateOf<Marker?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val locationClient = remember {
        DefaultLocationClient(context,
            LocationServices.getFusedLocationProviderClient(context)
        )
    }

    LaunchedEffect(stations, mapReference) {
        val map = mapReference ?: return@LaunchedEffect

        val offset = 0.0005

        if (stations.isNotEmpty()) {
            map.overlays.removeAll { it is Marker && it.title != "Your current location" }

            stations.forEach { station ->
                if (station.electroBikesCount >0){
                    val marker = Marker(map)
                    marker.position = GeoPoint(station.latitude, station.longitude + offset)
                    marker.title = station.name

                    val iconRes = android.R.drawable.ic_menu_directions

                    marker.icon = context.getDrawable(iconRes)
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    marker.setOnMarkerClickListener { m, _ ->
                        selectedStation = station
                        isElectroSelected = true
                        map.controller.animateTo(m.position, 18.0, 500L)
                        true
                    }
                    map.overlays.add(marker)
                }
                if (station.classicBikesCount>0){
                    val marker = Marker(map)
                    marker.position = GeoPoint(station.latitude, station.longitude - offset)
                    marker.title = station.name

                    val iconRes = android.R.drawable.ic_menu_mylocation

                    marker.icon = context.getDrawable(iconRes)
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    marker.setOnMarkerClickListener { m, _ ->
                        selectedStation = station
                        isElectroSelected = false
                        map.controller.animateTo(m.position, 18.0, 500L)
                        true
                    }
                    map.overlays.add(marker)
                }

            }
            map.invalidate()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(BackgroundBlack).padding(16.dp)
    ) {

        // search bar
        MapSearchBar(
            searchText = searchText,
            onSearchTextChange = { searchText = it },
            onSearchExecute = {
                selectedStation = null
                performSearch(searchText, context, mapReference, scope)
            },
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Box(
            modifier = Modifier.weight(1f).fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        ) {
            // map
            OpenStreetMap(
                modifier = Modifier.fillMaxSize(),
                onMapLoad = { map ->
                    mapReference = map
                    setupMap(map) { stationName ->
                        // click marker
                        //selectedStation = stationName
                        map.controller.animateTo(map.mapCenter, 18.0, 1000L) // Zoom-in
                    }
                }
            )

            // current location
            MapFAP(
              modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
                onClick = {
                    // start foreground service
                    Intent(context, LocationService::class.java).apply {
                        action = LocationService.ACTION_START
                        context.startService(this)
                    }

                    // current location and move on map
                    scope.launch {
                        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            location?.let {
                                val geoPoint = GeoPoint(it.latitude, it.longitude)
                                mapReference?.controller?.animateTo(geoPoint, 18.0, 500L)
                                updateUserMarker(mapReference, geoPoint, userLocationMarker, context)
                            }
                        }

                        locationClient.getLocationUpdates(1000L).collect { location ->
                            val geoPoint = GeoPoint(location.latitude, location.longitude)
                            updateUserMarker(mapReference, geoPoint, userLocationMarker, context)
                        }
                    }
                }
            )

            // parking
            MapParking(
                modifier = Modifier.align(Alignment.BottomStart),
                parkingChecked = parkingChecked,
                onCheckedChange = { parkingChecked = it }
            )

        }
        if (selectedStation != null) {
            Spacer(modifier = Modifier.size(16.dp))

            BikeDetailCard(
                modifier = Modifier.weight(0.7f),
                station = selectedStation!!,
                typeLabel = if (isElectroSelected) "ELECTRO" else "CLASSIC",
                onClose = { selectedStation = null },
                onRent = { station, isElectro ->
                    rentalViewModel.setRentalData(station)
                    onRent(station, isElectro)
                }
            )
        }
    }
}

// init map and markers
private fun setupMap(map: MapView, onMarkerClick: (String) -> Unit) {
    val startPoint = GeoPoint(45.2396, 19.8227)
    map.controller.setCenter(startPoint)
    map.controller.setZoom(15.0)

    val marker = Marker(map)
    marker.position = startPoint
    marker.title = "Central Parking"
    marker.setOnMarkerClickListener { m, _ ->
        onMarkerClick(m.title)
        true
    }
    map.overlays.add(marker)
    map.invalidate()
}

private fun updateUserMarker(
    map: MapView?,
    point: GeoPoint,
    markerState: MutableState<Marker?>,
    context: Context
) {
    map?.let {
        if (markerState.value == null) {
            val marker = Marker(it)
            marker.title = "Your current location"
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            markerState.value = marker
            it.overlays.add(marker)
        }
        markerState.value?.position = point
        it.invalidate()
    }
}

private fun performSearch(
    query: String,
    context: Context,
    map: MapView?,
    scope: kotlinx.coroutines.CoroutineScope
) {
    if (query.isBlank() || map == null) return

    scope.launch(kotlinx.coroutines.Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            // get first founded address
            val addresses = geocoder.getFromLocationName(query, 1)

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val targetPoint = GeoPoint(address.latitude, address.longitude)

                launch(kotlinx.coroutines.Dispatchers.Main) {
                    map.controller.animateTo(targetPoint, 17.0, 1000L)

                    val searchMarker = Marker(map)
                    searchMarker.position = targetPoint
                    searchMarker.title = query
                    map.overlays.add(searchMarker)
                    map.invalidate()
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("SearchError", "Error: ${e.message}")
        }
    }
}