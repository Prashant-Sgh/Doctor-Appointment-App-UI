package com.atul.doctorappointmentappui.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.core.model.DoctorModel

@Composable
fun DetailScreen(
    item: DoctorModel,
    onBack: () -> Unit,
    onOpenWebsite: (String) -> Unit,
    onSendSms: (mobile: String, body: String) -> Unit,
    onDial: (mobile: String) -> Unit,
    onDirection: (String) -> Unit,
    onShare: (subject: String, text: String) -> Unit
) {
    Column (
        Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            Modifier.fillMaxWidth()
        ) {
            DetailHeader(pictureUrl = item.picture, onBack = onBack)
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 400.dp)
            ) {
                DetailBody(
                    onOpenWebsite = onOpenWebsite,
                    onSendSms = onSendSms,
                    onDial = onDial,
                    onDirection =onDirection,
                    onShare = onShare
                )
            }
        }
    }
}