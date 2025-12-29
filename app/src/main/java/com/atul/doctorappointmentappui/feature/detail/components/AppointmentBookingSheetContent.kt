package com.atul.doctorappointmentappui.feature.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AppointmentBookingSheetContent(
    patientName: String,
    userId: String,
    doctorName: String,
    doctorId: String,
    date: Date = Date(),
    problemDescription: String,
    preBookedSlots: List<String>, // New parameter
    onProblemDescriptionChange: (String) -> Unit,
    onConfirmClick: (AppointmentModel) -> Unit,
    modifier: Modifier = Modifier
) {
    // --- STATE MANAGEMENT ---
    val formattedDate = rememberFormattedDate(date)

    // Generate all possible time slots for the day
    val allTimeSlots = remember { generateTimeSlots() }

    // Find the first available slot to be selected by default
    val firstAvailableSlot = remember(allTimeSlots, preBookedSlots) {
        allTimeSlots.firstOrNull { it !in preBookedSlots } ?: ""
    }

    // State to hold the currently selected time slot
    var selectedTimeSlot by remember { mutableStateOf(firstAvailableSlot) }

    val timeString = remember (date) {
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date)
    }

    // --- UI ---
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

        // --- 2. TIME SLOT GRID (New Component) ---
        TimeSlotGrid(
            allSlots = allTimeSlots,
            bookedSlots = preBookedSlots,
            selectedSlot = selectedTimeSlot,
            onSlotSelected = { newSlot -> selectedTimeSlot = newSlot }
        )

        // --- 3. PRE-FILLED INFO CHIPS ---
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            InfoChip(icon = Icons.Default.Person, label = "Patient", value = patientName)
            InfoChip(icon = Icons.Default.PersonPin, label = "Doctor", value = doctorName)
            InfoChip(icon = Icons.Default.CalendarToday, label = "Date", value = formattedDate)
        }

        // --- 4. EDITABLE PROBLEM DESCRIPTION ---
        OutlinedTextField(
            value = problemDescription,
            onValueChange = onProblemDescriptionChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Describe the reason for your visit") },
            placeholder = { Text("e.g., General checkup, fever, etc.") },
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- 5. CONFIRM BUTTON ---
        Button(
            onClick = {
                onConfirmClick(
                    AppointmentModel(
                        doctorId = doctorId,
                        userId = userId,
                        patientName = patientName,
                        problemDescription = problemDescription,
                        timeSlot = selectedTimeSlot,
                        time = timeString, // Use the selected time slot
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            // The button is only enabled when a time is selected and a problem is described
            enabled = problemDescription.isNotBlank() && selectedTimeSlot.isNotBlank()
        ) {
            Text("Confirm Appointment")
        }
    }
}

/**
 * Displays a grid of selectable time slots.
 */
@Composable
private fun TimeSlotGrid(
    allSlots: List<String>,
    bookedSlots: List<String>,
    selectedSlot: String,
    onSlotSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Select a Time Slot",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            // The grid itself shouldn't scroll; the parent column will
            modifier = Modifier
                .fillMaxWidth()
                .height(213.dp) // Constrain height to show a few rows
        ) {
            items(allSlots) { slot ->
                val isBooked = slot in bookedSlots
                val isSelected = slot == selectedSlot
                TimeSlotChip(
                    time = slot,
                    isBooked = isBooked,
                    isSelected = isSelected,
                    onCLick = { onSlotSelected(slot) }
                )
            }
        }
    }
}

/**
 * A single chip representing a time slot.
 */
@Composable
private fun TimeSlotChip(
    time: String,
    isBooked: Boolean,
    isSelected: Boolean,
    onCLick: () -> Unit
) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primary
        isBooked -> MaterialTheme.colorScheme.surfaceContainerHigh
        else -> MaterialTheme.colorScheme.surface
    }
    val contentColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimary
        isBooked -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        else -> MaterialTheme.colorScheme.onSurface
    }
    val borderColor = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(5.dp))
            .clickable(enabled = !isBooked, onClick = onCLick)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            color = contentColor,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Generates a list of 30-minute time slots from 09:00 AM to 03:00 PM.
 */
private fun generateTimeSlots(): List<String> {
    val slots = mutableListOf<String>()
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 9)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }
    val endTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 15)
        set(Calendar.MINUTE, 0)
    }
    val formatter = SimpleDateFormat("hh:mm a", Locale.US)

    while (calendar.before(endTime)) {
        slots.add(formatter.format(calendar.time))
        calendar.add(Calendar.MINUTE, 30)
    }
    return slots
}

// --- Other existing components (InfoChip, rememberFormattedDate) remain the same ---

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

@Composable
private fun rememberFormattedDate(date: Date, pattern: String = "EEEE, MMMM dd, yyyy"): String {
    return remember(date, pattern) {
        SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }
}


// --- UPDATED PREVIEWS ---

@Preview(showBackground = true, name = "Form Filled")
@Composable
private fun AppointmentBookingSheetContentPreview() {
    MaterialTheme {
        AppointmentBookingSheetContent(
            doctorId = "0",
            userId = "0",
            patientName = "Atul Singh",
            doctorName = "Dr. Emily Carter",
            problemDescription = "Feeling a bit under the weather.",
            preBookedSlots = listOf("09:30 AM", "11:00 AM", "01:00 PM"), // Example of booked slots
            onProblemDescriptionChange = {},
            onConfirmClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Form Empty (Button Disabled)")
@Composable
private fun AppointmentBookingSheetContentEmptyPreview() {
    MaterialTheme {
        AppointmentBookingSheetContent(
            doctorId = "0",
            userId = "0",
            patientName = "Atul Singh",
            doctorName = "Dr. Emily Carter",
            problemDescription = "", // Problem is empty
            preBookedSlots = listOf("09:30 AM", "11:00 AM"),
            onProblemDescriptionChange = {},
            onConfirmClick = {}
        )
    }
}
