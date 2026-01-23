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
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import project.components.buttons.AppButton
import project.components.ContentBox
import project.components.GrayLabel
import project.components.inputGroups.InputGroup
import project.components.cards.GlassCard
import project.components.sidebar.SideBar
import project.components.TextTitle
import project.components.buttons.SegmentedButtons
import project.data.*

@Page("/edit-bike/{id}")
@Composable
fun EditBike(ctx: PageContext) {
    val id = ctx.route.params.getValue("id")
    val bike = remember { Storage.getAllBikes().find { it.id == id.toInt() } }

    var tip by remember { mutableStateOf(bike?.type ?: "Classic") }
    var location by remember { mutableStateOf(bike?.location ?: "") }
    var price by remember { mutableStateOf(bike?.price?.toString() ?: "") }
    var photo by remember { mutableStateOf(bike?.photo ?: "") }

    var resultMessage by remember { mutableStateOf<String?>(null) }
    var messageColor by remember { mutableStateOf(Color.green) }

    Row(modifier = Modifier.fillMaxSize()) {
        SideBar()

        ContentBox({
            GlassCard {
                TextTitle("Edit Bike Details")

                // id
                GrayLabel(text = "Editing Bike: #$id")

                // bike type
                SegmentedButtons(
                    text = "Bike Type:",
                    options = listOf("Classic", "Electric"),
                    selectedOption = tip,
                    onOptionSelected = { tip = it }
                )

                // inputs
                InputGroup("Location", location) { location = it }
                InputGroup("Price per hour", price) { price = it }
                InputGroup("Photo URL", photo) { photo = it }

                // save button
                AppButton(
                    onButtonClick = {
                        val priceDouble = price.toDoubleOrNull()
                        if (priceDouble == null) {
                            resultMessage = "Invalid price format"
                            messageColor = Color.red
                            return@AppButton
                        }

                        val res = Storage.editBike(id.toInt(), location, priceDouble, photo, tip)
                        when (res) {
                            NewBikeResult.Success -> {
                                resultMessage = "Bike updated successfully! Redirecting..."
                                messageColor = Color.green
                                window.setTimeout({ window.location.href = "/bikes" }, 2000)
                            }
                            NewBikeResult.EmptyLocation -> {
                                resultMessage = "Location cannot be empty"
                                messageColor = Color.red
                            }
                            NewBikeResult.MissingPhoto -> {
                                resultMessage = "Photo URL is missing"
                                messageColor = Color.red
                            }
                            else -> {
                                resultMessage = "Error updating bike"
                                messageColor = Color.red
                            }
                        }
                    },
                    text = "Save Changes"
                )

                resultMessage?.let {
                    Span(attrs = Modifier.color(messageColor).fontSize(14.px).margin(top = 10.px).toAttrs()) {
                        Text(it)
                    }
                }
            }
        })
    }
}