package com.atul.doctorappointmentappui.feature.appointment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.AppointmentModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAppointmentScaffold(
    appointments: List<AppointmentModel>,
    onAppointmentCancel: (String) -> Unit
) {
    Scaffold(
        topBar = {
            OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = {
                    Text(
                        text = "My Appointments",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = colorResource(R.color.lightGgray) // Light background
    ) { padding ->
        if (appointments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No appointments yet.",
                    color = colorResource(R.color.ggray)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(appointments) { appointment ->
                    UserAppointmentCard(
                        appointment = appointment,
                        onViewClick = { appointmentId -> onAppointmentCancel(appointmentId) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    UserAppointmentScaffold(
        appointments = listOf(
            AppointmentModel(
                appointmentId = "appt123",
                patientName = "Atul Singh",
                problemDescription = "Follow-up after seasonal flu",
                status = "CONFIRMED",
                date = com.google.firebase.Timestamp.now() // Uses current time for the preview
            ),
            AppointmentModel(
                appointmentId = "appt123",
                patientName = "Bram to",
                problemDescription = "Follow-up after seasonal flu",
                status = "CANCELLED",
                date = com.google.firebase.Timestamp.now() // Uses current time for the preview
            ),
            AppointmentModel(
                appointmentId = "appt123",
                patientName = "Atul",
                problemDescription = "Follow-up after seasonal flu",
                status = "PENDING",
                date = com.google.firebase.Timestamp.now() // Uses current time for the preview
            )
        )
    ) { }
}