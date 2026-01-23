package project.components.table

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text


@Composable
fun HeaderTable(columns: List<TableColumn>){
    Row(
        modifier = Modifier.fillMaxWidth().padding(left=10.px,bottom = 10.px),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        columns.forEach { column ->
            Span(
                attrs = Modifier
                    .width(column.width)
                    .color(Color.gray)
                    .toAttrs()
            ) {
                Text(column.title)
            }
        }
    }
}

data class TableColumn(
    val title: String,
    val width: CSSSizeValue<CSSUnit.px>
)