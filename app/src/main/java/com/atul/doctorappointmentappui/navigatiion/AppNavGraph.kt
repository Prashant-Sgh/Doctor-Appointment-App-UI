package com.atul.doctorappointmentappui.navigatiion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.AppointmentViewModel
import com.atul.doctorappointmentappui.core.viewmodel.AuthViewModel
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.core.viewmodel.SellerDataViewModel
import com.atul.doctorappointmentappui.core.viewmodel.UserDataViewModel
import com.atul.doctorappointmentappui.navigatiion.routes.authRoute
import com.atul.doctorappointmentappui.navigatiion.routes.completeUserProfileRoute
import com.atul.doctorappointmentappui.navigatiion.routes.detailRoute
import com.atul.doctorappointmentappui.navigatiion.routes.drProfileManagementRoute
import com.atul.doctorappointmentappui.navigatiion.routes.homeRoute
import com.atul.doctorappointmentappui.navigatiion.routes.introRoute
import com.atul.doctorappointmentappui.navigatiion.routes.manageAccountRoute
import com.atul.doctorappointmentappui.navigatiion.routes.topDoctorsRoute
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(
    navCon: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    authVm: AuthViewModel = hiltViewModel(),
    userDataViewmodel: UserDataViewModel = hiltViewModel(),
    sellerDataViewModel: SellerDataViewModel = hiltViewModel(),
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // --- Start Destination Logic ---
    val authUserId by authVm.currentUserId.collectAsState()

    // Collect user data state
    val currentUserData by userDataViewmodel.userData.collectAsState() // Access value for logic

    // Collect seller data state
    val currentSellerData by sellerDataViewModel.sellerData.collectAsState()

    val fetchData = remember { mutableStateOf(false) }

    LaunchedEffect(authUserId, currentUserData.seller, fetchData.value) {
        if (authUserId != "" && currentUserData.seller) {
            userDataViewmodel.getData(authUserId, context)
            sellerDataViewModel.getData(authUserId, context)
        }
    }

    // 3. Calculate the Banner Logic
    // Show if: User is a Seller AND Seller Profile is NOT complete
    val showSellerBanner = remember { mutableStateOf(currentUserData.seller && !currentSellerData.profileCompleted) }


    // Decide the staring screen
    val startDestination = when {
        authUserId == "" -> Screen.Auth.route // Not authenticated
        !currentUserData.profileCompleted -> Screen.CompleteUserProfile.route // Profile incomplete
        else -> Screen.Home.route // All good
    }

    // This effect runs whenever the authenticated user changes
    LaunchedEffect(authUserId, fetchData.value) {
//        if (authUser != null && authUser != "") {
        if (authUserId != "") {
            // If we have a logged-in user, always ensure their data is loaded
            userDataViewmodel.getData(authUserId, context)
        } else {
            // If user logs out, clear the data
            userDataViewmodel.clearUserData()
            // Navigate to Auth screen after logout
            navCon.navigate(Screen.Auth.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(navCon, Screen.Intro.route) { // Always start at Intro to show brand

        introRoute(
            onStart = {
                // When "Get Started" is clicked, navigate to the correct calculated destination
                navCon.navigate(startDestination) {
                    popUpTo(Screen.Intro.route) { inclusive = true }
                }
            }
        )

        authRoute(
            onGoogleSignIn = {
                scope.launch {
                    authVm.signInWithGoogle(it, { _, _ -> }, context = context)
                    // The LaunchedEffect(authUser) will handle the rest
                }
            },
            onEmailAuth = { email, password, isLogin ->
                scope.launch {
                    authVm.authenticateWithEmailPassword(email, password, isLogin, context) {
                        fetchData.value = !fetchData.value
                        navCon.navigate(Screen.Intro.route)
                    }
                }
            },
        )

        completeUserProfileRoute(
            initialUser = userDataViewmodel.userData,
            // Pass the user data directly from the ViewModel's state
            onProfileCompleted = { updatedUser ->
                scope.launch {
                    // 1. Save to Firestore
                    userDataViewmodel.updateUserDetails(authUserId, context, updatedUser) {
                        fetchData.value = !fetchData.value
                        // 2. Navigate to Intro screen after completion
                        navCon.navigate(Screen.Intro.route) {
                            popUpTo(Screen.CompleteUserProfile.route) { inclusive = true }
                        }
                    }
                }
            },
            context = context
        )

        homeRoute(
            mainViewModel = mainViewModel,
            userDataViewModel = userDataViewmodel,
            sellerDataViewModel = sellerDataViewModel,
            appointmentViewModel = appointmentViewModel,
            userId = authUserId,
            showBanner = showSellerBanner.value,
            onBannerClick = {
                navCon.navigate(Screen.DrProfileManagement.route)
            },
            onOpenTopDoctors = {
                navCon.navigate(Screen.TopDoctors.route)
            },
            onManageAccountClick = {
                navCon.navigate(Screen.ManageAccount.route)
            },
            onRestartApp = {navCon.navigate(Screen.Intro.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            } },
            onSignOut = {
                scope.launch {
                    authVm.signOut()
                    fetchData.value = !fetchData.value
                    navCon.navigate(Screen.Intro.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            }
        )

        drProfileManagementRoute(sellerDataViewModel, authUserId)

        topDoctorsRoute(
            viewmodel = mainViewModel,
            onBack = {
                navCon.popBackStack()
            },
            onOpenDetails = { doctor ->
                navCon.navigateToDetail(doctor)
            }
        )
    }
}
