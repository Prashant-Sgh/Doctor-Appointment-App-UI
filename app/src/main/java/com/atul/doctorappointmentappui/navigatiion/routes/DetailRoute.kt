package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.savedstate.savedState
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.detailRoute(
    nav: NavHostController,
    onBack:() -> Unit
) {
    composable (Screen.Detail.route) {
        backStackEntry: NavBackStackEntry ->
        val context = LocalContext.current
        val prevEntry = remember (nav) { nav.previousBackStackEntry }
        val doctor = remember (prevEntry) { prevEntry?.savedStateHandle?.get<DoctorModel>("doctor") }

        LaunchedEffect(prevEntry, doctor) {
            if (doctor == null) {
                onBack()
            }
            else {
                prevEntry?.savedStateHandle?.remove<DoctorModel>("doctor")
            }
        }

    }
}