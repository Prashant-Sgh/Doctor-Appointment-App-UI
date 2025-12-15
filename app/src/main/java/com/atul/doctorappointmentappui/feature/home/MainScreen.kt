package com.atul.doctorappointmentappui.feature.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.feature.appointment.SellerAppointmentsScreen
import com.atul.doctorappointmentappui.feature.manageAccount.components.IncompleteProfileBanner
import com.atul.doctorappointmentappui.feature.profileTab.ManageProfileScreen
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier? = null,
    showSellerBanner: Boolean,    // <--- Received from AppNavGraph
    onBannerClick: () -> Unit,    // <--- Received from AppNavGraph
    onOpenDoctorDetails: (DoctorModel) -> Unit,
    onOpenTopDoctors: () -> Unit,
    onManageAccount: () -> Unit,
    onOpenUserProfile: () -> Unit,
    onOpenDrProfile: () -> Unit,
    appointmentsFlow: StateFlow<List<AppointmentModel>>,
    viewAppointment: (AppointmentModel) -> Unit
) {
    val categories by viewModel.category.collectAsState()
    var selectedBottom by remember { mutableStateOf(0) }
    val doctors by viewModel.doctors.collectAsState()
    val userName by viewModel.UserName.collectAsState()
    val appointments by appointmentsFlow.collectAsState()

    // Local state to handle closing the banner for this session
    var isBannerDismissed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (categories.isEmpty()) viewModel.loadCategory()
        if (doctors.isEmpty()) viewModel.loadDoctors()
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            HomeBottomBar(
                selected = selectedBottom,
                onSelect = { selectedBottom = it }
            )
        }
    ) { inner ->
        when (selectedBottom) {
            0 -> {
                LazyColumn(contentPadding = inner) {

                    // --- 1. ADD BANNER AS THE FIRST ITEM ---
                    if (showSellerBanner) {
                        item {
                            IncompleteProfileBanner(
                                isVisible = true, // We already checked the condition above
                                onDismiss = { isBannerDismissed = true },
                                onActionClick = {
                                    onBannerClick()
                                    isBannerDismissed = true
                                }
                            )
                        }
                    }

                    // --- Existing Items ---
                    item { HomeHeader(userName) { onManageAccount() } }
                    item { Banner() }
                    item { SectionHeader(title = "Doctor Speciality", onSeeAll = null) }
                    item { CategoryRow(categories) }
                    item { SectionHeader(title = "Top Doctors", onSeeAll = { onOpenTopDoctors() }) }
                    item { DoctorRow(items = doctors, onClick = { onOpenDoctorDetails(it) }) }
                }
            }

            2 -> {
                SellerAppointmentsScreen(appointments = appointments) { viewAppointment(it) }
            }

            3 -> {
                ManageProfileScreen(
                    onOpenUserProfile = onOpenUserProfile,
                    onOpenDrProfile = onOpenDrProfile
                )
            }
        }
    }
}
