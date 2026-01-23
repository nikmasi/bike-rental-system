package project.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import org.jetbrains.compose.web.css.px

@Composable
fun NavLink(
    path: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Link(
        path = path,
        text = text,
        modifier = Modifier.padding(0.px,25.px),
        variant = UndecoratedLinkVariant
    )
}