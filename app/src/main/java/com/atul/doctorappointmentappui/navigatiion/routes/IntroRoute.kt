package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.feature.intro.IntroScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.introRoute(onStart: () -> Unit) {
    composable (Screen.Intro.route) {
        IntroScreen (onStart)
    }
}