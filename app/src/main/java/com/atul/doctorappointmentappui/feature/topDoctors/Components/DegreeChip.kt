package com.atul.doctorappointmentappui.feature.topDoctors.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.R

@Composable
fun DegreeChip(
    text: String
) {
    val lightPurple = colorResource(R.color.lightPuurple)
    val purple = colorResource(R.color.puurple)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(25.dp)
            .background(lightPurple, RoundedCornerShape(100.dp))
            .padding(horizontal = 8.dp)
    ){
        Image(
            painterResource(R.drawable.tick),
            contentDescription = null
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text,
            color = purple,
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}