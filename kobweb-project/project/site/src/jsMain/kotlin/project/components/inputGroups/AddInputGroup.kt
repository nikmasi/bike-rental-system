package project.components.inputGroups

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import project.styles.inputStyle


@Composable
fun AddInputGroup(label: String, value: String, hint: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().margin(bottom = 15.px)) {
        Span(attrs = Modifier.color(Color.white).margin(bottom = 5.px).fontSize(14.px).toAttrs()) {
            Text(label)
        }
        Input(
            type = InputType.Text,
            attrs = inputStyle().toAttrs {
                placeholder(hint)
                value(value)
                onInput { onValueChange(it.value) }
            }
        )
    }
}