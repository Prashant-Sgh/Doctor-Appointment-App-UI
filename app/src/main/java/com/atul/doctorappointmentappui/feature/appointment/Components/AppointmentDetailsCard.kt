package com.atul.doctorappointmentappui.feature.appointment.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.feature.appointment.DetailItem
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AppointmentDetailsCard(appointment: AppointmentModel) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        modifier = Modifier.fillMaxWidth()
    ) {

        val javaDate = remember(appointment.date) {
            appointment.date?.toDate()
        }

        val dateString = remember(javaDate) {
            if (javaDate != null) {
                SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(javaDate)
            } else {
                "--/--/----"
            }
        }

        val timeString = remember(javaDate) {
            if (javaDate != null) {
                SimpleDateFormat("hh:mm a", Locale.getDefault()).format(javaDate)
            } else {
                "--:--"
            }
        }

//        val dateString = remember(appointment.date) {
//            appointment.date?.toDate()?.let {
//                // 'it' is now a java.util.Date object
//                SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(it)
//            } ?: "--/--/----" // Fallback if date is null
//        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailItem(Icons.Default.CalendarToday, "Date", dateString)
            Divider()
            DetailItem(Icons.Default.Schedule, "Time", timeString)
            Divider()
            DetailItem(Icons.Default.Info, "Problem", appointment.problemDescription, isProblem = true)
        }
    }
}