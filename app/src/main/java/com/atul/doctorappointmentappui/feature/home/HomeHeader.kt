package com.atul.doctorappointmentappui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atul.doctorappointmentappui.R

/**
 * A modern header for the home screen, displaying a user greeting and action icons.
 *
 * @param userName The name of the user to greet.
 * @param onManageAccount Lambda to be invoked when the user taps the account icon.
 */
@Composable
fun HomeHeader(userName: String, onReload: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- Greeting Text ---
        Column(
            modifier = Modifier.weight(1f) // Takes up available space
        ) {
            Text(
                text = "Hi, $userName",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "How are you doing today?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        // --- Action Icons ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = { /* TODO: Handle notification click */ }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.size(28.dp),
                    tint = Color.Gray
                )
            }
            IconButton(
                onClick = { onReload() },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.lightPuurple))
            ) {
                Icon(
                    imageVector = Icons.Default.Loop,
                    contentDescription = "Reload data",
                    tint = colorResource(R.color.puurple),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun HomeHeaderPreview() {
    MaterialTheme {
        HomeHeader("Dr. Edward Greek") { }
    }
}
