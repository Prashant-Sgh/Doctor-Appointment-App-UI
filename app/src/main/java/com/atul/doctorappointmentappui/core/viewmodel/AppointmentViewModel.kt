package com.atul.doctorappointmentappui.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.core.repo.AppointmentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val repo: AppointmentRepo
): ViewModel() {

    private val _appointments = MutableStateFlow<List<AppointmentModel>>(emptyList())
    val appointments: StateFlow<List<AppointmentModel>> = _appointments.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    /**
     * Call this when the Seller Dashboard screen opens
     */
    fun fetchAppointments(doctorId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            repo.getAppointmentsFlow(doctorId).collect { list ->
                _appointments.value = list
                _isLoading.value = false
            }
        }
    }

    /**
     * Use this when the Seller clicks "Accept" or "Reject"
     */
    fun updateStatus(appointmentId: String, newStatus: String) {
        viewModelScope.launch {
            repo.updateAppointmentStatus(appointmentId, newStatus)
        }
    }

    fun getUserDataOf(userId: String): Flow<UserModel?> {
        return repo.getUserDataFlow(userId)
    }

    }