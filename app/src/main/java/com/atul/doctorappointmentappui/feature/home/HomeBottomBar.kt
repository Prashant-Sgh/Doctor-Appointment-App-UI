package com.atul.doctorappointmentappui.feature.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.navigatiion.Screen

@Composable
fun HomeBottomBar (
    seller: Boolean,
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {

    // 1. Defined a simple helper to group data
    data class BottomTab(val label: String, val route: String, val icon: ImageVector)

    // 2. Defined tabs here.
    val usersTabs = listOf(
        BottomTab("Home", Screen.MainScreen.route, Icons.Default.Home),
        BottomTab("Appointments", Screen.UserAppointmentsScreen.route, Icons.Default.CalendarMonth),
        BottomTab("Profile", Screen.ManageAccount.route, Icons.Default.ManageAccounts)
    )
    val sellerTabs = listOf(
        BottomTab("Home", Screen.MainScreen.route, Icons.Default.Home),
        BottomTab("Appointments", Screen.SellerAppointmentsScreen.route, Icons.Default.CalendarMonth),
        BottomTab("Profile", Screen.ManageAccount.route, Icons.Default.ManageAccounts),
        BottomTab("Doctor", Screen.DrProfileManagement.route, Icons.Default.ManageAccounts)
    )

    val tabs = if (seller) sellerTabs else usersTabs

    NavigationBar(
        contentColor = colorResource(R.color.lightGgray),
        modifier = Modifier.height(100.dp),
        tonalElevation = 1.dp,
        windowInsets = WindowInsets.navigationBars
    ) {
        tabs.forEach { tab ->
            // Checking if the current route matches this tab
            val isSelected = currentRoute == tab.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        onNavigate(tab.route)
                    }
                },
                icon = {
                    Icon(
                        tab.icon,
                        contentDescription = tab.label,
                        modifier = Modifier.size(22.dp),
                        tint = if(isSelected) colorResource(R.color.puurple) else Color.Gray
                    )
                },
                label = {
                    Text(
                        tab.label,
                        color = if (isSelected) colorResource(R.color.puurple) else Color.Black,
                        fontSize = 10.sp
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeBottomBarPreview() {
//    // Preview 1: Home Selected
//    HomeBottomBar(
//        currentRoute = Screen.MainScreen.route,
//        onNavigate = {})
//}

//@Preview(showBackground = true)
//@Composable
//fun HomeBottomBarAppointmentsPreview() {
//    // Preview 2: Appointments Selected
//    HomeBottomBar(
//        currentRoute = Screen.SellerAppointmentsScreen.route,
//        onNavigate = {}
//    )
//}