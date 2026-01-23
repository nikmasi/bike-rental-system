package project.components.sidebar

import androidx.compose.runtime.Composable
import project.components.layouts.AppLayout

@Composable
fun SideBar(){
    AppLayout(
        content = {
            SidebarLink("HOME", "/home")
            SidebarLink("VIEW ALL BIKES", "/bikes")
            SidebarLink("ADD NEW BIKE", "/add-bike")
            SidebarLink("VIEW ALL RENTALS", "/rentals")
            SidebarLink("VIEW BIKE PHOTOS", "/returns")
            SidebarLink("VIEW REPORTED ISSUES", "/bike-problems")
        }
    )
}