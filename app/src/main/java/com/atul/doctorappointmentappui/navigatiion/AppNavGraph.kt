package com.atul.doctorappointmentappui.navigatiion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.navigatiion.routes.detailRoute
import com.atul.doctorappointmentappui.navigatiion.routes.homeRoute
import com.atul.doctorappointmentappui.navigatiion.routes.introRoute
import com.atul.doctorappointmentappui.navigatiion.routes.topDoctorsRoute

@Composable
fun AppNavGraph(
    navCon: NavHostController,
    vm: MainViewModel
) {
    NavHost(navCon, Screen.Intro.route) {
        introRoute(
            onStart = {
                navCon.navigate(Screen.Home.route) {
                    popUpTo(Screen.Intro.route) { inclusive = true }
                }
            }
        )
        homeRoute(
            viewmodel = vm,
            onOpenDetails = { doctorModel ->
                navCon.navigateToDetail(doctorModel)
            },
            onOpenTopDoctors = {
                navCon.navigate(Screen.TopDoctors.route)
            }
        )

        topDoctorsRoute(
            viewmodel = vm,
            onBack = {
                navCon.popBackStack()
            },
            onOpenDetails = {doctor ->
                navCon.navigateToDetail(doctor)
            }
        )

        detailRoute(
            nav = navCon,
            onBack = {
                navCon.popBackStack()
            }
        )
    }
}