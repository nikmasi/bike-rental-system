package project.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import project.components.buttons.AppButton
import project.components.cards.AppCard
import project.components.ContentBox
import project.components.sidebar.SideBar
import project.components.TextTitle
import project.data.ChangePasswordResult
import project.data.Storage
import project.styles.inputStyle

//./gradlew kobwebStart -PkobwebEnv=DEV -PkobwebRunLayout=FULLSTACK

@Page("/change-password")
@Composable
fun ChangePassword() {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    var resultMessage by remember { mutableStateOf<String?>(null) }
    var messageColor by remember { mutableStateOf(Color.green) }

    Row(modifier = Modifier.fillMaxSize()) {
        SideBar()

        ContentBox({
            AppCard {
                TextTitle("Change Password")

                // Old Password
                Input(
                    type = InputType.Password,
                    attrs = inputStyle().toAttrs {
                        placeholder("Old Password")
                        onInput { oldPassword = it.value }
                    }
                )

                // New Password
                Input(
                    type = InputType.Password,
                    attrs = inputStyle().toAttrs {
                        placeholder("New Password")
                        onInput { newPassword = it.value }
                    }
                )

                // save button
                AppButton(
                    onButtonClick = {
                        val res = Storage.changePasswordAdmin(newPassword,oldPassword)
                        when (res) {
                            ChangePasswordResult.Success -> {
                                resultMessage = "New password updated"
                                messageColor = Color.green

                                window.setTimeout({
                                    window.location.href = "/home"
                                }, 2000)
                            }
                            else -> {
                                resultMessage = "Error"
                                messageColor = Color.red
                            }
                        }
                    },
                    text="Save Changes"
                )

                // message
                resultMessage?.let {
                    Span(attrs = Modifier.color(messageColor).fontSize(14.px).toAttrs()) {
                        Text(it)
                    }
                }
            }
        })
    }
}