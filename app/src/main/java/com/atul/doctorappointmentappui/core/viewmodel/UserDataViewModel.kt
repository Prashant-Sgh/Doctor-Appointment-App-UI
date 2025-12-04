package com.atul.doctorappointmentappui.core.viewmodel

import android.util.Log
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

    fun updateUserDetails(details: UserModel) {
        repo.updateUserDetails(_userUid.value, details)
    }
}