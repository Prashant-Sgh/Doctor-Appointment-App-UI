package com.atul.doctorappointmentappui.navigatiion.routes

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.State
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.feature.manageAccount.CompleteProfileScreen
import com.atul.doctorappointmentappui.navigatiion.Screen
import kotlinx.coroutines.flow.StateFlow

fun NavGraphBuilder.completeUserProfileRoute (
    initialUser: StateFlow<UserModel>,
    onProfileCompleted: (UserModel) -> Unit,
    context: Context
) {
    composable(Screen.CompleteUserProfile.route) {
        // Prevent Back Press
        BackHandler(enabled = true) {
            Toast.makeText(context, "Please complete your profile to continue.", Toast.LENGTH_SHORT).show()
        }

        CompleteProfileScreen(
            initialUserFlow = initialUser,
            onProfileCompleted = onProfileCompleted
        )
    }
}