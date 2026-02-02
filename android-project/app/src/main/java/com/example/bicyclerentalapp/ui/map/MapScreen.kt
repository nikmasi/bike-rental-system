package com.example.bicyclerentalapp.ui.map

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.ui.components.AppButton
import com.example.bicyclerentalapp.ui.theme.BackgroundBlack
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun MapScreen(onRent: () -> Unit) {
    var mapReference by remember { mutableStateOf<MapView?>(null) }
    var searchText by remember { mutableStateOf("") }
    var parkingChecked by remember { mutableStateOf(false) }

    var selectedStation by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlack)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    if (searchText.isEmpty()) Text("Location", color = Color.Gray)
                    innerTextField()
                }
            )
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
        }

        Box(
            modifier = Modifier
                .weight(1f).fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        ) {
            // map
            OpenStreetMap(
                modifier = Modifier.fillMaxSize(),
                onMapLoad = { map ->
                    mapReference = map
                    setupMap(map) { stationName ->
                        // click marker
                        selectedStation = stationName
                        map.controller.animateTo(map.mapCenter, 18.0, 1000L) // Zoom-in
                    }
                }
            )

            // current location
            FloatingActionButton(
                onClick = {
                    val bgd = GeoPoint(44.7866, 20.4489)
                    mapReference?.controller?.animateTo(bgd)
                },
                containerColor = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(45.dp)
            ) {
                Icon(Icons.Default.MyLocation, contentDescription = "Location", tint = Color.Black)
            }

            // parking
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = parkingChecked,
                    onCheckedChange = { parkingChecked = it }
                )
                Text("Parking", color = Color.Black, modifier = Modifier.padding(end = 8.dp))
            }
        }
        if (selectedStation != null) {
            Spacer(modifier = Modifier.size(16.dp))

            BikeDetailCard(
                modifier = Modifier.weight(0.5f),
                stationName = selectedStation!!,
                onClose = { selectedStation = null },
                onRent = onRent
            )
        }
    }
}

@Composable
fun BikeDetailCard(
    modifier: Modifier = Modifier,
    stationName: String,
    onClose: () -> Unit,
    onRent: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
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
                    onClick = onClose,
                    modifier = Modifier.size(20.dp).background(Color.DarkGray, RoundedCornerShape(50))
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
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
                            text = stationName,
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

            // Rent button
            AppButton(onClick = onRent, text= "Rent now", height = 40.dp)
        }
    }
}

// init map and markers
fun setupMap(map: MapView, onMarkerClick: (String) -> Unit) {
    val startPoint = GeoPoint(45.2396, 19.8227)
    map.controller.setCenter(startPoint)
    map.controller.setZoom(15.0)

    val marker = org.osmdroid.views.overlay.Marker(map)
    marker.position = startPoint
    marker.title = "Central Parking"
    marker.setOnMarkerClickListener { m, _ ->
        onMarkerClick(m.title)
        true
    }
    map.overlays.add(marker)
    map.invalidate()
}