package com.atul.doctorappointmentappui.navigatiion

import android.util.Log
import androidx.navigation.NavController
import com.atul.doctorappointmentappui.core.model.DoctorModel

fun NavController.navigateToDetail(doctorModel: DoctorModel) {
    currentBackStackEntry?.savedStateHandle?.set("doctor", doctorModel)
    navigate(Screen.Detail.route)
}