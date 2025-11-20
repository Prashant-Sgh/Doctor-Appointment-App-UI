package com.atul.doctorappointmentappui.navigatiion

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.navigatiion.routes.detailRoute
import com.atul.doctorappointmentappui.navigatiion.routes.homeRoute
import com.atul.doctorappointmentappui.navigatiion.routes.introRoute

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
                Log.d("NavigateToDetail", "onOpenDetail CLICKED!!")
                navCon.navigateToDetail(doctorModel)
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