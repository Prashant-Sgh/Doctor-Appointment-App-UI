package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.viewmodel.AppointmentViewModel
import com.atul.doctorappointmentappui.feature.appointment.AppointmentRoute
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.appointmentRoute (
    appointmentViewModel: AppointmentViewModel,
    authId: String,
    seller: Boolean,
    onViewAppointment: (AppointmentModel) -> Unit,
    onCancelAppointment: (String) -> Unit
) {
    composable (Screen.AppointmentsScreen.route) {
        AppointmentRoute(
            appointmentViewModel = appointmentViewModel,
            authId = authId,
            seller = seller,
            onViewAppointment = onViewAppointment,
            onCancelAppointment = onCancelAppointment
        )
    }
}