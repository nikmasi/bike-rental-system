package com.example.bicyclerentalapp.ui.rental.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bicyclerentalapp.ui.rental.RentalWithReport
import java.util.Date
import java.util.Locale

@SuppressLint("DefaultLocale")
@Composable
fun RentalHistoryCard(rentalWithReport: RentalWithReport, onClick: () -> Unit) {
    val rental = rentalWithReport.rental
    val report = rentalWithReport.report

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFF1E1E1E),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (rental.bikePhotoAfterPath != null) {
                    AsyncImage(
                        model = rental.bikePhotoAfterPath,
                        contentDescription = "Bike condition",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF2196F3).copy(alpha = 0.2f),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.DirectionsBike,
                            contentDescription = "bike icon",
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                    Text("#${rental.idRental}", color = Color.White, fontWeight = FontWeight.Bold)
                    Text(
                        text = "Start: ${longToTime(rental.startTime)}",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    rental.endTime?.let {
                        Text(
                            text = "End: ${longToTime(it)}",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "${rental.totalDurationMinutes ?: 0} min",
                        color = Color.Cyan, fontSize = 12.sp
                    )
                    rental.totalPrice?.let {
                        Text(
                            text = String.format("%.2f €", it),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            if (report != null && report.hasIssue) {
                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "ISSUE REPORTED",
                            color = Color(0xFFF44336),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )

                        report.issueDescription?.let {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = it, color = Color.White, fontSize = 13.sp)
                        }

                        val photos = report.photosPaths.split("|").filter { it.isNotBlank() }
                        if (photos.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(photos) { path ->
                                    AsyncImage(
                                        model = path,
                                        contentDescription = "Issue Photo",
                                        modifier = Modifier
                                            .size(70.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                        else{
                            Text(text = "No photos", color = Color.White, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

private fun longToTime(time:Long):String{
    val date = Date(time)
    val format = java.text.SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault())
    return format.format(date)
}