package com.atul.doctorappointmentappui.feature.manageAccount.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atul.doctorappointmentappui.R

@Composable
fun SaveButton(edit: Boolean = false, onClick: () -> Unit) {

    val icon = if (edit) Icons.Default.Check else Icons.Default.Edit
    val buttonText = if (edit) "Save" else "Edit"

    IconButton (
        onClick = {},
        modifier = Modifier
            .size(height = 24.dp, width = 62.dp)
            .background(
                color = colorResource(R.color.puurple),
                shape = RoundedCornerShape(5.dp)
            ).padding(5.dp)
    ) {
        Row(
            Modifier.background(color = colorResource(R.color.puurple)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                Modifier.size(12.dp),
                tint = Color.White
            )
            Spacer(Modifier.width(6.dp))
            Text(
                buttonText,
                color = Color.White,
                fontSize = 12.sp,
            )
        }
    }
}
