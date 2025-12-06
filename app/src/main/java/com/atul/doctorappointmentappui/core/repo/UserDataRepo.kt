package com.atul.doctorappointmentappui.core.repo

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.atul.doctorappointmentappui.core.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserDataRepo @Inject constructor(){
    private val db = Firebase.firestore

    fun fetchUserData(uid: String, onResult: (UserModel?) -> Unit) {
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(UserModel::class.java)
                onResult(user)
                Log.d("Firestore", "User data Fetched.")
            }
            .addOnFailureListener {e ->
                Log.d("Firestore", "Failed to fetch user !! ERROR - ", e)
                onResult(null)
            }
    }

    suspend fun updateUserDetails(uid: String, userData: UserModel): Boolean {

        val data = mapOf(
            "imageURL" to userData.imageURL,
            "userName" to userData.userName,
            "age" to userData.age,
            "male" to userData.male,
            "email" to userData.email,
            "phone" to userData.phone,
            "address" to userData.address,
            "totalAppointments" to userData.totalAppointments,
        )

        return try {
            db.collection("users")
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