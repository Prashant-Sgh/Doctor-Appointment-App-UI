package com.atul.doctorappointmentappui.core.model

import com.google.firebase.Timestamp

data class AppointmentModel(
    val appointmentId: String = "",
    val doctorId: String = "",
    val userId: String = "",
    val patientName: String = "",
    val problemDescription: String = "",
    val timeSlot: String = "",
    val date: Timestamp? = null,
    val time: String = "", // Note: Your Firebase structure doesn't have a separate time field. We will need to derive it from the 'date' Timestamp.
    val status: String = "", // PENDING, CONFIRMED, COMPLETED, CANCELLED
    val createdAt: Timestamp = Timestamp.now() // Keep as Timestamp to handle creation date logic if needed
)