package com.atul.doctorappointmentappui.feature.manageAccount.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SignOut(onSignOut: () -> Unit) {
    TextButton(
        onClick = onSignOut,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary // Blue text
        )
    ) {
        Icon(
            imageVector = Icons.Default.Logout,
            contentDescription = "Sign Out"
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text("Sign out")
    }
}


@Preview
@Composable
private fun Preview() {
    SignOut {  }
}