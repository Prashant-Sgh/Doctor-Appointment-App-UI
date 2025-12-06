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
import com.atul.doctorappointmentappui.core.viewmodel.UserDataViewModel
import com.atul.doctorappointmentappui.navigatiion.routes.authRoute
import com.atul.doctorappointmentappui.navigatiion.routes.detailRoute
import com.atul.doctorappointmentappui.navigatiion.routes.docProManageRoute
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
    userDataViewmodel: UserDataViewModel
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val userData by userDataViewmodel.userData.collectAsState()

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
                userDataViewmodel.getData("uid")
//                Log.d("Firestore", "From NavGraph too: ${userDataViewmodel.getUserData()}")
                navCon.navigate(Screen.ManageAccount.route)
            },
            onUserSelected = {
                navCon.navigate(Screen.ManageAccount.route)
            },
            onDoctorSelected = {

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

        docProManageRoute(
            doctor = DoctorModel(),
            onSavedConfirmed = {}
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