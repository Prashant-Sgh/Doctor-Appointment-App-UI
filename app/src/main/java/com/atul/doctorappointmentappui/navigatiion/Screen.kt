package com.atul.doctorappointmentappui.navigatiion

sealed class Screen (val route: String) {
    data object Intro: Screen("intro")
    data object Home: Screen("home")
    data object TopDoctors: Screen("topDoctors")
    data object Detail: Screen("detail")
    data object Auth: Screen("auth")
}