package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.feature.home.MainScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.mainScreenRoute(
    showBanner: Boolean,
    onBannerClick: () -> Unit,
    onOpenDoctorDetails: (DoctorModel) -> Unit,
    onOpenTopDoctors: () -> Unit,
    onManageAccount: () -> Unit,
) {
    composable (Screen.MainScreen.route) {
        MainScreen(
            showBanner = showBanner,
            onBannerClick = { onBannerClick() },
            onOpenDoctorDetails = { onOpenDoctorDetails(it) },
            onOpenTopDoctors = { onOpenTopDoctors() },
            onManageAccount = { onManageAccount() },
        )
    }
}