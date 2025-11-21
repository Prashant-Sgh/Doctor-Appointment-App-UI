package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.feature.topDoctors.TopDoctorScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.topDoctorsRoute(
    viewmodel: MainViewModel,
    onBack: ()-> Unit,
    onOpenDetails: (DoctorModel) -> Unit
) {
    composable(Screen.TopDoctors.route) {
        val topDoctors by viewmodel.doctors.collectAsState()

        LaunchedEffect(Unit) {
            if (topDoctors.isEmpty()) viewmodel.loadDoctors()
        }

        TopDoctorScreen(
            topDoctors,
            onBack = { onBack() },
            onOpendetail = { onOpenDetails(it) }
        )
    }
}