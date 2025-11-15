package com.atul.doctorappointmentappui.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.feature.detail.components.RatingState
import com.atul.doctorappointmentappui.feature.detail.components.StateColumn
import com.atul.doctorappointmentappui.feature.detail.components.VerticalDivider

@Composable
fun DetailBody(
    item: DoctorModel,
    onOpenWebsite: (String) -> Unit,
    onSendSms: (mobile: String, body: String) -> Unit,
    onDial: (mobile: String) -> Unit,
    onDirection: (String) -> Unit,
    onShare: (subject: String, text: String) -> Unit
) {
    val darkPurple = colorResource(R.color.darkPuurple)
    val gray = colorResource(R.color.ggray)
    val lightPurple = colorResource(R.color.lightPuurple)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            item.name,
            color = Color.Black,
            fontSize = 220.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Text(
            item.specialty,
            color =  Color.Black
        )
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ){
            Image(painter = painterResource(R.drawable.location), contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(
                item.address,
                color = darkPurple,
                modifier = Modifier.weight(1f)
            )

            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StateColumn(title = "Patient", value = item.patients)
                VerticalDivider(gray)
                StateColumn(title = "Experience", value = "${item.experience}")
                VerticalDivider(gray)
                RatingState("Rating", item.rating)
            }

        }
    }

}