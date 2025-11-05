package com.atul.doctorappointmentappui.feature.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atul.doctorappointmentappui.R

@Composable
fun SectionHeader(title: String, onSeeAll: (() -> Unit)?) {
    Row (
        Modifier.fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
                .padding(16.dp)
        )
        if (onSeeAll != null) {
            TextButton(onClick = onSeeAll) {
                Text(
                    "See all",
                    color = colorResource(R.color.darkPuurple)
                    )
            }
        }
        else {
            Text(
                "See all",
                color = colorResource(R.color.darkPuurple)
            )
        }
    }
}