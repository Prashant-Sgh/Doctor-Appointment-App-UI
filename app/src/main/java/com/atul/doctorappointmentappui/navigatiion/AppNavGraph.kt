package com.atul.doctorappointmentappui.navigatiion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.AuthViewModel
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.core.viewmodel.SellerDataViewModel
import com.atul.doctorappointmentappui.core.viewmodel.UserDataViewModel
import com.atul.doctorappointmentappui.navigatiion.routes.authRoute
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
    vm: MainViewModel,
    authVm: AuthViewModel,
    userDataViewmodel: UserDataViewModel,
    sellerDataViewModel: SellerDataViewModel
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    NavHost(navCon, Screen.Intro.route) {
        introRoute(
            onStart = {
                if (authVm.currentUserId.value != null) {
                    val userName = authVm.UserName
                    vm.updateUserName(userName.value)
                    navCon.navigate(Screen.Home.route) {
                        popUpTo(Screen.Intro.route) { inclusive = true }
                    }
                }
                else{
                    navCon.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Intro.route) {inclusive = true}
                    }
                }
            }
        )

        authRoute(
            onGoogleSignIn = {
                scope.launch {
                    authVm.signInWithGoogle(it, { screen1, screen2 -> }, context = context)
                }
                             },
            onEmailAuth = {email, password, isLogin ->
                scope.launch {
                    authVm.authenticateWithEmailPassword(email, password, isLogin, context) {
                        navCon.navigate(Screen.Home.route) {
                            popUpTo(Screen.Auth.route) {inclusive = true}
                        }
                    }
                }
                          },
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
                userDataViewmodel.getData("uid", context)
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
                }
            },
            saveUserData = {
                scope.launch {
                    userDataViewmodel.updateUserDetails(context,it)
                }
                navCon.popBackStack()
            }
        )

        drProfileManagementRoute(
//            doctor = sellerData,
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
            onOpenDetails = {doctor ->
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