package com.atul.doctorappointmentappui.feature.manageAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.collectAsState
import com.atul.doctorappointmentappui.feature.manageAccount.components.SignOut

@Composable
fun ManageAccountScreen(
    userDataFlow: StateFlow<UserModel>,
    saveUserData: (UserModel) -> Unit,
    signOutUser: () -> Unit
) {

    val userData by userDataFlow.collectAsState()

    var userImage by remember { mutableStateOf(userData.imageURL) }
    var userName by remember { mutableStateOf(userData.userName) }
    var userAge by remember { mutableStateOf(userData.age) }
    var userPhone by remember { mutableStateOf(userData.phone) }
    var userEmail by remember { mutableStateOf(userData.email) }
    var userAddress by remember { mutableStateOf(userData.address) }
    var totalAppointments by remember { mutableStateOf(userData.totalAppointments) }
    var gender by remember { mutableStateOf(userData.male) }
    var userGender = if(gender) "Male" else "Female"
    var editable by remember { mutableStateOf(false) }

    Column (
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.lightPuurple))
            .statusBarsPadding()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AccountHeader(editable) {
            val newData = (
                UserModel(
                    userAddress,
                    userAge,
                    userEmail,
                    userImage,
                    gender,
                    userPhone,
                    totalAppointments,
                    userName
                )
            )
            editable = false
            saveUserData(newData)
        }
        Spacer(Modifier.height(50.dp))
        UserPicture(userImage)
        SignOut({signOutUser()})
        Spacer(Modifier.height(40.dp))

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            DetailItem("User Name", userName, editable, onValueChange = {userName = it})
            DetailItem("Age", userAge, editable, onValueChange = {userAge = it})
            DetailItem("Phone", userPhone, editable, onValueChange = {userPhone = it})
            DetailItem("Address", userAddress, editable, onValueChange = {userAddress = it})
            DetailItem("Gender", userGender, editable, needToggleValue = true, toggleChange = {gender = !gender}, onValueChange = {})
            DetailItem("Email", userEmail, editable, onValueChange = {userEmail = it})
            DetailItem("Image Url", userImage, editable, onValueChange = {userImage = it})
        }

        Spacer(Modifier.height(50.dp))

        TextButton(
            onClick = {editable = !editable},
            elevation = ButtonDefaults.buttonElevation(3.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.puurple))
        ) {
            Text("Edit")
        }

    }
}

//@Preview
//@Composable
//private fun P() {
//    ManageAccountScreen()
//}