package com.atul.doctorappointmentappui.feature.manageAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.feature.manageAccount.components.AccountHeader
import com.atul.doctorappointmentappui.feature.manageAccount.components.DetailItem
import com.atul.doctorappointmentappui.feature.manageAccount.components.UserPicture
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ManageAccountScreen(userData: UserModel) {

    val userImage = userData.imageURL
    val userName = userData.userName
    val userAge = userData.age
    val userPhone = userData.phone
    val userEmail = userData.email
    val userAddress = userData.address
    val userGender = if(userData.isMale) "Male" else "Female"
    var editable by remember { mutableStateOf(true) }

    Column (
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.lightPuurple))
            .statusBarsPadding()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AccountHeader(editable) {}
        Spacer(Modifier.height(50.dp))
        UserPicture(userImage)
        Spacer(Modifier.height(50.dp))

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            DetailItem("User Name", userName, editable, onValueChange = {})
            DetailItem("Age", userAge, editable, onValueChange = {})
            DetailItem("Phone", userPhone, editable, onValueChange = {})
            DetailItem("Email", userEmail, editable, onValueChange = {})
            DetailItem("Address", userAddress, editable, onValueChange = {})
            DetailItem("Gender", userGender, editable, onValueChange = {})
        }

        Spacer(Modifier.height(50.dp))

        TextButton(onClick = {editable = !editable}) {
            Text("Edit")
        }

    }
}

//@Preview
//@Composable
//private fun P() {
//    ManageAccountScreen()
//}