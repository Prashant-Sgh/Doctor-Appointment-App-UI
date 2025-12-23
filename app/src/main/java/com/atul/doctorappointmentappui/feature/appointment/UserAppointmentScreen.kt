package com.atul.doctorappointmentappui.feature.appointment

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.core.viewmodel.AppointmentViewModel
import com.atul.doctorappointmentappui.feature.appointment.Components.SellerAppointmentScaffold
import com.atul.doctorappointmentappui.feature.appointment.Components.UserAppointmentScaffold
import kotlinx.coroutines.flow.StateFlow

@Composable
fun UserAppointmentsScreen(
    appointmentViewModel: AppointmentViewModel,
    userId: String,
) {

    // 1. Fetch data when screen opens
    LaunchedEffect(userId) {
        appointmentViewModel.fetchUserAppointments(userId)
        Log.d("UserAppointmentsScreen", "Fetching appointments for user: $userId")
    }

    // 2. Observe the state
    val appointments by appointmentViewModel.appointments.collectAsState()
    val isLoading by appointmentViewModel.isLoading.collectAsState()

    UserAppointmentsContent(
        appointments = appointments,
        isLoading = isLoading,
        onAppointmentCancel = { appointmentId -> appointmentViewModel.updateStatus(appointmentId, "CANCELLED") }
    )

}

// 2. The "Pure" UI Composable (Easy to Preview, no Hilt)
@Composable
fun UserAppointmentsContent(
    appointments: List<AppointmentModel>,
    isLoading: Boolean,
    onAppointmentCancel: (String) -> Unit
) {
    if (isLoading && appointments.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = colorResource(R.color.puurple))
        }
    } else {
        UserAppointmentScaffold(
            appointments = appointments,
            onAppointmentCancel = { onAppointmentCancel(it) }
        )
    }
}