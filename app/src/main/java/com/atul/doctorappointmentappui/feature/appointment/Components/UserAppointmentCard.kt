package com.atul.doctorappointmentappui.feature.appointment.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun UserAppointmentCard(
    appointment: AppointmentModel,
    onViewClick: (String) -> Unit
) {
//    val userData by userDataFlow.collectAsState()

    val javaDate = remember(appointment.date) {
        appointment.date?.toDate()
    }

    val dateString = remember (javaDate) {
        if (javaDate != null) {
            SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(javaDate)
        } else {
            "--/--/----"
        }
    }

    val timeString = remember (javaDate) {
        if (javaDate != null) {
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(javaDate)
        } else {
            "--:--"
        }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // --- Header: Name and Status ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile Placeholder / Image
                Surface(
                    shape = CircleShape,
                    color = colorResource(R.color.lightPuurple),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = colorResource(R.color.puurple)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = appointment.patientName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = appointment.problemDescription,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(R.color.darkGgray),
                        maxLines = 1
                    )
                }

                StatusChip(status = appointment.status)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Info Row: Date & Time ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(R.color.lightPuurple).copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoItem(
                    icon = Icons.Default.CalendarToday,
                    text = dateString
                )

                // Vertical Divider
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                        .background(colorResource(R.color.ggray).copy(alpha = 0.5f))
                )

                InfoItem(
                    icon = Icons.Default.Schedule,
                    text = timeString
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Action Button ---
            Button(
                onClick = { onViewClick(appointment.appointmentId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.puurple)
                )
            ) {
                Text(
                    text = "Cancel Appointment",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

//// --- Generated Preview ---
//@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
//@Composable
//private fun UserAppointmentCardPreview() {
//    MaterialTheme {
//        Box(modifier = Modifier.padding(16.dp)) {
//            UserAppointmentCard(
//                appointment = AppointmentModel(
//                    appointmentId = "appt123",
//                    patientName = "Atul Kumar",
//                    problemDescription = "Follow-up after seasonal flu",
//                    status = "CONFIRMED",
//                    date = com.google.firebase.Timestamp.now() // Uses current time for the preview
//                ),
//                onViewClick = { appointmentId ->
//                    println("Clicked on appointment: $appointmentId")
//                }
//            )
//        }
//    }
//}