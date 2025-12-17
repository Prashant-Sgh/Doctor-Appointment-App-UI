package com.atul.doctorappointmentappui.feature.home

import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.core.viewmodel.SellerDataViewModel
import com.atul.doctorappointmentappui.feature.manageAccount.components.IncompleteProfileBanner
import com.atul.doctorappointmentappui.navigatiion.Screen
import com.atul.doctorappointmentappui.navigatiion.navigateToDetail
import com.atul.doctorappointmentappui.navigatiion.routes.mainScreenRoute
import com.atul.doctorappointmentappui.navigatiion.routes.manageAccountRoute
import com.atul.doctorappointmentappui.navigatiion.routes.sellerAppointmentRoute
import com.atul.doctorappointmentappui.navigatiion.routes.sellerAppointmentsManagementRoute


@Composable
fun MainScreenWrapper(
    showBanner: Boolean,
    onBannerClick: () -> Unit,
    signOutUser: () -> Unit,
) {
    val navController= rememberNavController()
    // Observe current route to highlight the correct bottom bar icon
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            HomeBottomBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    )  { innerPadding ->
        //This NavHost handles the tab switching
        NavHost(
            navController = navController,
            startDestination = Screen.MainScreen.route,
            modifier = Modifier.padding(innerPadding)
        )
        {
            mainScreenRoute(
                showBanner = showBanner,
                onOpenDoctorDetails = { doctor -> navController.navigateToDetail(doctor) },
                onOpenTopDoctors = { navController.navigate(Screen.TopDoctors.route) },
                onManageAccount = { navController.navigate(Screen.ManageAccount.route) },
                onBannerClick = { onBannerClick() }
            )
            manageAccountRoute(
                signOutUser = { signOutUser() }
            )
            sellerAppointmentRoute(
                onViewAppointment = { sellerAppointmentsManagementRoute(it) }
            )
        }
    }
}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    sellerViewModel: SellerDataViewModel = hiltViewModel(),
    showBanner: Boolean = false,
    modifier: Modifier? = null,
    onBannerClick: () -> Unit,    // <--- Received from AppNavGraph
    onOpenDoctorDetails: (DoctorModel) -> Unit,
    onOpenTopDoctors: () -> Unit,
    onManageAccount: () -> Unit,
) {

    // Collect Data
    val categories by mainViewModel.category.collectAsState()
    val doctors by mainViewModel.doctors.collectAsState()
    val userName by mainViewModel.UserName.collectAsState()

    // Local state to handle closing the banner for this session
    var isBannerDismissed by remember { mutableStateOf(false) }

    // Initial Data Load
    LaunchedEffect(Unit) {
        if (categories.isEmpty()) mainViewModel.loadCategory()
        if (doctors.isEmpty()) mainViewModel.loadDoctors()
    }

    LazyColumn() {

        // --- 1. BANNER ---
        // Only add item if needed.
        if (showBanner && !isBannerDismissed) {
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
