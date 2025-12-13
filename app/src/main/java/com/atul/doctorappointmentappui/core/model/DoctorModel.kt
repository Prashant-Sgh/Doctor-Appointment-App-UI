package com.atul.doctorappointmentappui.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorModel(
    val id: Int = 0,
    val name: String = "",
    val address: String = "",
    val biography: String = "",
    val experience: Int = 0,
    val location: String = "",
    val phone: String = "",
    val patients: Int = 0,
    val picture: String = "",
    val rating: Double = 0.0,
    val site: String = "",
    val special: String = "",
    val profileCompleted: Boolean = false
): Parcelable {
    val isProfileInformationFilled: Boolean
        get() = name.isNotBlank() &&
                address.isNotBlank() &&
                biography.isNotBlank() &&
                location.isNotBlank() &&
                phone.isNotBlank() &&
                site.isNotBlank() &&
                special.isNotBlank() &&
                experience > 0 // Assuming a doctor must have at least 1 year or some input > 0
}
