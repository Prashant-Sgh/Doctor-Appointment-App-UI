package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.feature.home.MainScreen
import com.atul.doctorappointmentappui.feature.home.MainScreenWrapper
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.homeRoute(
    showBanner: Boolean,
    onBannerClick: () -> Unit,
    onSignOut: () -> Unit
) {
    composable (Screen.Home.route) {
        MainScreenWrapper(
            showBanner = showBanner,
            onBannerClick = { onBannerClick() },
            signOutUser = {
                onSignOut()
            }
        )
    }
}