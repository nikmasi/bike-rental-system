package project.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import project.data.*

@Page
@Composable
fun HomePage() {
    val ctx = rememberPageContext()
    LaunchedEffect(Unit) {
        if(Storage.isLoggedIn()){
            ctx.router.tryRoutingTo("/home")
        }
        else{
            ctx.router.tryRoutingTo("/login")
        }
    }
}