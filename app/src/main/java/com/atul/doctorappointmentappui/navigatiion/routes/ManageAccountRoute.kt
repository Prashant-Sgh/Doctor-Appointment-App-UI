package com.atul.doctorappointmentappui.navigatiion.routes

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.core.viewmodel.UserDataViewModel
import com.atul.doctorappointmentappui.feature.manageAccount.ManageAccountRoute
import com.atul.doctorappointmentappui.feature.manageAccount.ManageAccountScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.manageAccountRoute(
    userViewModel: UserDataViewModel,
    userId: String,
    signOutUser: () -> Unit,
    onNavigateToSellerRegistration: () -> Unit
) {
    composable(Screen.ManageAccount.route) {
        ManageAccountRoute(
            viewModel = userViewModel,
            userId = userId,
            onSignOut = { signOutUser() },
            onNavigateToSellerRegistration = onNavigateToSellerRegistration
        )
    }
}