package com.example.bicyclerentalapp.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
//@Immutable
//data class BikeReturnReport(
//    @PrimaryKey val idBikeReturn: Int,
//    val rentalId: Int,
//    val returnedAt: Long,
////    val photos: List<String>,
//    val hasIssue: Boolean,
//    val issueDescription: String?
//)

@Entity
data class BikeReturnReport(
    @PrimaryKey(autoGenerate = true) val idBikeReturn: Int = 0,
    val rentalId: Int,
    val userId: Int,
    val returnedAt: Long = System.currentTimeMillis(),
    val photosPaths: String, // "path1|path2|path3"
    val hasIssue: Boolean,
    val issueDescription: String?
)