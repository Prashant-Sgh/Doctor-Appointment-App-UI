package com.atul.doctorappointmentappui.feature.manageAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.feature.manageAccount.components.AccountHeader
import com.atul.doctorappointmentappui.feature.manageAccount.components.DetailItem
import com.atul.doctorappointmentappui.feature.manageAccount.components.UserPicture

@Composable
fun ManageAccountScreen() {

    val userImage = "https://f4.bcbits.com/img/a3174902089_10.jpg"
    val userName = "Atul Singh"
    val userAge = "20"
    val userPhone = "+1 325 458 497"
    val userEmail = "example@gmail.com"
    val userAddress = "Down town, London"
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
            DetailItem("Email Id", userPhone, editable, onValueChange = {})
            DetailItem("User Address", userEmail, editable, onValueChange = {})
            DetailItem("User Address", userAddress, editable, onValueChange = {})
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