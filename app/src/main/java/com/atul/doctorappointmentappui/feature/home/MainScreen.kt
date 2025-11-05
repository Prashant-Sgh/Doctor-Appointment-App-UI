package com.atul.doctorappointmentappui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(

) {
    var selectedBottom by remember { mutableStateOf(2) }

    Scaffold (
        containerColor = Color.White,
        bottomBar = {
            HomeBottomBar(
                selected = selectedBottom,
                onSelect = {selectedBottom = it}
            )
        }
    ) { inner ->
        LazyColumn( contentPadding = inner) {
            item { HomeHeader() }
            item { Banner() }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MainScreen()
}