package com.atul.doctorappointmentappui.core.model

data class UserModel(
    val address: String = "    --    ",
    val age: String = "    --    ",
    val email: String = "    --    ",
    val imageURL: String = "    --    ",
    val male: Boolean = true,
    val phone: String = "    --    ",
    val totalAppointments: Int = 0,
    val userName: String = "    --    ",
    val seller: Boolean = false
)
