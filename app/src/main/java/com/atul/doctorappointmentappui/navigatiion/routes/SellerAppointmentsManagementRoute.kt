package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.feature.appointment.SellerAppointmentsManagementScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.sellerAppointmentsManagementRoute(appointmentModel: AppointmentModel) {
    composable(Screen.SellerAppointmentsManagementScreen.route) {
        SellerAppointmentsManagementScreen(appointmentModel = appointmentModel)
    }
}