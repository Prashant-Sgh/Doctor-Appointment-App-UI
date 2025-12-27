package com.atul.doctorappointmentappui.core.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.core.repo.UserDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val repo: UserDataRepo
): ViewModel() {

    private val _userData = MutableStateFlow(UserModel())
    val userData: StateFlow<UserModel> = _userData.asStateFlow()

    fun getData(userId: String, context: Context) {
        if (userId.isBlank()) {
            Log.d("UserDataViewModel", "getData called with blank UID. Aborting.")
            return
        }

        viewModelScope.launch {
            if (repo.doesUserExist(userId)) {
                repo.getUserDataFlow(userId)
                    .collect { user ->
                        if (user != null) {
                            _userData.value = user
                            showToast(context, "User data fetched.")
                        }
                        else {
                            showToast(context, "User document for UID: $userId does not exist.")
                            _userData.value = UserModel(userId = userId, profileCompleted = false)
                        }
                    }
            }
        }
    }

    fun clearUserData() {
        _userData.value = UserModel()
    }

    suspend fun updateUserDetails(userId: String, context: Context, details: UserModel, onSuccess: () -> Unit) {

        if (userId.isBlank()) {
            showToast(context, "Cannot update profile: User ID is missing.")
            return
        }

        val isUpdated = repo.updateUserDetails(userId, details)
        if (isUpdated) onSuccess()
        val toastMessage = if (isUpdated) "Profile updated" else "Profile not-updated!!"
        showToast(context, toastMessage)
    }

    fun showToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }
}