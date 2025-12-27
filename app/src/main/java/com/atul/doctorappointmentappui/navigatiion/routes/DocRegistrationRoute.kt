package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.feature.registerAsDoctor.DoctorRegistrationRoute
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder. drRegistrationRoute(
    userId: String,
    onSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    composable(Screen.DrRegistrationScreen.route){
        DoctorRegistrationRoute(
            userId = userId,
            onSuccess = onSuccess,
            onNavigateBack = onNavigateBack,
        )
    }
}