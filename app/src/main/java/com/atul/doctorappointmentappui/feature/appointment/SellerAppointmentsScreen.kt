package com.atul.doctorappointmentappui.feature.appointment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.viewmodel.AppointmentViewModel
import com.atul.doctorappointmentappui.feature.appointment.Components.SellerAppointmentScaffold

@Composable
fun SellerAppointmentsScreen(
    viewModel: AppointmentViewModel = hiltViewModel(),
    doctorId: String,
    onViewAppointment: (AppointmentModel) -> Unit
) {

    // 1. Fetch data when screen opens
    LaunchedEffect(doctorId) {
        viewModel.fetchAppointments(doctorId)
    }

    // 2. Observe the state
    val appointments by viewModel.appointments.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // 3. Pass data to your UI
    if (isLoading && appointments.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = colorResource(R.color.puurple))
        }
    }
    SellerAppointmentScaffold(appointments) { onViewAppointment }
}

// --- Preview ---
//@Preview
//@Composable
//fun SellerAppointmentsScreenPreview() {
//    val dummyList = listOf(
//        AppointmentModel(
//            patientName = "Alice Smith",
//            date = "Oct 24, 2023",
//            time = "10:00 AM",
//            status = "PENDING",
//            problemDescription = "High Fever and chills"
//        ),
//        AppointmentModel(
//            patientName = "Bob Jones",
//            date = "Oct 24, 2023",
//            time = "11:30 AM",
//            status = "CONFIRMED",
//            problemDescription = "Regular Checkup"
//        ),
//        AppointmentModel(
//            patientName = "Charlie Day",
//            date = "Oct 25, 2023",
//            time = "09:00 AM",
//            status = "CANCELLED",
//            problemDescription = "Stomach Ache"
//        )
//    )
//
//    SellerAppointmentsScreen(
//        appointments = dummyList,
//        onViewAppointment = {}
//    )
//}