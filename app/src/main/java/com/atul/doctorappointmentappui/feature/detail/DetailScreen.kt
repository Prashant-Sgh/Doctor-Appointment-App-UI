package com.atul.doctorappointmentappui.feature.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.atul.doctorappointmentappui.core.model.DoctorModel

@Composable
fun DetailScreen(
    item: DoctorModel,
    onBack: () -> Unit,
    onOpenWebsite: (String) -> Unit,
    onSendSms: (mobile: String, body: String) -> Unit,
    onDial: (mobile: String) -> Unit,
) {

}