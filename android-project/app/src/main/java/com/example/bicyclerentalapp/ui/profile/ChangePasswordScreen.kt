package com.example.bicyclerentalapp.ui.profile

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.ui.auth.AuthViewModel
import com.example.bicyclerentalapp.ui.auth.UserIntent
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.components.AppButton
import com.example.bicyclerentalapp.ui.components.AuthTextField

@Composable
fun ChangePasswordScreen(viewModel: AuthViewModel, onOK: () -> Unit) {
    val user by viewModel.logInResult.collectAsState()

    var oldPasswordInput by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    ScreenWrapper(title = "Change Password") {
        AuthTextField(
            value = oldPasswordInput,
            onValueChange = { oldPasswordInput = it },
            placeholder = "Current Password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        AuthTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            placeholder = "New Password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        AuthTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "Confirm New Password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        if (errorMessage != null) {
            Text(errorMessage!!, color = Color.Red, modifier = Modifier.padding(vertical = 8.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        AppButton(
            onClick = {
                if (oldPasswordInput.isBlank() || newPassword.isBlank()) {
                    errorMessage = "All fields required"
                    return@AppButton
                }

                if (newPassword != confirmPassword) {
                    errorMessage = "Passwords do not match"
                    return@AppButton
                }

                user?.let { currentUser ->
                    val updatedUser = currentUser.copy(password = newPassword)
                    viewModel.processIntent(UserIntent.EditProfile(updatedUser))
                    onOK()
                }
            },
            text = "Update Password"
        )
    }
}