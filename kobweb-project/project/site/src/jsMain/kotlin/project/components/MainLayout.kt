package project.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import project.components.sidebar.SideBar

@Composable
fun MainLayout(content: @Composable () -> Unit) {
    Row(modifier = Modifier.fillMaxSize()) {
        SideBar()

        ContentBox({
            content()
        })
    }
}
