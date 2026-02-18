package project.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.margin
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.dom.I
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import project.components.ContentBox
import project.components.cards.GlassCard
import project.components.sidebar.SideBar
import project.components.TextTitle
import project.components.buttons.ActionButton
import project.components.table.HeaderTable
import project.components.table.RowTable
import project.components.table.TableColumn
import project.data.Storage

@Page
@Composable
fun Rentals() {
    var rentals by remember { mutableStateOf(Storage.getAllRentals()) }
    val rentalsColumns = listOf(
        TableColumn("ID", 50.px),
        TableColumn("User", 120.px),
        TableColumn("Bike", 100.px),
        TableColumn("Time Period", 140.px),
        TableColumn("Price", 140.px),
        TableColumn("Action", 50.px),
    )

    Row(modifier = Modifier.fillMaxSize()) {
        SideBar()

        ContentBox({
            GlassCard {
                TextTitle("Active Rentals")

                HeaderTable(rentalsColumns)

                rentals.forEach { rental ->
                    RowTable {
                        // id
                        Span(attrs = Modifier.width(50.px).color(Color.white).fontWeight(FontWeight.Bold).toAttrs()) {
                            Text("#${rental.id}")
                        }

                        // user
                        Column(modifier = Modifier.width(120.px)) {
                            Span(attrs = Modifier.color(Color.white).toAttrs()) {
                                Text(rental.user.name)
                            }
                            Span(attrs = Modifier.color(Color.gray).fontSize(12.px).toAttrs()) {
                                Text("Customer")
                            }
                        }

                        // bike
                        Span(attrs = Modifier.width(100.px).color(rgb(99, 102, 241)).toAttrs()) {
                            Text("Bike #${rental.bike.id}")
                        }

                        // time
                        Column(modifier = Modifier.width(180.px)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                I(attrs = { classes("fa", "fa-calendar-alt"); style { color(Color.gray); margin(right = 5.px); fontSize(12.px) } })
                                Span(attrs = Modifier.color(Color.white).fontSize(13.px).toAttrs()) {
                                    Text(rental.start)
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.margin(top = 4.px)) {
                                I(attrs = { classes("fa", "fa-flag-checkered"); style { color(Color.gray); margin(right = 5.px); fontSize(12.px) } })
                                Span(attrs = Modifier.color(rgba(255,255,255,0.7)).fontSize(13.px).toAttrs()) {
                                    Text(rental.end)
                                }
                            }
                        }

                        Column(modifier = Modifier.width(140.px)) {
                            Span(
                                attrs = Modifier.color(Color.gray).fontSize(11.px).toAttrs()
                            ) {
                                Text(rental.price.toString())
                            }
                        }

                        // delete button
                        ActionButton("fa-trash", rgb(239, 68, 68)) {
                            if (window.confirm("Are you sure you want to cancel this rental?")) {

                            }
                        }
                    }
                }
            }
        })
    }
}