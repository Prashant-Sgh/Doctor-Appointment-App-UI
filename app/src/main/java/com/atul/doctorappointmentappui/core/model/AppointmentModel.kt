package com.atul.doctorappointmentappui.core.model

data class AppointmentModel(
    val appointmentId: String = "",
    val patientName: String = "Unknown User",
    val problemDescription: String = "General Checkup",
    val date: String = "--/--/----",
    val time: String = "--:--",
    val status: String = "PENDING", // PENDING, CONFIRMED, COMPLETED, CANCELLED
    val patientImage: String = "" // Optional URL
)


