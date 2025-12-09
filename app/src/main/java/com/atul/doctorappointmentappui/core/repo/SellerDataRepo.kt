package com.atul.doctorappointmentappui.core.repo

import android.util.Log
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SellerDataRepo @Inject constructor() {
    val db = Firebase.firestore

    fun fetchSellerData (uid: String, onResult: (DoctorModel?) -> Unit) {
        db.collection("sellers")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                val seller = document.toObject(DoctorModel::class.java)
                onResult(seller)
                Log.d("Firestore", "Seller data fetched")
            }
            .addOnFailureListener { e ->
                Log.d("Firestore", "Failed to fetch user !! ERROR -", e)
                onResult(null)
            }
    }

    suspend fun updateSellerDetails(uid: String, sellerData: DoctorModel): Boolean {
        val data = mapOf(
            "address" to sellerData.address,
            "biography" to sellerData.biography,
            "experience" to sellerData.experience,
            "location" to sellerData.location,
            "name" to sellerData.name,
            "patients" to sellerData.patients,
            "phone" to sellerData.phone,
            "picture" to sellerData.picture,
            "rating" to sellerData.rating,
            "site" to sellerData.site,
            "special" to sellerData.special,
        )

        return try {
            db.collection("sellers")
                .document(uid)
                .update(data)
                .await()
            true
        }
        catch (e: Exception) {
            false
        }
    }
}