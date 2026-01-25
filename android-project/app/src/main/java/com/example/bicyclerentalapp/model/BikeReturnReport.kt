package com.example.bicyclerentalapp.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class BikeReturnReport(
    @PrimaryKey val idBikeReturn: Int,
    val rentalId: Int,          // veza sa Rental
    val returnedAt: Long,
//    val photos: List<String>,
    val hasIssue: Boolean,
    val issueDescription: String?
)
