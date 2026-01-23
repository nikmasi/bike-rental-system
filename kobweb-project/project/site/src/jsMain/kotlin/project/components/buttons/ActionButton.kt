package project.components.buttons

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.I

@Composable
fun ActionButton(icon: String, iconColor: CSSColorValue, onClick: () -> Unit) {
    Button(
        attrs = Modifier
            .backgroundColor(Color.transparent)
            .cursor(Cursor.Pointer)
            .padding(5.px)
            .toAttrs { onClick { onClick() } }
    ) {
        I(attrs = {
            classes("fa", icon)
            style { color(iconColor) }
        })
    }
}