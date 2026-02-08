package com.example.bicyclerentalapp.ui.rental

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bicyclerentalapp.ui.components.AppButton
import com.example.bicyclerentalapp.ui.components.wrapper.LargeCardsWrapper
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.rental.components.CameraPreview
import com.example.bicyclerentalapp.ui.rental.components.takePhoto

@Composable
fun ProblemScreen(
    onFinishClick: (String) -> Unit,
    rentalViewModel: RentalViewModel,
    context: Context,
    controller: LifecycleCameraController
) {
    val photos by rentalViewModel.reportPhotos.collectAsState()
    var description by remember { mutableStateOf("") }

    ScreenWrapper(title = "Report a Problem") {
        LargeCardsWrapper(
            onClick = {},
            buttonText = "SUBMIT REPORT",
            buttonClick = { onFinishClick(description) },
            buttonActive = description.isNotBlank() || photos.isNotEmpty(),
            topContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Column {
                        Text(
                            text = "ISSUE DESCRIPTION",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            modifier = Modifier.fillMaxWidth().height(120.dp),
                            placeholder = { Text("Describe what happened...", color = Color.DarkGray) },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFF121212),
                                focusedContainerColor = Color(0xFF121212),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color.Cyan,
                                focusedIndicatorColor = Color.Cyan,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "VISUAL EVIDENCE",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(20.dp))
                        ) {
                            CameraPreview(controller = controller)

                            AppButton(
                                onClick = {
                                    takePhoto(
                                        controller = controller,
                                        context = context,
                                        onPhotoTaken = { bitmap ->
                                            val path = saveBitmapToInternalStorage(context, bitmap, "problem")
                                            path?.let { rentalViewModel.addReportPhoto(it) }
                                        }
                                    )
                                },
                                text = "Capture",
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp)
                                    .height(52.dp)
                            )
                        }
                    }

                    if (photos.isNotEmpty()) {
                        Column {
                            Text(
                                text = "ATTACHED (${photos.size})",
                                color = Color.Cyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                items(photos) { path ->
                                    AsyncImage(
                                        model = path,
                                        contentDescription = "Problem photo",
                                        modifier = Modifier
                                            .size(70.dp)
                                            .clip(RoundedCornerShape(12.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}