package project.styles

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.css.width

@Composable
fun inputStyle(): Modifier =
    Modifier
        .fillMaxWidth()
        .height(42.px)
        .padding(leftRight = 12.px)
        .borderRadius(8.px)
        .fontSize(14.px)
        .styleModifier {
            backgroundColor(rgba(255, 255, 255, 0.18))
            color(Color.white)
            border {
                style(LineStyle.Solid)
                width(1.px)
                color(rgba(255, 255, 255, 0.25))
            }
        }