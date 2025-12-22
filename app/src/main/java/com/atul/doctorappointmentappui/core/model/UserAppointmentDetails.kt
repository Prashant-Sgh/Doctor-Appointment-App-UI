package com.atul.doctorappointmentappui.core.model

import java.util.Date

data class UserAppointmentDetails (
    val doctorId: Int = 0,
    val patientName: String = "",
    val doctorName: String = "",
    val bookingDate: Date = Date(),
    val problemDescription: String = ""
)