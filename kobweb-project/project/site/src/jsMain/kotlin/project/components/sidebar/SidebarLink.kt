package project.components.sidebar

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.cursor
import com.varabyte.kobweb.compose.style.KobwebComposeStyleSheet.hover
import com.varabyte.kobweb.compose.style.KobwebComposeStyleSheet.invoke
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Text

@Composable
fun SidebarLink(text: String, href: String) {
    A(
        href = href,
        attrs = Modifier
            .fontSize(14.px)
            .color(rgb(220, 220, 220))
            .textDecorationLine(TextDecorationLine.None)
            .styleModifier {
                hover {
                    color(rgb(99, 102, 241))
                    cursor(Cursor.Pointer)
                }
            }
            .toAttrs {}
    ) {
        Text(text)
    }
}