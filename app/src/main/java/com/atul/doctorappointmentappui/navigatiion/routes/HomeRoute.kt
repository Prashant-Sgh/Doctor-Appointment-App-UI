package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.feature.home.MainScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.homeRoute(
    viewmodel: MainViewModel,
    showSellerBanner: Boolean,
    onBannerClick: () -> Unit,
    onOpenDetails: (DoctorModel) -> Unit,
    onOpenTopDoctors: () -> Unit,
    onManageAccount: () -> Unit,
    onOpenUserProfile: () -> Unit,
    onOpenDrProfile: () -> Unit,
) {
    composable (Screen.Home.route) {
        val category by viewmodel.category.collectAsState()
        val doctors by viewmodel.doctors.collectAsState()

        LaunchedEffect("string") {
            if (category.isEmpty()) viewmodel.loadCategory()
            if (doctors.isEmpty()) viewmodel.loadDoctors()
        }

        MainScreen(
            viewModel = viewmodel,
            showSellerBanner = showSellerBanner,
            onBannerClick = onBannerClick,
            onOpenDoctorDetails = { onOpenDetails(it) },
            onOpenTopDoctors = { onOpenTopDoctors() },
            onManageAccount = { onManageAccount() },
            onOpenUserProfile = onOpenUserProfile,
            onOpenDrProfile = onOpenDrProfile
        )
    }
}