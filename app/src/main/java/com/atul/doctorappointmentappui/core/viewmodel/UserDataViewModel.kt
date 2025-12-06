package com.atul.doctorappointmentappui.core.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.core.repo.UserDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val repo: UserDataRepo
): ViewModel() {

    private val _userData = MutableStateFlow(UserModel())
    val userData: StateFlow<UserModel> = _userData

    private val _userUid = MutableStateFlow("")
    val userUid: StateFlow<String> = _userUid

    fun updateUid(uid: String) {
        _userUid.value = uid
    }

    fun getData(uid: String) {
        updateUid(uid)
        repo.fetchUserData(uid) {result ->
            val data = result?: UserModel(userName = "WTF..")
            updateUserData(data)
        }
    }

    fun updateUserData(data: UserModel) {
        _userData.value = data
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