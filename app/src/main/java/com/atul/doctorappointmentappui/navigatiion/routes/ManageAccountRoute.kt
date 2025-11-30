package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.feature.manageAccount.ManageAccountScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.manageAccountRoute() {
    composable(Screen.ManageAccount.route) {
        ManageAccountScreen()
    }
}