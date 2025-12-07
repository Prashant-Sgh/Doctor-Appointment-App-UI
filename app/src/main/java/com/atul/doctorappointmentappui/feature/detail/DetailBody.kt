package com.atul.doctorappointmentappui.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.feature.detail.components.ActionItem
import com.atul.doctorappointmentappui.feature.detail.components.RatingState
import com.atul.doctorappointmentappui.feature.detail.components.StateColumn
import com.atul.doctorappointmentappui.feature.detail.components.VerticalDivider
import kotlin.Int
import kotlin.String

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
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Text(
            item.special,
            color =  Color.Black,
            modifier = Modifier.padding(start = 16.dp)
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
        }
        Row (
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StateColumn(title = "Patient", value = "${item.patients} +")
            VerticalDivider(gray)
            StateColumn(title = "Experience", value = "${item.experience}")
            VerticalDivider(gray)
            RatingState("Rating", item.rating)
        }

        Text(
            "Biography",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            item.biography,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionItem(
                "Direction",
                R.drawable.direction,
                lightPurple,
                !item.site.isNullOrBlank(),
            ) { onDirection(item.location) }
            ActionItem(
                "Website",
                R.drawable.website,
                lightPurple,
                !item.site.isNullOrBlank(),
            ) { onOpenWebsite(item.site) }
            ActionItem(
                "Call",
                R.drawable.call,
                lightPurple,
                !item.site.isNullOrBlank(),
            ) { onDial(item.phone) }
            ActionItem(
                "Message",
                R.drawable.message,
                lightPurple,
                !item.site.isNullOrBlank(),
            ) { onSendSms(item.phone, "${item.special} - ${item.name} - ${item.biography}") }
            ActionItem(
                "Share",
                R.drawable.share,
                lightPurple,
                !item.site.isNullOrBlank(),
            ) {
                val subject = item.name
                val text = "${item.name}, ${item.address}, ${item.phone} "
                onShare(subject, text)
            }
        }
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.darkPuurple),
                contentColor = Color.White
            )
        ) {
            Text(
                "Make Appointment",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}