package project.components.buttons

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Composable
fun AppButton(
    onButtonClick: () -> Unit,
    text: String
){
    Button(
        attrs = Modifier
            .width(120.px)
            .height(38.px)
            .borderRadius(8.px)
            .backgroundColor(rgb(39, 35, 35))
            .color(Color.white)
            .fontSize(12.px)
            .cursor(Cursor.Pointer)
            .toAttrs { onClick { onButtonClick() } }
    ) {
        Text(text)
    }
}