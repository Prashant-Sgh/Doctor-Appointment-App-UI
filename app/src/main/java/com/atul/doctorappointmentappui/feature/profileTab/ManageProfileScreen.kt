package com.atul.doctorappointmentappui.feature.profileTab

import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.feature.profileTab.components.ProfileOptionCard
import kotlinx.coroutines.delay

@Composable
fun ManageProfileScreen(
    onOpenUserProfile: () -> Unit,
    onOpenDrProfile: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest) // A subtle background color
    ) {
        // --- HEADER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.ManageAccounts,
                    contentDescription = "Manage Profile",
                    modifier = Modifier.height(50.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Manage Profile",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Select a profile to view or update your information.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }

        // --- CONTENT CARDS ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // This spacer pushes the cards down to the vertical center of the screen
            Spacer(modifier = Modifier.height(60.dp))

            // USER PROFILE OPTION
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { -it }, // Slide in from the left
                    animationSpec = tween(durationMillis = 600, delayMillis = 200)
                )
            ) {
                ProfileOptionCard(
                    title = "Personal Profile",
                    subtitle = "View your appointments and personal data.",
                    icon = Icons.Default.Person,
                    onClick = onOpenUserProfile
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // DOCTOR PROFILE OPTION
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { it }, // Slide in from the right
                    animationSpec = tween(durationMillis = 600, delayMillis = 300)
                )
            ) {
                ProfileOptionCard(
                    title = "Doctor Profile",
                    subtitle = "Manage professional details and schedule.",
                    icon = Icons.Default.LocalHospital,
                    onClick = onOpenDrProfile
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PreviewManageProfileScreen() {
//    MaterialTheme {
//        ManageProfileScreen(onOpenUserProfile = {}, onOpenDrProfile = {})
//    }
//}