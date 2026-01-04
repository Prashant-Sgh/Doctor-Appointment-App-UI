package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.feature.splash.BrandedLoadingScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.splashScreenRoute(
    afterLoading: () -> Unit,
    loadingState: Boolean
) {
    composable(Screen.SplashScreen.route) {
        BrandedLoadingScreen(
            afterLoading = afterLoading,
            loadingState = loadingState
            )

    }
}