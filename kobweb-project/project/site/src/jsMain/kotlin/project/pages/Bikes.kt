package project.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import project.components.buttons.ActionButton
import project.components.ContentBox
import project.components.table.HeaderTable
import project.components.cards.GlassCard
import project.components.NavLink
import project.components.table.RowTable
import project.components.sidebar.SideBar
import project.components.table.TableColumn
import project.components.TextTitle
import project.data.*

@Page
@Composable
fun Bikes() {
    var bikes by remember { mutableStateOf(Storage.getAllBikes()) }
    val bikeColumns = listOf(
        TableColumn("ID", 60.px),
        TableColumn("Photo", 60.px),
        TableColumn("Type & Status", 140.px),
        TableColumn("Location", 140.px),
        TableColumn("Actions", 140.px)
    )

    Row(modifier = Modifier.fillMaxSize()) {
        SideBar()

        ContentBox({
            GlassCard {
                TextTitle("All Bikes")

                // header table
                HeaderTable(bikeColumns)

                bikes.forEach { bike ->
                    RowTable {
                        // id
                        Span(attrs = Modifier.width(50.px).color(Color.white).toAttrs()) {
                            Text("#${bike.id}")
                        }

                        // image
                        Img(
                            src = bike.photo,
                            attrs = Modifier.size(100.px).borderRadius(10.px).styleModifier {
                                property("object-fit", "cover")
                            }.toAttrs()
                        )

                        // type and status
                        Column(modifier = Modifier.width(150.px)) {
                            Span(attrs = Modifier.color(Color.white).toAttrs()) {
                                Text(bike.type)
                            }
                            Span(attrs = Modifier.color(if (bike.status == "Available") Color.green else Color.orange).fontSize(12.px).toAttrs()) {
                                Text(bike.status)
                            }
                        }

                        Column(modifier = Modifier.width(140.px)) {
                            Span(
                                attrs = Modifier.color(Color.gray).fontSize(11.px).toAttrs()
                            ) {
                                Text(bike.location)
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // status link
                            NavLink("/edit-bike-status/${bike.id}", "Status", Modifier.fontSize(12.px).margin(right = 10.px))

                            // edit button
                            ActionButton("fa-pen", rgb(59, 130, 246)) {
                                window.location.href = "/edit-bike/${bike.id}"
                            }

                            // delete button
                            ActionButton("fa-trash", rgb(239, 68, 68)) {
                                if (window.confirm("Delete bike #${bike.id}?")) {
                                    Storage.deleteBike(bike.id)
                                    bikes = Storage.getAllBikes()
                                }
                            }
                        }
                    }
                }
            }
        })
    }
}