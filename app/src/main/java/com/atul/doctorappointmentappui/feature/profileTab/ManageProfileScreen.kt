package com.atul.doctorappointmentappui.feature.profileTab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.feature.profileTab.components.ProfileOptionCard

@Composable
fun ManageProfileScreen(
    onUserSelected: () -> Unit,
    onDoctorSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Choose Profile Type",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            // USER PROFILE OPTION
            ProfileOptionCard(
                title = "User Profile",
                icon = Icons.Default.Person,
                onClick = onUserSelected
            )

            // SELLER PROFILE OPTION
            ProfileOptionCard(
                title = "Doctor Profile",
                icon = Icons.Default.LocalHospital,
                onClick = onDoctorSelected
            )
        }
    }
}