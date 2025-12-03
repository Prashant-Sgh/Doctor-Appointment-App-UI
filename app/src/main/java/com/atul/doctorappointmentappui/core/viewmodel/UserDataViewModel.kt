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

    private val _userData = MutableStateFlow<UserModel>(UserModel())
    val userData: StateFlow<UserModel> = _userData

    fun getData(uid: String) {
        repo.fetchUserData(uid) {result ->
            _userData.value = result?: UserModel()
            Log.d("Firestore", "Data Fetched: ${userData.value.userName}")
            Log.d("Firestore", "Data Fetched too: ${result?.userName}")
//            Log.d("Firestore", "Entire Data: $result")
        }
    }

    fun getUserData(): UserModel {
        return _userData.value
    }
}