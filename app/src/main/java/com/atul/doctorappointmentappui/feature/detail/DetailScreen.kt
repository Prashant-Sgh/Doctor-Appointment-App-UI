//package com.atul.doctorappointmentappui.feature.detail
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.atul.doctorappointmentappui.core.model.DoctorModel
//
//@Composable
//fun DetailScreen(
//    item: DoctorModel,
//    onBack: () -> Unit,
//    onOpenWebsite: (String) -> Unit,
//    onSendSms: (mobile: String, body: String) -> Unit,
//    onDial: (mobile: String) -> Unit,
//    onDirection: (String) -> Unit,
//    onShare: (subject: String, text: String) -> Unit
//) {
//    Column (
//        Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    ) {
//        Box(
//            Modifier.fillMaxWidth()
//        ) {
//            DetailHeader(pictureUrl = item.picture, onBack = onBack)
//            Surface(
//                color = Color.White,
//                shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 400.dp)
//            ) {
//                DetailBody(
//                    item = item,
//                    onOpenWebsite = onOpenWebsite,
//                    onSendSms = onSendSms,
//                    onDial = onDial,
//                    onDirection =onDirection,
//                    onShare = onShare
//                )
//            }
//        }
//    }
//}
//
//
//@Preview
//@Composable
//private fun Preview() {
//    DetailScreen(DoctorModel(), {}, {}, {s,d ->}, {}, {}, {f,j ->})
//}


package com.atul.doctorappointmentappui.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.feature.detail.components.AppointmentBookingSheetContent

// --- 1. The Stateful Route (The one you navigate to) ---
@Composable
fun DetailScreenRoute(
//    viewModel: DetailViewModel = hiltViewModel(),
    // Replace these with your actual navigation actions
    doctor: DoctorModel,
    currentUserData: UserModel,
    onBookAppointment: (AppointmentModel) -> Unit,
    onBack: () -> Unit,
    onOpenWebsite: (String) -> Unit,
    onSendSms: (mobile: String, body: String) -> Unit,
    onDial: (mobile: String) -> Unit,
    onDirection: (String) -> Unit,
    onShare: (subject: String, text: String) -> Unit,
) {
    // In a real app, you would fetch the doctor details based on a navigation argument
    val doctor = DoctorModel(name = "Dr. Emily Carter") // Using a placeholder with a name

    // Collect all necessary state from the ViewModel
//    val isSheetVisible by viewModel.isSheetVisible.collectAsState()
//    val problemDescription by viewModel.problemDescription.collectAsState()
//    val patientName by viewModel.patientName.collectAsState()
//    val bookingDate by viewModel.selectedDateTime.collectAsState()

    DetailScreen(
        item = doctor,
//        isSheetVisible = isSheetVisible,
        // Pass all the new data down to the stateless UI
        currentUserData = currentUserData,
//        bookingDate = Date(),
//        problemDescription = problemDescription,
//        onProblemDescriptionChange = viewModel::onProblemDescriptionChange,
//        onConfirmAppointment = { viewModel.onConfirmAppointment(doctor) },
//        onDismissBottomSheet = viewModel::onDismissBottomSheet,
//        onBookAppointmentClick = viewModel::onBookAppointmentClick,
        onBookAppointment = onBookAppointment,
        onBack = onBack,
        onOpenWebsite = onOpenWebsite,
        onSendSms = onSendSms,
        onDial = onDial,
        onDirection = onDirection,
        onShare = onShare
    )
}

// --- 2. The Stateless UI (Now testable and previewable) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    item: DoctorModel,
    currentUserData: UserModel,
    // Add the new parameters required by the bottom sheet
//    isSheetVisible: Boolean,
//    bookingDate: Date,
//    problemDescription: String,
//    onProblemDescriptionChange: (String) -> Unit,
    onBookAppointment: (AppointmentModel) -> Unit,
    onBack: () -> Unit,
    onOpenWebsite: (String) -> Unit,
    onSendSms: (mobile: String, body: String) -> Unit,
    onDial: (mobile: String) -> Unit,
    onDirection: (String) -> Unit,
    onShare: (subject: String, text: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isSheetVisible = remember { mutableStateOf(false) }
    var problemDescription by remember { mutableStateOf("") }

    // Show the bottom sheet when the state is true
    if (isSheetVisible.value) {
        ModalBottomSheet(
//            onDismissRequest = onDismissBottomSheet,
            onDismissRequest = { isSheetVisible.value = false },
            sheetState = sheetState,
        ) {
            // Pass all the required data to the redesigned sheet content
            AppointmentBookingSheetContent(
                patientName = currentUserData.userName,
                userId = currentUserData.userId,
                doctorName = item.name,
                doctorId = item.id,
//                date = appointmentDetails.bookingDate,
                problemDescription = problemDescription,
                onProblemDescriptionChange = { desc -> problemDescription = desc },
                onConfirmClick = { appoData -> onBookAppointment(appoData) }
            )
        }
    }

    Scaffold(
        // Add a floating action button or a button at the bottom
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
//                    onClick = onBookAppointmentClick,
                    onClick = { isSheetVisible.value = !isSheetVisible.value },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Book Appointment")
                }
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues) // Use padding from Scaffold
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                Modifier.fillMaxWidth()
            ) {
                DetailHeader(pictureUrl = item.picture, onBack = onBack)
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 400.dp)
                ) {
                    DetailBody(
                        item = item,
                        onOpenWebsite = onOpenWebsite,
                        onSendSms = onSendSms,
                        onDial = onDial,
                        onDirection = onDirection,
                        onShare = onShare,
                        onBookAppointmentClick = { isSheetVisible.value = !isSheetVisible.value }
                    )
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun DetailScreenPreview() {
//    MaterialTheme {
//        DetailScreen(
//            item = DoctorModel(name = "Dr. Atul Kumar"),
////            isSheetVisible = false, // Preview with sheet hidden
//            patientName = "John Doe",
////            bookingDate = Date(),
////            problemDescription = "",
////            onProblemDescriptionChange = {},
////            onConfirmAppointment = {},
////            onDismissBottomSheet = {},
//            onBookAppointment = {},
//            onBack = {},
//            onOpenWebsite = {},
//            onSendSms = { _, _ -> },
//            onDial = {},
//            onDirection = {},
//            onShare = { _, _ -> }
//        )
//    }
//}
