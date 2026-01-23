package project.components.table

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.minHeight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba

@Composable
fun RowTable(
    onClick: (() -> Unit)? = null,
    isSelected: Boolean = false,
    content: @Composable () -> Unit,

){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.cursor(Cursor.Pointer).onClick { onClick() }
                } else Modifier
            )
            .padding(15.px)
            .borderRadius(12.px)
            .styleModifier {
                if (isSelected) {
                    backgroundColor(rgba(255, 255, 255, 0.1))
                    property("border-left", "3px solid #3b82f6")
                }
                minHeight(110.px)
                backgroundColor(rgba(255, 255, 255, 0.05))
                property("border-bottom", "1px solid rgba(255,255,255,0.1)")
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        content()
    }
}
