package com.atul.doctorappointmentappui.feature.profileTab.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: ((String) -> Unit)?,
    onIntValueChange: ((Int) -> Unit)?,
    isError: Boolean = false
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)

    if (onValueChange != null) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = isError,
            modifier = modifier
        )
    } else if (onIntValueChange != null) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                // Only allow numeric input
                if (newValue.all { it.isDigit() }) {
                    // Prevent empty string crash during conversion
                    val intValue = if (newValue.isEmpty()) 0 else newValue.toInt()
                    onIntValueChange(intValue)
                }
            },
            label = { Text(label) },
            isError = isError, // <--- Apply red border
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier
        )
    }
}
