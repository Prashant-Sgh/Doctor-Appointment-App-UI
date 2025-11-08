package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.feature.home.MainScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.homeRoute(
    viewmodel: MainViewModel
) {
    composable (Screen.Home.route) {
        val category by viewmodel.category.collectAsState()

        LaunchedEffect("string") {
            if (category.isEmpty()) viewmodel.loadCategory()
        }

        MainScreen(viewmodel)
    }
}