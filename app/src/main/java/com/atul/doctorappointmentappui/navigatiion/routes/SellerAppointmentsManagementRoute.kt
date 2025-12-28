package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.viewmodel.AppointmentViewModel
import com.atul.doctorappointmentappui.feature.appointment.SellerAppointmentsManagementRoute
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.sellerAppointmentsManagementRoute(
    appointmentViewModel: AppointmentViewModel,
    userPicture: String,
    onBack: () -> Unit
) {
    composable(
        route = Screen.SellerAppointmentsManagementScreen.route
    ) { navBackStackEntry ->

        val appointmentId = navBackStackEntry.arguments?.getString("appointmentId")

        if (appointmentId != null) {
            SellerAppointmentsManagementRoute(
                appointmentId = appointmentId,
                userPicture = userPicture,
                viewModel = appointmentViewModel,
                onBack = { onBack() }
            )
        }
        else {
            onBack()
        }
    }
}