package project.components.buttons

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Composable
fun ToggleButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        attrs = Modifier
            .padding(leftRight = 15.px, topBottom = 8.px)
            .borderRadius(8.px)
            .backgroundColor(if (isSelected) rgb(34, 197, 94) else rgba(255,255,255,0.1))
            .color(Color.white)
            .cursor(Cursor.Pointer)
            .styleModifier { property("border", "none") }
            .toAttrs { onClick { onClick() } }
    ) {
        Text(text)
    }
}