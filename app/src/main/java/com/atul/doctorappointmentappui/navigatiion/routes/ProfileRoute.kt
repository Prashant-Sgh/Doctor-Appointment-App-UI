package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.feature.profileTab.ManageProfileScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.profileRoute(
    onOpenUserProfile: () -> Unit,
    onOpenDrProfile: () -> Unit
) {
    composable(Screen.ProfileManagement.route) {
        ManageProfileScreen(
            onOpenUserProfile = { onOpenUserProfile() },
            onOpenDrProfile = { onOpenDrProfile() }
        )
    }
}