package project.components.cards

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.styleModifier
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.css.width

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .width(420.px)
            .padding(40.px)
            .borderRadius(20.px)
            .styleModifier {
                backgroundColor(rgba(0, 0, 0, 0.45))
                property("backdrop-filter", "blur(12px)")
                property("-webkit-backdrop-filter", "blur(12px)")

                border {
                    style(LineStyle.Solid)
                    width(1.px)
                    color(rgba(255, 255, 255, 0.25))
                }
            },
        verticalArrangement = Arrangement.spacedBy(18.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}