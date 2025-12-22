package com.atul.doctorappointmentappui.core.model


data class UserModel(
    val userId:String = "",
    val address: String = "",
    val age: String = "",
    val email: String = "",
    val imageURL: String = "",
    val male: Boolean = true,
    val phone: String = "",
    val totalAppointments: Int = 0,
    val userName: String = "",
    val seller: Boolean = false,
    var profileCompleted: Boolean = false
) {

    /**
     * Returns true if all essential fields are filled in.
     * Since placeholders are now "", we only check isNotBlank().
     */
    val isProfileInformationFilled: Boolean
        get() = address.isNotBlank() &&
                age.isNotBlank() &&
                email.isNotBlank() &&
                phone.isNotBlank() &&
                userName.isNotBlank() &&
                imageURL.isNotBlank()
}
