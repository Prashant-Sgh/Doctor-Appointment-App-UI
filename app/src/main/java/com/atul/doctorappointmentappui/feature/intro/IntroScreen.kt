package com.atul.doctorappointmentappui.feature.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atul.doctorappointmentappui.R

@Composable
fun IntroScreen(onStartClick: ()-> Unit ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.puurple))
    ) {
        Image(
            painter = painterResource(R.drawable.white_halo),
            contentDescription = "White halo",
            modifier = Modifier
                .align( Alignment.TopCenter )
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Image(
            painter = painterResource(R.drawable.intro_women),
            contentDescription = "White halo",
            modifier = Modifier
                .align( Alignment.BottomCenter )
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column (
            modifier = Modifier
                .align( Alignment.TopStart )
                .statusBarsPadding()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            Text(stringResource(R.string.sub_intro),
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(24.dp))
            Button(onClick = { onStartClick() },
                modifier = Modifier.size(170.dp, 60.dp),
                shape = RoundedCornerShape(size = 100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.darkPuurple),
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(start = 8.dp, end = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.get_started),
                        fontSize = 20.sp)
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(R.drawable.white_arrow),
                        contentDescription = "Arrow right",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    IntroScreen { {} }
}