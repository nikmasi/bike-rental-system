package com.example.bicyclerentalapp.ui.profile

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.components.AppButton

@Composable
fun ChangePasswordScreen(
    onOK: () -> Unit
) {
    ScreenWrapper(title = "Change Password") {
        ProfileInfoCard(label = "Old Password", value = "")
        ProfileInfoCard(label = "New Password", value = "")
        ProfileInfoCard(label = "Confirm New Password", value = "")

        Spacer(modifier = Modifier.weight(1f))

        // save button
        AppButton(onOK, "Save")
    }
}