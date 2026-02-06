package com.example.bicyclerentalapp.ui.rental

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.ui.components.AppButton
import com.example.bicyclerentalapp.ui.components.wrapper.CardsWrapper
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.rental.components.BottomInfoCard
import com.example.bicyclerentalapp.ui.rental.components.CameraPreview
import com.example.bicyclerentalapp.ui.rental.components.takePhoto

@Composable
fun FinishRentalScreen(
    controller: LifecycleCameraController,
    //onFinishClick: () -> Unit = {},
    onFinishClick: (Bitmap) -> Unit = {},
    context: Context,
    rentalViewModel: RentalViewModel
) {

    val station by rentalViewModel.selectedStation.collectAsState()
    val bike by rentalViewModel.selectedBike.collectAsState()

    ScreenWrapper(title = "Finish Rental") {
        CardsWrapper(onClick = {}, buttonText = "Finish", buttonClick = {

        }, buttonActive = true,
            topContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    CameraPreview(
                        controller = controller
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                AppButton(onClick = {
                    takePhoto(
                        controller = controller,
                        context = context,
                        onPhotoTaken = { bitmap ->
                            onFinishClick(bitmap)
                        }
                    )
                },text="Take a photo")
            },
            bottomContent = {
                BottomInfoCard(bike?.isElectro == true, station)
            }
        )
    }
}