package com.atul.doctorappointmentappui.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.feature.detail.components.AppointmentBookingSheetContent
import com.atul.doctorappointmentappui.feature.detail.components.AppointmentBookingSheetContentTest
import java.util.Date

/**
 * A stateful composable that holds the logic for the Detail Screen.
 * It manages the bottom sheet state and user interactions.
 */
@Composable
fun DetailScreenRoute(
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
    DetailScreen(
        item = doctor,
        currentUserData = currentUserData,
        onBookAppointment = onBookAppointment,
        onBack = onBack,
        onOpenWebsite = onOpenWebsite,
        onSendSms = onSendSms,
        onDial = onDial,
        onDirection = onDirection,
        onShare = onShare
    )
}

/**
 * A stateless, pure UI composable for the Detail Screen.
 * It displays doctor details and handles the presentation of the booking bottom sheet.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    item: DoctorModel,
    currentUserData: UserModel,
    onBookAppointment: (AppointmentModel) -> Unit,
    onBack: () -> Unit,
    onOpenWebsite: (String) -> Unit,
    onSendSms: (mobile: String, body: String) -> Unit,
    onDial: (mobile: String) -> Unit,
    onDirection: (String) -> Unit,
    onShare: (subject: String, text: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetVisible by remember { mutableStateOf(false) }
    var problemDescription by remember { mutableStateOf("") }

    if (isSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isSheetVisible = false },
            sheetState = sheetState,
        ) {
//            AppointmentBookingSheetContent(
//                patientName = currentUserData.userName,
//                userId = currentUserData.userId,
//                doctorName = item.name,
//                doctorId = item.id,
////                date = Date(), // Pass the current date
//                problemDescription = problemDescription,
//                onProblemDescriptionChange = { newDescription -> problemDescription = newDescription },
//                onConfirmClick = { appointmentData -> onBookAppointment(appointmentData) }
//            )
            AppointmentBookingSheetContentTest(
                patientName = currentUserData.userName,
                userId = currentUserData.userId,
                doctorName = item.name,
                doctorId = item.id,
//                date = Date(), // Pass the current date
                problemDescription = problemDescription,
                preBookedSlots = listOf("09:30 AM", "11:00 AM", "01:00 PM"),
                onProblemDescriptionChange = { newDescription -> problemDescription = newDescription },
                onConfirmClick = { appointmentData -> onBookAppointment(appointmentData) }
            )

        }
    }

    Scaffold { paddingValues ->
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
                    shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
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
                        onBookAppointmentClick = { isSheetVisible = true }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    MaterialTheme {
        DetailScreen(
            item = DoctorModel(id = "0", name = "Dr. Atul Kumar", picture = ""),
            currentUserData = UserModel(userId = "user1", userName = "John Doe"),
            onBookAppointment = {},
            onBack = {},
            onOpenWebsite = {},
            onSendSms = { _, _ -> },
            onDial = {},
            onDirection = {},
            onShare = { _, _ -> }
        )
    }
}
