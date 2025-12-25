package com.atul.doctorappointmentappui.core.repo

import android.util.Log
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SellerDataRepo @Inject constructor() {

    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    // Ideally inject via constructor, but keeping your structure
    private val db = Firebase.firestore

    /**
     * Creates a new document for a seller in the 'sellers' collection.
     *
     * @param uid The unique ID for the document (typically the user's Firebase Auth UID).
     * @param doctorData The initial data for the seller, based on the DoctorModel.
     * @return A Result object indicating success (Result.success(Unit)) or failure (Result.failure(exception)).
     */
    suspend fun createSellerProfile(uid: String, doctorData: DoctorModel): Result<Unit> {
        return try {
            db.collection("sellers")
                .document(uid)
                .set(doctorData)
                .await() // Wait for the operation to complete
            Log.d("Firestore", "Seller profile created successfully for UID: $uid")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("Firestore", "Error creating seller profile for UID: $uid", e)
            Result.failure(e)
        }
    }

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
