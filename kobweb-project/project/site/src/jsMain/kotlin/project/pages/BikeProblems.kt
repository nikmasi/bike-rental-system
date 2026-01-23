package project.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.css.textTransform
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
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
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.letterSpacing
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.dom.I
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import project.EmptyState
import project.components.ColumnItem
import project.components.ContentBox
import project.components.cards.GlassCard
import project.components.sidebar.SideBar
import project.components.TextTitle
import project.components.buttons.AppButton
import project.components.table.RowTable
import project.data.models.BikeReturn
import project.data.*

@Page
@Composable
fun BikeProblems() {
    var problems by remember { mutableStateOf(Storage.getAllProblems()) }
    var selectedProblem by remember { mutableStateOf<BikeReturn?>(null) }

    Row(modifier = Modifier.fillMaxSize()) {
        SideBar()

        ContentBox({
            GlassCard(modifier = Modifier.fillMaxWidth().padding(0.px)) {
                Row(modifier = Modifier.fillMaxWidth()) {

                    // left side
                    Column(
                        modifier = Modifier
                            .width(350.px)
                            .styleModifier {
                                property("border-right", "1px solid ${rgba(255,255,255,0.1)}")
                                property("overflow-y", "auto")
                            }
                    ) {
                        TextTitle("Issue Reports")

                        problems.forEach { problem ->
                            IssueListRow(
                                problem = problem,
                                isSelected = selectedProblem?.id == problem.id,
                                onClick = { selectedProblem = problem }
                            )
                        }
                    }

                    // right side
                    Column(modifier = Modifier.fillMaxWidth().padding(30.px)) {
                        if (selectedProblem == null) {
                            EmptyState("Select a report from the list to view details")
                        } else {
                            ProblemDetailsContent(selectedProblem!!)
                        }
                    }
                }
            }
        })
    }
}

@Composable
private fun IssueListRow(problem: BikeReturn, isSelected: Boolean, onClick: () -> Unit) {
    RowTable(isSelected = isSelected, onClick = onClick) {
        Column {
            Span(attrs = Modifier.color(Color.white).fontWeight(FontWeight.Bold).toAttrs()) {
                Text("Issue #${problem.id}")
            }
            Span(attrs = Modifier.color(Color.gray).fontSize(12.px).toAttrs()) {
                Text("Rental ID: ${problem.rentalId}")
            }
        }
        I(attrs = { classes("fa", "fa-chevron-right"); style { color(rgba(255,255,255,0.2)) } })
    }
}

@Composable
private fun ProblemDetailsContent(p: BikeReturn) {
    TextTitle("Problem Details")

    Row(modifier = Modifier.fillMaxWidth().margin(top = 25.px), horizontalArrangement = Arrangement.spacedBy(40.px)) {
        ColumnItem("#ID", "#${p.id}", rgb(239, 68, 68))
        ColumnItem("Date Reported", "${p.returnedAt}", Color.white)
        ColumnItem("Rental ID", "#${p.rentalId}", Color.white)
    }

    Column(modifier = Modifier.fillMaxWidth().margin(top = 30.px)) {
        SectionLabel("DESCRIPTION", Modifier.margin(bottom = 10.px))
        Span(attrs = Modifier.color(Color.white).fontSize(16.px).toAttrs()) {
            Text(p.issueDescription ?: "No description provided.")
        }
    }

    Column(modifier = Modifier.margin(top = 30.px)) {
        SectionLabel("PHOTOS", Modifier.margin(bottom = 10.px))
        Row {
            p.photos.forEach { photo ->
                Img(src = photo, attrs = Modifier.size(140.px).borderRadius(12.px).margin(right = 12.px).styleModifier { property("object-fit", "cover") }.toAttrs())
            }
        }
    }

    Row(modifier = Modifier.margin(top = 40.px), horizontalArrangement = Arrangement.spacedBy(15.px)) {
        AppButton({},"Start Maintenance")
        AppButton({},"Deactivate Bike")
    }
}

@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Span(
        attrs = modifier
            .color(Color.gray)
            .fontSize(11.px)
            .fontWeight(FontWeight.Bold)
            .styleModifier {
                letterSpacing(1.2.px)
                textTransform(TextTransform.Uppercase)
            }
            .toAttrs()
    ) {
        Text(text)
    }
}