package com.atul.doctorappointmentappui.feature.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.model.UserAppointmentDetails
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AppointmentBookingSheetContent(
    patientName: String,
    userId: String,
    doctorName: String,
    doctorId: Int,
    date: Date = Date(),
    problemDescription: String,
    onProblemDescriptionChange: (String) -> Unit,
    onConfirmClick: (AppointmentModel) -> Unit,
    modifier: Modifier = Modifier
) {
    // Format the date for display
    val formattedDate = rememberFormattedDate(date)

    val timeString = remember (date) {
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // --- 1. HEADER ---
        Text(
            text = "Confirm Your Details",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        // --- 2. PRE-FILLED INFO CHIPS ---
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            InfoChip(icon = Icons.Default.Person, label = "Patient", value = patientName)
            InfoChip(icon = Icons.Default.PersonPin, label = "Doctor", value = doctorName)
            InfoChip(icon = Icons.Default.CalendarToday, label = "Date", value = formattedDate)
        }

        // --- 3. EDITABLE PROBLEM DESCRIPTION ---
        OutlinedTextField(
            value = problemDescription,
            onValueChange = onProblemDescriptionChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Describe the reason for your visit") },
            placeholder = { Text("e.g., General checkup, fever, etc.") },
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- 4. CONFIRM BUTTON (conditionally enabled) ---
        Button(
            onClick = {
                onConfirmClick(
                    AppointmentModel(
                        doctorId = doctorId.toString(),
                        userId = userId,
                        patientName = patientName,
                        problemDescription = problemDescription,
                        time = timeString,
                    ))
            },
            modifier = Modifier.fillMaxWidth(),
            // The button is only enabled when the problem description is not blank
            enabled = problemDescription.isNotBlank()
        ) {
            Text("Confirm Appointment")
        }
    }
}

/**
 * A visually appealing chip to display non-editable information.
 */
@Composable
private fun InfoChip(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "$label:",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * A helper composable to remember the formatted date string, avoiding recalculation.
 */
@Composable
private fun rememberFormattedDate(date: Date, pattern: String = "EEEE, MMMM dd, yyyy"): String {
    return remember(date, pattern) {
        SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }
}

// --- PREVIEWS for TESTABILITY ---

//@Preview(showBackground = true, name = "Form Filled")
//@Composable
//private fun AppointmentBookingSheetContentPreview() {
//    MaterialTheme {
//        AppointmentBookingSheetContent(
//            doctorId = 0,
//            patientName = "Atul Singh",
//            doctorName = "Dr. Emily Carter",
////            date = Date(), // Today's date
//            problemDescription = "Feeling a bit under the weather.",
//            onProblemDescriptionChange = {},
//            onConfirmClick = {}
//        )
//    }
//}

//@Preview(showBackground = true, name = "Form Empty (Button Disabled)")
//@Composable
//private fun AppointmentBookingSheetContentEmptyPreview() {
//    MaterialTheme {
//        AppointmentBookingSheetContent(
//            patientName = "Atul Singh",
//            doctorName = "Dr. Emily Carter",
////            date = Date(), // Today's date
//            problemDescription = "", // Problem is empty
//            onProblemDescriptionChange = {},
//            onConfirmClick = {}
//        )
//    }
//}
