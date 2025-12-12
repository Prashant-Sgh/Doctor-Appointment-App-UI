package com.atul.doctorappointmentappui.feature.manageAccount.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun GenderSelectionRow(
    isMale: Boolean,
    isEditable: Boolean,
    onGenderSelected: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Match standard TextField height
            .clip(RoundedCornerShape(4.dp)) // Match TextField default corners
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Male Button
        GenderOption(
            text = "Male",
            icon = Icons.Default.Male,
            isSelected = isMale,
            isEditable = isEditable,
            modifier = Modifier.weight(1f),
            onClick = { onGenderSelected(true) }
        )

        // Female Button
        GenderOption(
            text = "Female",
            icon = Icons.Default.Female,
            isSelected = !isMale,
            isEditable = isEditable,
            modifier = Modifier.weight(1f),
            onClick = { onGenderSelected(false) }
        )
    }
}