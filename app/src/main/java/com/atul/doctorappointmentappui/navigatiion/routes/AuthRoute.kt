package com.atul.doctorappointmentappui.navigatiion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.feature.auth.AuthScreen
import com.atul.doctorappointmentappui.navigatiion.Screen

fun NavGraphBuilder.authRoute(
    onGoogleSignIn: () -> Unit,
    onEmailAuth: (String, String, Boolean) -> Unit, // email, pass, isLogin
) {
    composable(Screen.Auth.route) {
        AuthScreen(
            { onGoogleSignIn },
            {email, password, isLogin ->
                onEmailAuth(email, password, isLogin)
            }
        )
    }
}