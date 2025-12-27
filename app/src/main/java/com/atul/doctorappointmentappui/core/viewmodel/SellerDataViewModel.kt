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

    suspend fun createSellerProfile(uid: String, doctorData: DoctorModel, onSuccess: () -> Unit, context: Context) {
        val finalDocData = doctorData.copy(id = uid, rating = 5.0)
        val result = repo.createSellerProfile(uid, finalDocData, onSuccess)
        when {
            result.isSuccess -> showToast(context, "Seller profile created")
            result.isFailure -> showToast(context, "Something went wrong")
        }
    }

    fun getData(sellerId: String, context: Context) {
        repo.fetchSellerData(sellerId) { result ->
            if (result != null) {
                _sellerData.value = result
                showToast(context, "Seller, data fetched")
            }
            else {
                showToast(context, "Seller data not-fetched")
            }
        }
    }

    suspend fun updateSellerDetails(sellerId: String, context: Context, details: DoctorModel) {
        val isUpdated = repo.updateSellerDetails(sellerId, details)
        val toastMessage = if (isUpdated) "Seller profile updated." else "Seller profile not-updated!!"
        showToast(context, toastMessage)
    }

    fun showToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }
}