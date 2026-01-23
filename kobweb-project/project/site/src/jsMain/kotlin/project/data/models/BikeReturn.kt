package project.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BikeReturn(
    val id: Int,
    val rentalId: Int,
    val returnedAt: Long,
    val photos: List<String>,
    val hasIssue: Boolean,
    val issueDescription: String?
)