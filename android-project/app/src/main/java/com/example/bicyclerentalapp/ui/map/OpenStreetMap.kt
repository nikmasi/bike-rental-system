package com.example.bicyclerentalapp.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView

@Composable
fun OpenStreetMap(
    modifier: Modifier,
    onMapLoad: (MapView) -> Unit
){
    val context = LocalContext.current

    val mapView = remember { MapView(context) }

    AndroidView(
        factory = {
            mapView.apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(15.0)
                onMapLoad(this)
            }
        },
        modifier = modifier,
        update = {}
    )
}

