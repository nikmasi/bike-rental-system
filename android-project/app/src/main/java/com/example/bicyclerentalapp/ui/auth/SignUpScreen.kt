package com.example.bicyclerentalapp.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
fun SignUpScreen(
    onLoginClick: (username: String, password: String) -> Unit = { _, _ -> },
    viewModel: AuthViewModel,
    onSignUp: () -> Unit
) {
    val signUpResult by viewModel.signUpResult.collectAsState()
    val state by viewModel.uiState.collectAsState()

    val error by viewModel.errorState.collectAsState()

    val isEmailError = error == AuthError.InvalidEmail
    val isPasswordError = error == AuthError.WeakPassword
    val isEmptyError = error == AuthError.EmptyFields

    LaunchedEffect(signUpResult) {
        signUpResult?.let {
            onSignUp()
        }
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(error) {
        error?.let {
            val message = when (it) {
                AuthError.EmptyFields ->
                    "All fields are required"
                AuthError.InvalidEmail ->
                    "Invalid email address"
                AuthError.WeakPassword ->
                    "Password must be at least 8 characters, contain a number and uppercase letter"
                AuthError.InvalidCredentials ->
                    "Wrong username or password"
            }
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BackgroundBlack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Title(text = "SignUp")

            AuthTextField(
                value = firstname,
                onValueChange = { firstname = it },
                placeholder =  "Firstname"
            )

            AuthTextField(
                value = lastname,
                onValueChange = { lastname = it },
                placeholder =  "Lastname"
            )

            AuthTextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder =  "Phone"
            )

            AuthTextField(
                value = email,
                onValueChange = { email = it },
                placeholder =  "Email",
                isError = isEmailError || isEmptyError
            )

            AuthTextField(
                value = username,
                onValueChange = { username = it },
                placeholder =  "Username"
            )

            AuthTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                isError = isPasswordError || isEmptyError,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // SignUp Button
            AppButton(
                onClick = {
                    viewModel.processIntent(
                        UserIntent.SignUp(
                            username = username,
                            password = password,
                            firstname = firstname,
                            lastname = lastname,
                            phone = phone,
                            email = email
                        )
                    )
                },
                text = "SignUp",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BicycleRentalAppTheme {
        //SignUpScreen()
    }
}
