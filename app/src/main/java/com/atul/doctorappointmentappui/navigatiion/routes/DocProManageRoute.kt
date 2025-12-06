package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.feature.profileTab.DocProfileManageScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.docProManageRoute(doctor: DoctorModel, onSavedConfirmed: (DoctorModel) -> Unit) {
    composable(Screen.DocProManage.route) {
        DocProfileManageScreen(doctor) { onSavedConfirmed(it) }
    }
}