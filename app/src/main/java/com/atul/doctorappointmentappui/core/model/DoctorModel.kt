package com.atul.doctorappointmentappui.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorModel(
    val Id: Int = 0,
    val Name: String = "Unknown",
    val Address: String = "Address",
    val Biography: String = "Biography",
    val Expriense: Int = 0,
    val Location: String = "Location",
    val Mobile: String = "+11 23456456789",
    val Patiens: String = "500+",
    val Picture: String = "",
    val Rating: Double = 0.0,
    val Site: String = "Website",
    val Special: String = "Speciality"
): Parcelable
