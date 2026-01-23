package project.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import project.EmptyState
import project.components.ColumnItem
import project.components.ContentBox
import project.components.cards.GlassCard
import project.components.NavLink
import project.components.sidebar.SideBar
import project.components.TextTitle
import project.components.table.RowTable
import project.data.*

@Page
@Composable
fun Home() {
    var allBikes by remember { mutableStateOf(Storage.getAllBikes()) }
    val available = allBikes.filter { it.status == "Available" }
    val issues = allBikes.filter { it.status != "Available" }
    var problems by remember { mutableStateOf(Storage.getAllProblems()) }

    Row(modifier = Modifier.fillMaxSize()) {
        SideBar()

        ContentBox({
                GlassCard(modifier = Modifier.fillMaxWidth().margin(top = 20.px)) {
                    TextTitle("System Overview")

                    Column(modifier = Modifier.fillMaxWidth().padding(25.px)) {

                        // stats
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 30.px)
                                .styleModifier { property("border-bottom", "1px solid rgba(255,255,255,0.1)") },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ColumnItem("Total Fleet", "${allBikes.size}", rgb(59, 130, 246))
                            ColumnItem("Issues", "${issues.size}", rgb(239, 68, 68))
                            ColumnItem("Available", "${available.size}", rgb(168, 85, 247))
                        }

                        Row(modifier = Modifier.fillMaxWidth().margin(top = 25.px)) {

                            // problems
                            Column(modifier = Modifier.weight(1.2f).margin(right = 20.px)) {
                                TextTitle("Critical Alerts")
                                if (problems.isEmpty()) {
                                    EmptyState("System healthy. No pending issues.")
                                } else {
                                    problems.forEach {  bike ->
                                        RowTable {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Column(modifier = Modifier.margin(left = 12.px)) {
                                                    Span(attrs = Modifier.color(Color.white).fontSize(14.px).toAttrs()) { Text("Bike #${bike.id}") }
                                                    Span(attrs = Modifier.color(Color.gray).fontSize(11.px).toAttrs()) { bike.issueDescription?.let { Text(it) } }
                                                }
                                            }
                                            NavLink("", "Manage", Modifier.fontSize(11.px))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        })
    }
}