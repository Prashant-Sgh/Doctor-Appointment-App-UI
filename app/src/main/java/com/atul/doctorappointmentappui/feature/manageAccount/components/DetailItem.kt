package com.atul.doctorappointmentappui.feature.manageAccount.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    needToggleValue: Boolean = false,
    toggleChange: (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {

    var fieldDetail by remember { mutableStateOf(detail) }
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
        if (!needToggleValue) {
            BasicTextField(
                value = fieldDetail,
                enabled = enabled,
                onValueChange = {
                    fieldDetail = it
                    onValueChange(it)
                },
                textStyle = TextStyle(
                    fontSize = fontSize,
                    fontStyle = if (enabled) FontStyle.Normal else FontStyle.Italic,
                    color = if (enabled) Color.Black else Color.DarkGray
                ),
                modifier = Modifier.background(colorResource(R.color.lightPuurple))
            )
        }
        else {
            val elevationValue = if (enabled) 5.dp else 0.dp
            val cardElevation = CardDefaults.cardElevation(elevationValue)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.width(5.dp))
                ElevatedCard (
                    elevation = cardElevation,
                    shape = RoundedCornerShape(6.dp)
                    ){
                    Text(
                        detail,
                        color = if (enabled) Color.Black else Color.Gray,
                        modifier = Modifier.padding(5.dp)
                            .background(
                                color = Color.Unspecified,
                                shape = RoundedCornerShape(0.dp)
                                )
                    )
                }
                if (enabled) {
                    IconButton (
                        enabled = true,
                        onClick = {
                            toggleChange?.invoke()
                        }
                    ){
                        Icon(
                            Icons.Default.Repeat,
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }
    }
}

//@Preview (showBackground = true)
//@Composable
//private fun P() {
//    DetailItem(
//        "Gender", "Male", false,  true, {}, {}
//    )
//}