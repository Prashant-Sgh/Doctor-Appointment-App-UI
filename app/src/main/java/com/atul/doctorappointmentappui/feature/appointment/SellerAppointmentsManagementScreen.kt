// In: feature/appointment/SellerAppointmentsManagementScreen.kt

package com.atul.doctorappointmentappui.feature.appointment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.viewmodel.AppointmentViewModel
import com.atul.doctorappointmentappui.feature.appointment.Components.AppointmentActionsBottomBar
import com.atul.doctorappointmentappui.feature.appointment.Components.AppointmentDetailsCard
import com.atul.doctorappointmentappui.feature.appointment.Components.PatientHeader

@Composable
fun SellerAppointmentsManagementRoute(
    appointmentId: String,
    viewModel: AppointmentViewModel,
    onBack: () -> Unit,
) {
    // 1. Collecting the list of all appointments from the ViewModel
    val appointments by viewModel.appointments.collectAsState()

    // 2. Find the specific appointment to display
    //    This is safe because `remember` will re-run if appointments or appointmentId change.
    val appointment = remember(appointmentId, appointments) {
        appointments.find { it.appointmentId == appointmentId }
    }

    // 3. Define the actions (lambdas) that interact with the ViewModel
    val onConfirm: () -> Unit = {
        appointment?.let { viewModel.updateStatus(it.appointmentId, "CONFIRMED") }
        onBack() // Navigate back after the action is dispatched
    }

    val onCancel: () -> Unit = {
        appointment?.let { viewModel.updateStatus(it.appointmentId, "CANCELLED") }
        onBack() // Navigate back after the action is dispatched
    }

    // 4. Call the Stateless UI Composable with pure data and lambdas
    SellerAppointmentsManagementScreen(
        appointment = appointment,
        onBack = onBack,
        onConfirm = onConfirm,
        onCancel = onCancel
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerAppointmentsManagementScreen(
    appointment: AppointmentModel?, // Takes pure data, not a ViewModel
    onBack: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Appointment") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            )
        },
        bottomBar = {
            // Show action buttons only if the appointment data is loaded and status is PENDING
            appointment?.let { appt ->
                if (appt.status == "PENDING") {
                    AppointmentActionsBottomBar(
                        onConfirm = onConfirm, // Use the passed lambda
                        onCancel = onCancel   // Use the passed lambda
                    )
                }
            }
        }
    ) { innerPadding ->
        // Show a loading indicator if appointment is null
        if (appointment == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Display appointment details
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                PatientHeader(appointment)
                Spacer(modifier = Modifier.height(24.dp))
                AppointmentDetailsCard(appointment)
            }
        }
    }
}


// --- THE BIG PAYOFF: EASY AND RELIABLE PREVIEWS ---
//@Preview(showBackground = true, name = "Displaying Appointment")
//@Composable
//private fun SellerAppointmentsManagementScreenPreview() {
//    MaterialTheme {
//        val dummyAppointment = AppointmentModel(
//            appointmentId = "123",
//            patientName = "John Doe",
//            problemDescription = "Feeling unwell with symptoms of a common cold and persistent cough for the last 3 days.",
//            date = "Dec 19, 2025",
//            time = "10:30 AM",
//            status = "PENDING"
//        )
//        SellerAppointmentsManagementScreen(
//            appointment = dummyAppointment,
//            onBack = {},
//            onConfirm = {},
//            onCancel = {}
//        )
//    }
//}