package com.atul.doctorappointmentappui.feature.topDoctors.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.DoctorModel

@Composable
fun DoctorsRowCard(
    item: DoctorModel,
    onMakeClick: (DoctorModel) -> Unit
) {
    val lightPurple = colorResource(R.color.lightPuurple)
    val darkPurple = colorResource(R.color.darkPuurple)
    val gray = colorResource(R.color.ggray)
    val purple = colorResource(R.color.black)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors( contentColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()){
            Column (
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth()
                ) {
                    Box(
                        Modifier
                            .size(96.dp)
                            .background(lightPurple, RoundedCornerShape(10.dp))
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(item.Picture).crossfade(true).build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        DegreeChip("Professional Doctor")
                        Spacer(Modifier.height(8.dp))
                        Text(item.Name, color =Color.Black, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(item.Special, color = gray)

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            ComposeRatingBar(rating = item.Rating.toFloat() )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                item.Rating.toString(),
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                OutlinedButton(
                    onClick = { onMakeClick(item) },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lightPurple,
                        contentColor = darkPurple
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 1.dp,
                        brush = SolidColor(darkPurple)
                    )
                ) {
                    Text(text = "Make Appointment", fontWeight = FontWeight.Bold)
                }
            }
            IconButton(
                onClick = {},
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
            ) {
                Icon(
                    painterResource(R.drawable.fav_bold),
                    null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}