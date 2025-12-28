package com.atul.doctorappointmentappui.feature.appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.viewmodel.AppointmentViewModel
import com.atul.doctorappointmentappui.feature.appointment.components.SellerAppointmentCard
import com.atul.doctorappointmentappui.feature.appointment.components.UserAppointmentCard

@Composable
fun AppointmentRoute(
    appointmentViewModel: AppointmentViewModel,
    authId: String,
    seller: Boolean,
    onViewAppointment: (AppointmentModel) -> Unit,
    onCancelAppointment: (String) -> Unit
) {

    val sellerRole = remember { mutableStateOf(true) }

    if (!seller) {
        LaunchedEffect(Unit) {
            appointmentViewModel.fetchUserAppointments(authId)
        }

        val appointments by appointmentViewModel.appointments.collectAsState()

        AppointmentScreen(
            appointments = appointments,
            seller = false,
            dualRole = false,
            onSwitch = { sellerRole.value = !sellerRole.value },
            onViewAppointment = {},
            onCancelAppointment = onCancelAppointment,
        )
    } else {
        if (sellerRole.value) {

            LaunchedEffect(Unit) {
                appointmentViewModel.fetchAppointments(authId)
            }

            val appointments by appointmentViewModel.appointments.collectAsState()

            AppointmentScreen(
                appointments = appointments,
                seller = true,
                dualRole = true,
                onSwitch = { sellerRole.value = !sellerRole.value },
                onViewAppointment = onViewAppointment,
                onCancelAppointment = onCancelAppointment
            )
        } else {

            LaunchedEffect(Unit) {
                appointmentViewModel.fetchUserAppointments(authId)
            }

            val appointments by appointmentViewModel.appointments.collectAsState()

            AppointmentScreen(
                appointments = appointments,
                seller = false,
                dualRole = true,
                onSwitch = { sellerRole.value = !sellerRole.value },
                onViewAppointment = {},
                onCancelAppointment = onCancelAppointment,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(
    appointments: List<AppointmentModel>,
    seller: Boolean,
    dualRole: Boolean,
    onSwitch: () -> Unit,
    onViewAppointment: (AppointmentModel) -> Unit,
    onCancelAppointment: (String) -> Unit
) {
    Scaffold(
        topBar = {
            OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "My Appointments",
                            fontWeight = FontWeight.SemiBold
                        )

                        if (dualRole) {
                            TextButton(
                                onClick = { onSwitch() },
                            ) {
                                Text(
                                    text = if (seller) "As a User" else "As a Seller",
                                    color = colorResource(R.color.puurple),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
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
            if (seller) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(appointments) { appointment ->
                        SellerAppointmentCard(
                            appointment = appointment,
                            onViewClick = { onViewAppointment(appointment) }
                        )
                    }
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
                            onViewClick = { appointmentId -> onCancelAppointment(appointmentId) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Seller View With Appointments")
@Composable
private fun SellerViewPreview() {
    val previewAppointments = listOf(
        AppointmentModel(
            patientName = "John Doe",
//            date = Timestamp(Date()),
            time = "10:00 AM",
            status = "Confirmed"
        ),
        AppointmentModel(
            patientName = "Jane Smith",
//            date = Timestamp(Date()),
            time = "11:00 AM",
            status = "Pending"
        )
    )

    AppointmentScreen(
        appointments = previewAppointments,
        seller = true,      // Viewing as a seller
        dualRole = true,    // Show the switch button
        onSwitch = {},
        onViewAppointment = {},
        onCancelAppointment = {}
    )
}
