package com.atul.doctorappointmentappui.core.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.repo.SellerDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SellerDataViewModel @Inject constructor(
    private val repo: SellerDataRepo
): ViewModel() {

    private val _sellerData = MutableStateFlow(DoctorModel())
    val sellerData: StateFlow<DoctorModel> = _sellerData.asStateFlow()

    private val _sellerUid = MutableStateFlow("uid")
    val sellerUid: StateFlow<String> = _sellerUid

    suspend fun createSellerProfile(uid: String, doctorData: DoctorModel, context: Context) {
        val finalDocData = doctorData.copy(id = uid)
        val result = repo.createSellerProfile(uid, finalDocData)
        when {
            result.isSuccess -> showToast(context, "Seller profile created")
            result.isFailure -> showToast(context, "Something went wrong")
        }
    }

    private fun updateSellerUid(uid: String) {
        _sellerUid.value = uid
    }

    fun getData(uid: String, context: Context) {
        updateSellerUid(uid)
        repo.fetchSellerData(uid) { result ->
            if (result != null) {
                _sellerData.value = result
                showToast(context, "Seller, data fetched")
            }
            else {
                showToast(context, "Seller data not-fetched")
            }
        }
    }

    suspend fun updateSellerDetails(context: Context, details: DoctorModel) {
        val isUpdated = repo.updateSellerDetails(sellerUid.value, details)
        val toastMessage = if (isUpdated) "Seller profile updated." else "Seller profile not-updated!!"
        showToast(context, toastMessage)
    }

    fun showToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }
}