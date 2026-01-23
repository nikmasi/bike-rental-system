package project.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.dom.Input
import project.components.buttons.AppButton
import project.components.layouts.AuthLayout
import project.components.ErrorMessage
import project.components.TextTitle
import project.data.*
import project.styles.inputStyle

@Page
@Composable
fun Login() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf<String?>(null) }

    val handleLogin = {
        when (Storage.login(username, password)) {
            LoginResult.Success -> {
                window.localStorage.setItem("loggedIn", "true")
                window.location.replace("/home")
            }
            LoginResult.UsernameEmpty -> loginError = "Username is required"
            LoginResult.PasswordEmpty -> loginError = "Password is required"
            LoginResult.UserNotFound -> loginError = "User not found"
            LoginResult.WrongPassword -> loginError = "Incorrect password"
        }
    }


    AuthLayout {
        TextTitle("Login")

        // Username
        Input(
            type = InputType.Text,
            attrs = inputStyle().toAttrs {
                placeholder("Username")
                onInput { username = it.value; loginError = null }
            }
        )

        // Password
        Input(
            type = InputType.Password,
            attrs = inputStyle().toAttrs {
                placeholder("Password")
                onInput { password = it.value; loginError = null }
                onKeyDown { if (it.key == "Enter") handleLogin() }
            }
        )

        // login button
        AppButton(
            onButtonClick = handleLogin,
            text = "Login"
        )

        // error message
        loginError?.let { ErrorMessage(it) }
    }
}