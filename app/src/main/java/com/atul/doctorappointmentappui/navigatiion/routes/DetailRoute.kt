package com.atul.doctorappointmentappui.navigatiion.routes

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.savedstate.savedState
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.feature.detail.DetailScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.detailRoute(
    nav: NavHostController,
    onBack:() -> Unit
) {
    composable (Screen.Detail.route) {
        backStackEntry: NavBackStackEntry ->
        val context = LocalContext.current
        val prevEntry = remember (nav) { nav.previousBackStackEntry }
        val doctor = remember (prevEntry) { prevEntry?.savedStateHandle?.get<DoctorModel>("doctor") }

        LaunchedEffect(prevEntry, doctor) {
            if (doctor == null) {
                onBack()
            }
            else {
                prevEntry?.savedStateHandle?.remove<DoctorModel>("doctor")
            }
        }

        if(doctor != null) {
            DetailScreen(
                item = doctor,
                onBack = onBack,
                onOpenWebsite = {url ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                },
                onSendSms = {mobile, body ->
                    context.startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$mobile"))
                        .apply { putExtra("sms body", body) }
                    )
                },
                onDial = {mobile ->
                    context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${mobile.trim()}")))
                },
            )
        }

    }
}