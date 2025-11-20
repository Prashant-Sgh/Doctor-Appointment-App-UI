package com.atul.doctorappointmentappui.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.core.model.DoctorModel

@Composable
fun DoctorRow(
    items: List<DoctorModel>,
    onClick: (DoctorModel) -> Unit
) {
    Box(
        modifier = Modifier
//            .padding(top = 16.dp)
            .fillMaxWidth()
            .heightIn(min = 260.dp)
    ) {
        if (items.isEmpty()) {
            CircularProgressIndicator()
        }
        else {
            LazyRow(
                contentPadding = PaddingValues(16.dp),
            ) {
                items(items) {item ->
                    DoctorCard(item) { onClick(item) }
                }
            }
        }
    }
}