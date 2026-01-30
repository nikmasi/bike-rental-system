package project.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.backgroundImage
import org.jetbrains.compose.web.css.backgroundPosition
import org.jetbrains.compose.web.css.backgroundSize
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text


@Composable
fun ContentBox(
    content: @Composable ColumnScope.() -> Unit
){
    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .styleModifier {
                    backgroundColor(rgb(15, 23, 42))
                    backgroundImage("url('/bike_hd.webp')")
                    backgroundSize("cover")
                    backgroundPosition("center")
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.px)
                .zIndex(10),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.px)
        ) {
            // LOGOUT
            Span(
                attrs = Modifier
                    .cursor(Cursor.Pointer)
                    .color(Color.white)
                    .toAttrs {
                        onClick {
                            window.localStorage.clear()
                            window.location.href = "/login"
                        }
                    }
            ) {
                Text("LOGOUT")
            }

            Box(
                modifier = Modifier
                    .size(36.px)
                    .borderRadius(50.percent)
                    .backgroundColor(rgb(30, 30, 30))
                    .cursor(Cursor.Pointer)
                    .attrsModifier {
                        onClick {
                            window.alert("Radi!")
                            window.location.href = "/change-password"
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text("A")
            }
        }
    }
}