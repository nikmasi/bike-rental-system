package com.example.bicyclerentalapp.ui.profile

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.auth.AuthViewModel
import com.example.bicyclerentalapp.ui.auth.UserIntent
import com.example.bicyclerentalapp.ui.components.AppButton

@Composable
fun ProfileScreen(
    viewModel: AuthViewModel,
    onLogout: () -> Unit,
    onChangePassword: () -> Unit,
    onRentalHistory: () -> Unit
) {
    val user by viewModel.logInResult.collectAsState()

    // if user not here
    LaunchedEffect(Unit) {
        if (user == null) {
            viewModel.processIntent(UserIntent.LoadCurrentUser)
        }
    }

    ScreenWrapper(title = "My profile") {
        // icon person
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(bottom = 24.dp).size(80.dp)
        )

        user?.let { currentUser ->
            ProfileInfoCard(
                label = "Firstname & Lastname",
                value = "${currentUser.firstname} ${currentUser.lastname}"
            )
            ProfileInfoCard(label = "Username", value = currentUser.username)
            ProfileInfoCard(label = "Email", value = currentUser.email)
            ProfileInfoCard(label = "Phone", value = currentUser.phone)
        }

        Spacer(modifier = Modifier.weight(1f))

        // logout button
        AppButton(onLogout, "Logout")
        // change password button
        AppButton(onChangePassword, "Change password")
        // my rentals button
        AppButton(onRentalHistory, "My rentals")
    }
}