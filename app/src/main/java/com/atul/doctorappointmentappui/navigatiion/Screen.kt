package com.atul.doctorappointmentappui.navigatiion

sealed class Screen (val route: String) {
    data object Intro: Screen("intro")
    data object Home: Screen("home")
    data object MainScreen: Screen("mainScreen")
    data object TopDoctors: Screen("topDoctors")
    data object Detail: Screen("detail")
    data object Auth: Screen("auth")
    data object ManageAccount: Screen("userAccount")
    data object DrProfileManagement: Screen("docAccount")
    data object CompleteUserProfile: Screen("completeUserProfile")
    data object SellerAppointmentsScreen: Screen("sellerAppointmentsScreen")
    data object SellerAppointmentsManagementScreen: Screen("sellerAppointmentsManagementScreen")
}