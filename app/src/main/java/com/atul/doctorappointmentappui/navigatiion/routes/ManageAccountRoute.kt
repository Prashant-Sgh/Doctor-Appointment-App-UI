package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.feature.manageAccount.ManageAccountScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.manageAccountRoute(userData: UserModel?) {
    composable(Screen.ManageAccount.route) {

        var data = userData
        if(userData == null) {
            data = UserModel(userName = "Not fetched")
        }

        ManageAccountScreen(data)
    }
}