package com.atul.doctorappointmentappui.navigatiion.routes

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.core.viewmodel.UserDataViewModel
import com.atul.doctorappointmentappui.feature.manageAccount.ManageAccountScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.manageAccountRoute(
    userViewModel: UserDataViewModel,
    signOutUser: () -> Unit
) {
    composable(Screen.ManageAccount.route) {
        ManageAccountScreen(
            userViewModel = userViewModel,
            signOutUser = { signOutUser() }
        )
    }
}