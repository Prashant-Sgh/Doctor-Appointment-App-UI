package com.atul.doctorappointmentappui.core.repo

import android.util.Log
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SellerDataRepo @Inject constructor() {
    // Ideally inject via constructor, but keeping your structure
    private val db = Firebase.firestore

    fun fetchSellerData(uid: String, onResult: (DoctorModel?) -> Unit) {
        db.collection("sellers")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val seller = document.toObject(DoctorModel::class.java)
                    onResult(seller)
                    Log.d("Firestore", "Seller data fetched")
                } else {
                    onResult(null)
                    Log.d("Firestore", "Seller document does not exist")
                }
            }
            .addOnFailureListener { e ->
                Log.d("Firestore", "Failed to fetch seller !! ERROR -", e)
                onResult(null)
            }
    }

    suspend fun updateSellerDetails(uid: String, sellerData: DoctorModel): Boolean {
        return try {
            // 1. Create a copy with the updated profileCompleted status
            // This checks our new 'isProfileInformationFilled' property automatically
            val updatedSellerData = sellerData.copy(
                profileCompleted = sellerData.isProfileInformationFilled
            )

            // 2. Save entire object to Firestore
            // Using .set() with Merge is often safer if we want to ensure fields exist,
            // but straight .set(data) works perfectly for a full profile update.
            db.collection("sellers")
                .document(uid)
                .set(updatedSellerData)
                .await()

            true
        } catch (e: Exception) {
            Log.e("SellerRepo", "Error updating seller details", e)
            false
        }
    }
}
