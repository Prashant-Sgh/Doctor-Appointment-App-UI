package com.atul.doctorappointmentappui.core.model

//data class DoctorModel (
//    val Id: Int = 0,
//    val Name: String = "",
//    val Specialization: String = "",
//    val Experience: Int = 0,
//    val Picture: String = "",
//    val Rating: Double = 0.0,
//)

data class DoctorModel(
    val id: Int = 0,
    val name: String = "",
    val address: String = "",
    val biography: String = "",
    val experience: Int = 0,
    val location: String = "",
    val mobile: String = "",
    val patients: String = "",
    val picture: String = "",
    val rating: Double = 0.0,
    val site: String = "",
    val specialty: String = ""
)
