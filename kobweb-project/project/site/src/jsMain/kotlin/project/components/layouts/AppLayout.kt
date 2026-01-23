package project.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb

@Composable
fun AppLayout(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .width(220.px)
            .fillMaxHeight()
            .backgroundColor(rgb(30, 30, 30))
            .padding(20.px),
        verticalArrangement = Arrangement.spacedBy(16.px),
        horizontalAlignment = Alignment.Start
    ) {
        content()
    }
}
