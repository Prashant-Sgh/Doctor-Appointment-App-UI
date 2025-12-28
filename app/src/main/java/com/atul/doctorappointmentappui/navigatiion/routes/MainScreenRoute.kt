package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.feature.home.MainScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.mainScreenRoute(
    mainViewModel: MainViewModel,
    userName: String,
    showBanner: Boolean,
    onBannerClick: () -> Unit,
    onOpenDoctorDetails: (DoctorModel) -> Unit,
    onOpenTopDoctors: () -> Unit,
    onReload: () -> Unit,
) {
    composable (Screen.MainScreen.route) {
        MainScreen(
            mainViewModel = mainViewModel,
            userName  = userName,
            showBanner = showBanner,
            onBannerClick = { onBannerClick() },
            onOpenDoctorDetails = { onOpenDoctorDetails(it) },
            onOpenTopDoctors = { onOpenTopDoctors() },
            onReload = onReload,
        )
    }
}