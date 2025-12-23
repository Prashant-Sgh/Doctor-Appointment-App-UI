package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.core.viewmodel.AppointmentViewModel
import com.atul.doctorappointmentappui.feature.appointment.UserAppointmentsScreen
import com.atul.doctorappointmentappui.navigatiion.Screen
import kotlinx.coroutines.flow.StateFlow

fun NavGraphBuilder.userAppointmentRoute(
    appointmentViewModel: AppointmentViewModel,
    userId: String,
) {
    composable(Screen.UserAppointmentsScreen.route) {
        UserAppointmentsScreen(
            appointmentViewModel = appointmentViewModel,
            userId = userId,
        )
    }
}