package com.atul.doctorappointmentappui.navigatiion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(
    navCon: NavHostController,
    vm: MainViewModel,
    authVm: AuthViewModel,
    userDataViewmodel: UserDataViewModel,
    sellerDataViewModel: SellerDataViewModel
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 1. Observe User Data globally here to make navigation decisions
    val currentUserData by userDataViewmodel.userData.collectAsState()
    val authUser = authVm.currentUserId.collectAsState().value

    // Helper to fetch data when we know we have a UID
    fun ensureUserDataLoaded() {
        if (authUser != null && currentUserData.userName == "") {
            userDataViewmodel.getData("uid", context)
        }
    }

    // 2. The critical logic for where to send the user
    suspend fun navigateToCorrectScreen() {
        if (authUser == null) {
            // Case A: Not Logged In
            navCon.navigate(Screen.Auth.route) {
                popUpTo(Screen.Intro.route) { inclusive = true }
            }
        } else {
            // Ensure we have the latest data before deciding
            ensureUserDataLoaded()

            // We might need a small delay or a check if data is loaded,
            // but assuming data flows in:
            delay(2000)

            if (!currentUserData.profileCompleted) {
                // Case B: Logged in, but profile incomplete -> Force Setup
                navCon.navigate(Screen.CompleteUserProfile.route) {
                    popUpTo(Screen.Intro.route) { inclusive = true }
                }
            } else {
                // Case C: All Good -> Home
                vm.updateUserName(currentUserData.userName)
                navCon.navigate(Screen.Home.route) {
                    popUpTo(Screen.Intro.route) { inclusive = true }
                }
            }
        }
    }

    NavHost(navCon, Screen.Intro.route) {

        introRoute(
            onStart = {
                scope.launch {
                    // Trigger the check when user clicks "Get Started"
                    navigateToCorrectScreen()
                }
            }
        )

        authRoute(
            onGoogleSignIn = {
                scope.launch {
                    authVm.signInWithGoogle(it, { _, _ -> }, context = context)
                    // After successful login, check where to go next
                    // We need to fetch data first
                    userDataViewmodel.getData(authVm.currentUserId.value ?: "", context)
                }
            },
            onEmailAuth = { email, password, isLogin ->
                scope.launch {
                    authVm.authenticateWithEmailPassword(email, password, isLogin, context) {
                        // Auth successful, now check profile status
                        userDataViewmodel.getData(authVm.currentUserId.value ?: "", context)
                    }
                }
            },
        )

        // Listen for Auth/Data changes to redirect automatically after login
        // This acts as a router after the Auth screen closes
        composable("auth_redirect_handler") {
            LaunchedEffect(currentUserData) {
                if (currentUserData.profileCompleted) {
                    navCon.navigate(Screen.Home.route) { popUpTo(Screen.Auth.route) { inclusive = true } }
                } else {
                    navCon.navigate(Screen.CompleteUserProfile.route) { popUpTo(Screen.Auth.route) { inclusive = true } }
                }
            }
        }

        // --- NEW ROUTE: Complete Profile ---

        completeUserProfileRoute(
            context = context,
            initialUser = currentUserData,
            onProfileCompleted = { updatedUser ->
                scope.launch {
                    // 1. Save to Firestore
                    userDataViewmodel.updateUserDetails(context, updatedUser)
                    // 2. Navigate to Home
                    navCon.navigate(Screen.Home.route) {
                        popUpTo("complete_profile_route") { inclusive = true }
                    }
                }
            }
        )

        homeRoute(
            viewmodel = vm,
            onOpenDetails = { doctorModel ->
                navCon.navigateToDetail(doctorModel)
            },
            onOpenTopDoctors = {
                navCon.navigate(Screen.TopDoctors.route)
            },
            onManageAccount = {
                navCon.navigate(Screen.ManageAccount.route)
            },
            onOpenUserProfile = {
                navCon.navigate(Screen.ManageAccount.route)
            },
            onOpenDrProfile = {
                sellerDataViewModel.getData("uid", context)
                navCon.navigate(Screen.DrProfileManagement.route)
            }
        )

        manageAccountRoute(
            userDataVm = userDataViewmodel,
            signOutUser = {
                scope.launch {
                    authVm.signOutUser()
                    navCon.navigate(Screen.Intro.route) {
                        popUpTo(0) { inclusive = true } // Clear stack
                    }
                }
            },
            saveUserData = {
                scope.launch {
                    userDataViewmodel.updateUserDetails(context, it)
                }
                navCon.popBackStack()
            }
        )

        drProfileManagementRoute(
            sellerDataViewModel = sellerDataViewModel,
            onSavedConfirmed = {
                scope.launch {
                    sellerDataViewModel.updateSellerDetails(context, it)
                }
            }
        )

        topDoctorsRoute(
            viewmodel = vm,
            onBack = {
                navCon.popBackStack()
            },
            onOpenDetails = { doctor ->
                navCon.navigateToDetail(doctor)
            }
        )

        detailRoute(
            nav = navCon,
            onBack = {
                navCon.popBackStack()
            }
        )
    }
}
