package com.atul.doctorappointmentappui.navigatiion.routes

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
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
                    context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                },
                onSendSms = {mobile, body ->
                    context.startActivity(Intent(Intent.ACTION_SENDTO, "smsto:${mobile.trim()}".toUri())
                        .apply { putExtra("sms_body", body) }
                    )
                },
                onDial = {mobile ->
                    context.startActivity(Intent(Intent.ACTION_DIAL, "tel:${mobile.trim()}".toUri()))
                },
                onDirection = {loc ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, loc.toUri()))
                },
                onShare = {subject, text ->
                    context.startActivity(
                        Intent.createChooser(
                            Intent(Intent.ACTION_SEND).apply {
                                type="text/plain"
                                putExtra(Intent.EXTRA_SUBJECT,subject)
                                putExtra(Intent.EXTRA_TEXT,text)
                            },
                            "choose one"
                        )
                    )
                }
            )
        }
        else {
            Spacer(Modifier.height(1.dp))
        }

    }
}