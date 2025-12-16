package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.feature.appointment.SellerAppointmentsScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.sellerAppointmentRoute(
    onViewAppointment: (AppointmentModel) -> Unit
) {
    composable(Screen.SellerAppointmentsScreen.route) {
        SellerAppointmentsScreen(
            onViewAppointment = { onViewAppointment(it) }
        )
    }
}