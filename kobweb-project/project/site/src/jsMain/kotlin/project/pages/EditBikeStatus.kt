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
import com.varabyte.kobweb.core.PageContext
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import project.components.buttons.AppButton
import project.components.cards.AppCard
import project.components.ContentBox
import project.components.GrayLabel
import project.components.sidebar.SideBar
import project.components.TextTitle
import project.components.buttons.SegmentedButtons
import project.data.*

@Page("/edit-bike-status/{id}")
@Composable
fun EditBikeStatus(ctx: PageContext) {
    val id = ctx.route.params.getValue("id")
    val bike = remember { Storage.getAllBikes().find { it.id == id.toInt() } }

    var status by remember { mutableStateOf(bike?.status ?: "Available") }
    var resultMessage by remember { mutableStateOf<String?>(null) }
    var messageColor by remember { mutableStateOf(Color.green) }

    Row(modifier = Modifier.fillMaxSize()) {
        SideBar()

        ContentBox({
            AppCard {
                TextTitle("Update Bike Status")

                // id
                GrayLabel(text = "Editing Bike ID: #$id")

                // bike status
                SegmentedButtons(
                    text = "Select New Status:",
                    options = listOf("Available", "Rented", "Maintenance"),
                    selectedOption = status,
                    onOptionSelected = { status = it }
                )

                // save button
                AppButton(
                    onButtonClick = {
                        val res = Storage.editBikeStatus(id.toInt(), status)
                        when (res) {
                            NewBikeResult.Success -> {
                                resultMessage = "Status updated to $status"
                                messageColor = Color.green

                                window.setTimeout({
                                    window.location.href = "/bikes"
                                }, 2000)
                            }
                            else -> {
                                resultMessage = "Error updating status"
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