package com.atul.doctorappointmentappui.feature.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailBody(
    onOpenWebsite: (String) -> Unit,
    onSendSms: (mobile: String, body: String) -> Unit,
    onDial: (mobile: String) -> Unit,
    onDirection: (String) -> Unit,
    onShare: (subject: String, text: String) -> Unit
) {

}