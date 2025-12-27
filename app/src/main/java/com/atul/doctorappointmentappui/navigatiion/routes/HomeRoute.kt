package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.viewmodel.AppointmentViewModel
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.core.viewmodel.SellerDataViewModel
import com.atul.doctorappointmentappui.core.viewmodel.UserDataViewModel
import com.atul.doctorappointmentappui.feature.home.MainScreenWrapper
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.homeRoute(
    mainViewModel: MainViewModel,
    userDataViewModel: UserDataViewModel,
    sellerDataViewModel: SellerDataViewModel,
    appointmentViewModel: AppointmentViewModel,
    userId: String,
    showBanner: Boolean,
    onBannerClick: () -> Unit,
    onOpenTopDoctors: () -> Unit,
    onManageAccountClick: () -> Unit,
    onSignOut: () -> Unit
) {
    composable (Screen.Home.route) {
        MainScreenWrapper(
            mainViewModel = mainViewModel,
            userDataViewModel = userDataViewModel,
            sellerDataViewModel = sellerDataViewModel,
            appointmentViewModel = appointmentViewModel,
            userId = userId,
            showBanner = showBanner,
            onBannerClick = { onBannerClick() },
            onOpenTopDoctors = { onOpenTopDoctors() },
            onManageAccountClick = { onManageAccountClick() },
            signOutUser = {
                onSignOut()
            }
        )
    }
}