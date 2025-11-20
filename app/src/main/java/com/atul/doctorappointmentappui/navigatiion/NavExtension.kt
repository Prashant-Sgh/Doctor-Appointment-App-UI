package com.atul.doctorappointmentappui.navigatiion

import android.util.Log
import androidx.navigation.NavController
import com.atul.doctorappointmentappui.core.model.DoctorModel

fun NavController.navigateToDetail(doctorModel: DoctorModel) {
    currentBackStackEntry?.savedStateHandle?.set("doctor", doctorModel)
    Log.d("NavigateToDetail", "Name is: ${doctorModel.Name}")
    navigate(Screen.Detail.route)
}