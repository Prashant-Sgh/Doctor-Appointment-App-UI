package com.atul.doctorappointmentappui.feature.appointment

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.viewmodel.AppointmentViewModel
import com.atul.doctorappointmentappui.core.viewmodel.SellerDataViewModel
import com.atul.doctorappointmentappui.core.viewmodel.UserDataViewModel
import com.atul.doctorappointmentappui.feature.appointment.Components.SellerAppointmentScaffold

// 1. The "Connected" Composable (Has ViewModel logic)
@Composable
fun SellerAppointmentsScreen(
    appointmentViewModel: AppointmentViewModel,
    sellerDataViewModel: SellerDataViewModel = hiltViewModel(),
    onViewAppointment: (AppointmentModel) -> Unit
) {

    val doctorId by sellerDataViewModel.sellerUid.collectAsState()

    // 1. Fetch data when screen opens
    LaunchedEffect(doctorId) {
        appointmentViewModel.fetchAppointments(doctorId)
    }

    // 2. Observe the state
    val appointments by appointmentViewModel.appointments.collectAsState()
    val isLoading by appointmentViewModel.isLoading.collectAsState()

    // 3. Delegate to the Pure UI Composable
    SellerAppointmentsContent(
        appointments = appointments,
        isLoading = isLoading,
        onViewAppointment = onViewAppointment
    )
}

// 2. The "Pure" UI Composable (Easy to Preview, no Hilt)
@Composable
fun SellerAppointmentsContent(
    appointments: List<AppointmentModel>,
    isLoading: Boolean,
    onViewAppointment: (AppointmentModel) -> Unit
) {
    if (isLoading && appointments.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = colorResource(R.color.puurple))
        }
    } else {
        SellerAppointmentScaffold(
            appointments = appointments,
            onViewAppointment = { onViewAppointment(it) }
        )
    }
}

// 3. The Preview
//@Preview(showBackground = true)
//@Composable
//fun SellerAppointmentsScreenPreview() {
//    // Create dummy data
//    val dummyAppointments = listOf(
//        AppointmentModel(
//            patientName = "John Doe",
//            date = "2023-10-27",
//            time = "10:00 AM",
//            status = "PENDING",
//            problemDescription = "Fever and headache"
//        ),
//        AppointmentModel(
//            patientName = "Jane Smith",
//            date = "2023-10-27",
//            time = "11:30 AM",
//            status = "CONFIRMED",
//            problemDescription = "Dental checkup"
//        )
//    )
//
//    // Render the Pure UI composable
//    SellerAppointmentsContent(
//        appointments = dummyAppointments,
//        isLoading = false,
//        onViewAppointment = {}
//    )
//}