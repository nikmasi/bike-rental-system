package com.example.bicyclerentalapp.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.bicyclerentalapp.ui.theme.BicycleRentalAppTheme
import com.example.bicyclerentalapp.ui.components.AppButton
import com.example.bicyclerentalapp.ui.components.AuthTextField
import com.example.bicyclerentalapp.ui.components.Title
import com.example.bicyclerentalapp.ui.theme.BackgroundBlack

@Composable
fun LoginScreen(
    onLogIn: () ->Unit,
    viewModel: AuthViewModel,
    onLoginClick: (username: String, password: String) -> Unit = { _, _ -> }
) {
    val logInResult by viewModel.logInResult.collectAsState()

    LaunchedEffect(logInResult) {
        logInResult?.let {
            onLogIn()
        }
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlack)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Title(text = "Login")

        // Username
        AuthTextField(
            value = username,
            onValueChange = { username = it },
            placeholder =  "Username"
        )

        // Password
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        // Login Button
        AppButton(
            onClick = {
                viewModel.processIntent(
                    UserIntent.Login(
                        username = username,
                        password = password
                    )
                )
                onLoginClick(username, password) },
            text = "Login",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BicycleRentalAppTheme {
       // LoginScreen()
    }
}
