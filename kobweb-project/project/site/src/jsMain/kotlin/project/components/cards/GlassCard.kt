package project.components.cards

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.css.width

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(80.percent)
            .fillMaxHeight(80.percent)
            .styleModifier {
                property("max-width", "800.px")
                property("max-height", "800.px")
                property("overflow-y", "auto")
                property("padding-right", "10.px")
                property("&::-webkit-scrollbar", "width: 6px")
                property("&::-webkit-scrollbar-track", "background: transparent")

                property("&::-webkit-scrollbar-thumb", """
                    background: rgba(255, 255, 255, 0.2);
                    border-radius: 10px;
                """.trimIndent())

                property("&::-webkit-scrollbar-thumb:hover", "background: rgba(255, 255, 255, 0.4)")
                property("scrollbar-width", "thin")
                property("scrollbar-color", "rgba(255, 255, 255, 0.2) transparent")
                backgroundColor(rgba(0, 0, 0, 0.5))

                val blurEffect = blur(15.px)
                property("backdrop-filter", blurEffect)
                property("-webkit-backdrop-filter", blurEffect)

                border {
                    style(LineStyle.Solid)
                    width(1.px)
                    color(rgba(255, 255, 255, 0.2))
                }
            }
            .borderRadius(24.px)
            .padding(40.px),
        verticalArrangement = Arrangement.spacedBy(24.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}