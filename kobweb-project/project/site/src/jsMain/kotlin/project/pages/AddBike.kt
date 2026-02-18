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
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import project.components.buttons.AppButton
import project.components.ContentBox
import project.components.GrayLabel
import project.components.cards.GlassCard
import project.components.sidebar.SideBar
import project.components.TextTitle
import project.components.buttons.SegmentedButtons
import project.components.inputGroups.AddInputGroup
import project.data.models.Bike
import project.data.*

@Page
@Composable
fun AddBike() {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        val id = remember { Storage.newIdBike() }
        var tip by remember { mutableStateOf("Classic") }
        var location by remember { mutableStateOf("") }
        var price by remember { mutableStateOf("") }
        var photo by remember { mutableStateOf("") }

        var resultMessage by remember { mutableStateOf<String?>(null) }
        var messageColor by remember { mutableStateOf(Color.green) }

        // sidebar
        SideBar()

        // content
        ContentBox({
            GlassCard {
                TextTitle("Add New Bike")

                // id
                GrayLabel("Generated ID: #$id")

                // bike type
                SegmentedButtons(
                    text = "Bike Type:",
                    options = listOf("Classic", "Electric"),
                    selectedOption = tip,
                    onOptionSelected = { tip = it }
                )

                // input field
                AddInputGroup("Starting Location", location, "e.g. Block 45") { location = it }
                AddInputGroup("Price per hour ($)", price, "e.g. 100.00") { price = it }
                AddInputGroup("Photo URL", photo, "e.g. /bike1.jpg") { photo = it }

                // add button
                AppButton(
                    onButtonClick = {
                        val priceDouble = price.toDoubleOrNull()
                        if (priceDouble == null) {
                            resultMessage = "Please enter a valid price"
                            messageColor = Color.red
                            return@AppButton
                        }

                        val res = Storage.registerBike(
                            Bike(id, tip, location, priceDouble, photo, "Available")
                        )

                        when (res) {
                            NewBikeResult.Success -> {
                                resultMessage = "Bike successfully added! Redirecting..."
                                messageColor = Color.green
                                window.setTimeout({ window.location.href = "/bikes" }, 3000)
                            }
                            NewBikeResult.EmptyLocation -> {
                                resultMessage = "Location is required"
                                messageColor = Color.red
                            }
                            NewBikeResult.InvalidPrice -> {
                                resultMessage = "Invalid price amount"
                                messageColor = Color.red
                            }
                            NewBikeResult.MissingPhoto -> {
                                resultMessage = "Photo path is missing"
                                messageColor = Color.red
                            }
                        }
                    },
                    text = "Add Bike"
                )

                // result
                resultMessage?.let {
                    Span(attrs = Modifier.color(messageColor).fontSize(14.px).margin(top = 10.px).toAttrs()) {
                        Text(it)
                    }
                    Img(
                        src = "qr_code.svg",
                        attrs = Modifier.size(150.px).margin(top = 10.px).toAttrs()
                    )
                }
            }
        })
    }
}