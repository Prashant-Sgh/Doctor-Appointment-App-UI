package com.atul.doctorappointmentappui.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorModel(
    val id: Int = 0,
    val name: String = "Unknown",
    val address: String = "Address",
    val biography: String = "Biography",
    val experience: Int = 0,
    val location: String = "Location",
    val phone: String = "+11 23456456789",
    val patients: Int = 500,
    val picture: String = "https://avatarfiles.alphacoders.com/375/thumb-1920-375364.png",
    val rating: Double = 0.0,
    val site: String = "Website",
    val special: String = "Speciality"
): Parcelable
