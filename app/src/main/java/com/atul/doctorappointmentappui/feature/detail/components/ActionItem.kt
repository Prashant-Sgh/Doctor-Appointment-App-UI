package com.atul.doctorappointmentappui.feature.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.atul.doctorappointmentappui.R

@Composable
fun RowScope.ActionItem(
    label: String,
    icon: Int,
    lightPurple: Color,
    enable: Boolean,
    onClick: () -> Unit
    ) {
    val textColor = if (enable) Color.Black else colorResource(R.color.ggray)

    Column(
        Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            onClick = onClick,
            enabled = enable,
            shape = CircleShape,
            color = lightPurple,
            tonalElevation = 0.dp
        ) {
            Box(
                Modifier
                    .size(55.dp)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(icon), contentDescription = null)
            }

            Spacer(Modifier.height(8.dp))

            Text(
                label,
                color = textColor,
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                fontWeight = MaterialTheme.typography.labelSmall.fontWeight?: FontWeight.Bold
            )
        }
    }
}