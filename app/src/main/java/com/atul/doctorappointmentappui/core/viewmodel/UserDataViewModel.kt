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

    private val _userUid = MutableStateFlow("")
    val userUid: StateFlow<String> = _userUid

    private fun updateUid(uid: String) {
        _userUid.value = uid
    }

    fun getData(uid: String, context: Context) {
        updateUid(uid)

        viewModelScope.launch {
            repo.getUserDataFlow(uid)
                .collect { user ->
                    if (user != null) {
                        updateUserData(user)
                        showToast(context, "User data fetched.")
                    }
                    else {
                        showToast(context, "User data not-fetched.")
                    }
                }
        }
    }

    private fun updateUserData(data: UserModel) {
        _userData.value = data
    }

    fun clearUserData() {
        val userdata = UserModel()
        updateUserData(userdata)
    }


    suspend fun updateUserDetails(context: Context, details: UserModel) {
        val isUpdated = repo.updateUserDetails(_userUid.value, details)
        val toastMessage = if (isUpdated) "Profile updated" else "Profile not-updated!!"
        showToast(context, toastMessage)
    }

    fun showToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }
}