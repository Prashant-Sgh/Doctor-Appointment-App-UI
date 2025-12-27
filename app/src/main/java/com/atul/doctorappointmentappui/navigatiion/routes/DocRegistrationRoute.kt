package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.feature.registerAsDoctor.DoctorRegistrationRoute
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder. drRegistrationRoute(
    userId: String,
    onNavigateBack: () -> Unit
) {
    composable(Screen.DrRegistrationScreen.route){
        DoctorRegistrationRoute(
            userId = userId,
            onNavigateBack = onNavigateBack,
        )
    }
}