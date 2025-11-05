package com.atul.doctorappointmentappui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.R

@Composable
fun Banner() {
    Box(
        Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ){
        Image(
            painter = painterResource(R.drawable.banner),
            contentDescription = "Banner",
            contentScale = ContentScale.FillBounds
        )

    }
}