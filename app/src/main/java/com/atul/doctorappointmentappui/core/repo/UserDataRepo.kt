package com.atul.doctorappointmentappui.core.repo

import android.util.Log
import com.atul.doctorappointmentappui.core.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataRepo @Inject constructor() {
    // Ideally this should be injected via constructor, but we'll keep it simple for now
    // to match your Hilt setup without changing your module file.
    private val db = Firebase.firestore

    /**
     * Fetches user data using a Flow.
     * This allows real-time updates. If the user changes their profile on another device,
     * this flow will emit the new data immediately.
     */
    fun getUserDataFlow(uid: String): Flow<UserModel?> = callbackFlow {
        val listenerRegistration = db.collection("users")
            .document(uid)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Listen failed.", error)
                    trySend(null) // Emit null on error
                    return@addSnapshotListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val user = documentSnapshot.toObject(UserModel::class.java)
                    trySend(user)
                    Log.d("Firestore", "Real-time user data received.")
                } else {
                    Log.d("Firestore", "User document does not exist")
                    trySend(null)
                }
            }

        // This block is called when the Flow collector is cancelled (e.g. screen closed)
        awaitClose {
            listenerRegistration.remove()
        }
    }

    /**
     * Updates user details.
     * We use 'set' with merge options or just 'set' to overwrite.
     * 'set' is often safer than 'update' if the document might not exist yet.
     */
    suspend fun updateUserDetails(uid: String, userData: UserModel): Boolean {
        return try {
            // Automatically update the profileCompleted flag before saving
            val updatedUserData = userData.copy(
                profileCompleted = userData.isProfileInformationFilled
            )
            // .set(userData) automatically maps the fields from the data class.
            // No need to manually create a mapOf("name" to ..., "age" to ...)
            db.collection("users")
                .document(uid)
                .set(updatedUserData)
                .await()
            true
        } catch (e: Exception) {
            Log.e("Firestore", "Error updating profile", e)
            false
        }
    }
}




//import android.util.Log
//import com.atul.doctorappointmentappui.core.model.UserModel
//import com.google.firebase.Firebase
//import com.google.firebase.firestore.firestore
//import kotlinx.coroutines.tasks.await
//import javax.inject.Inject
//
//
//class UserDataRepo @Inject constructor(){
//    private val db = Firebase.firestore
//
//    fun fetchUserData(uid: String, onResult: (UserModel?) -> Unit) {
//        db.collection("users")
//            .document(uid)
//            .get()
//            .addOnSuccessListener { document ->
//                val user = document.toObject(UserModel::class.java)
//                onResult(user)
//                Log.d("Firestore", "User data Fetched.")
//            }
//            .addOnFailureListener {e ->
//                Log.d("Firestore", "Failed to fetch user !! ERROR - ", e)
//                onResult(null)
//            }
//    }
//
//    suspend fun updateUserDetails(uid: String, userData: UserModel): Boolean {
//
//        val data = mapOf(
//            "imageURL" to userData.imageURL,
//            "userName" to userData.userName,
//            "age" to userData.age,
//            "male" to userData.male,
//            "email" to userData.email,
//            "phone" to userData.phone,
//            "address" to userData.address,
//            "totalAppointments" to userData.totalAppointments,
//            "seller" to userData.seller,
//            "profileCompleted" to userData.profileCompleted
//        )
//
//        return try {
//            db.collection("users")
//                .document(uid)
//                .update(data)
//                .await()
//            true
//        }
//        catch (e: Exception) {
//            false
//        }
//    }
//
//}