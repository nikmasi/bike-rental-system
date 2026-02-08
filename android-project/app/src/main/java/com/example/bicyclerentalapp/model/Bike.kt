package com.example.bicyclerentalapp.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class Bike(
    @PrimaryKey val idBike: Int,
    val stationId: Int,
    val isElectro: Boolean,
    val batteryLevel: Int? = null, // null if classic bike
    val isReserved: Boolean = false,
    val modelName: String = "Standard Model"
)