package com.atul.doctorappointmentappui.feature.home

import android.R.attr.fontWeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atul.doctorappointmentappui.R

@Composable
fun HomeHeader() {
    Row(
        Modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {

            Text("Hi, Edward Greek",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text("How are you doing today?",
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
            Image(
                painter = painterResource(R.drawable.bell_icon),
                contentDescription = "Bell icon",
                modifier = Modifier.size(42.dp)
            )

        }
    }
}