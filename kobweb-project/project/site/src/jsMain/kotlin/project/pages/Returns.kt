package project.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.dom.Img
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
import project.data.*

@Page
@Composable
fun Returns() {
    var returns by remember { mutableStateOf(Storage.getAllReturns()) }
    val returnsColumns = listOf(
        TableColumn("ID", 60.px),
        TableColumn("Return Time", 180.px),
        TableColumn("Photos", 120.px),
        TableColumn("Condition", 120.px),
        TableColumn("Details", 150.px),
        TableColumn("Report", 50.px)
    )

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        SideBar()

        ContentBox({
            GlassCard {
                TextTitle("Returns & Inspections")

                // header table
                HeaderTable(returnsColumns)

                returns.forEach { ret ->
                    RowTable{
                        // id
                        Span(attrs = Modifier.width(60.px).color(Color.white).fontWeight(FontWeight.Bold).toAttrs()) {
                            Text("#${ret.id}")
                        }

                        // time
                        Column(modifier = Modifier.width(180.px)) {
                            Span(attrs = Modifier.color(Color.white).fontSize(14.px).toAttrs()) {
                                Text(ret.returnedAt.toString())
                            }
                        }

                        // photo
                        Row(modifier = Modifier.width(120.px), horizontalArrangement = Arrangement.Start) {
                            ret.photos.forEach { photo ->
                                Img(
                                    src = photo,
                                    attrs = Modifier
                                        .size(50.px)
                                        .borderRadius(8.px)
                                        .margin(right = 5.px)
                                        .styleModifier { property("object-fit", "cover") }
                                        .toAttrs()
                                )
                            }
                        }

                        // status
                        Column(modifier = Modifier.width(120.px)) {
                            val hasIssue = ret.hasIssue
                            Span(attrs = Modifier
                                .padding(leftRight = 10.px, topBottom = 4.px)
                                .borderRadius(6.px)
                                .backgroundColor(if (hasIssue) rgba(239, 68, 68, 0.2) else rgba(34, 197, 94, 0.2))
                                .color(if (hasIssue) rgb(239, 68, 68) else rgb(34, 197, 94))
                                .fontSize(12.px)
                                .fontWeight(FontWeight.Bold)
                                .toAttrs()
                            ) {
                                Text(if (hasIssue) "ISSUE" else "RETURNED OK")
                            }
                        }

                        // issueDescription
                        Span(attrs = Modifier
                            .width(150.px)
                            .color(rgba(255,255,255,0.7))
                            .fontSize(13.px)
                            .toAttrs()
                        ) {
                            Text(ret.issueDescription ?: "No issues reported")
                        }

                        // action button
                        ActionButton("fa-bug", if (ret.hasIssue) rgb(239, 68, 68) else Color.gray) {
                        }
                    }
                }
            }
        })
    }
}