package com.atul.doctorappointmentappui.feature.manageAccount.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atul.doctorappointmentappui.R

@Composable
fun DetailItem(
    detailName: String = "",
    detail: String = "",
    enabled: Boolean = false,
    onValueChange: (String) -> Unit
) {

    var fieldDetail = detail
    val fontSize = 18.sp

    Row(
        Modifier.padding(7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${detailName}: ",
            fontSize = fontSize,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        BasicTextField(
            value = fieldDetail,
            enabled = enabled,
            onValueChange = { value ->
                fieldDetail = value
                // onValueChange(value)
            },
            textStyle = TextStyle(
                fontSize = fontSize,
                fontStyle = if (enabled) FontStyle.Normal else FontStyle.Italic,
                color = if (enabled) Color.Black else Color.Gray
            ),
            modifier = Modifier.background(colorResource(R.color.lightPuurple))
        )
    }
}