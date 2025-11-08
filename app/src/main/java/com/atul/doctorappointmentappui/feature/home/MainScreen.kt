package com.atul.doctorappointmentappui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier? = null
) {

    val categories by viewModel.category.collectAsState()
    var selectedBottom by remember { mutableStateOf(0) }

    val doctors by viewModel.doctors.collectAsState()

    LaunchedEffect(Unit) {
        if (categories.isEmpty()) viewModel.loadCategory()
        if (doctors.isEmpty()) viewModel.loadDoctors()
    }

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
            item { SectionHeader(title = "Doctor Speciality", onSeeAll = null) }
            item { CategoryRow(categories) }
            item { SectionHeader(title = "Doctor Speciality", onSeeAll = null) }
            item { DoctorRow(items = doctors, onClick = {}) }
        }
    }
}