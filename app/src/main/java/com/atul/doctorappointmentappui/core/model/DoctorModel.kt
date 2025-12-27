package com.atul.doctorappointmentappui.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorModel(
    val id: String = "",
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
                experience > 0 && // Assuming a doctor must have at least 1 year or some input > 0
                location.isNotBlank() &&
                phone.isNotBlank() &&
                patients >= 0 &&
                picture.isNotBlank() &&
                site.isNotBlank() &&
                special.isNotBlank()
}
