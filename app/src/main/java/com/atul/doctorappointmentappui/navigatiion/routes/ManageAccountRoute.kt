package com.atul.doctorappointmentappui.navigatiion.routes

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.core.viewmodel.UserDataViewModel
import com.atul.doctorappointmentappui.feature.manageAccount.ManageAccountScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.manageAccountRoute(userDataVm: UserDataViewModel, saveUserData: (UserModel) -> Unit) {
    val userDataFlow = userDataVm.userData
    composable(Screen.ManageAccount.route) {
//        Log.d("Firestore", "From manageAccountRoute: User Name = ${userDataFlow.value.userName}")
        ManageAccountScreen(userDataFlow) {saveUserData(it)}
    }
}