package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.SellerDataViewModel
import com.atul.doctorappointmentappui.feature.profileTab.DocProfileManageScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.drProfileManagementRoute(sellerDataViewModel: SellerDataViewModel, onSavedConfirmed: (DoctorModel) -> Unit) {
    val doctorDataFlow = sellerDataViewModel.sellerData
    composable(Screen.DrProfileManagement.route) {
        DocProfileManageScreen(doctorDataFlow) { onSavedConfirmed(it) }
    }
}