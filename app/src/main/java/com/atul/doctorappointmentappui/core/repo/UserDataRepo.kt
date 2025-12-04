package com.atul.doctorappointmentappui.core.repo

import android.util.Log
import com.atul.doctorappointmentappui.core.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
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

    fun updateUserDetails(uid: String, userData: UserModel) {

//        db.collection("users")
//            .document(uid)
//            .set(userData, SetOptions.merge())
//            .addOnSuccessListener {
//                Log.d("Firestore", "User Updated Successful.")
//            }
//            .addOnFailureListener { e ->
//                Log.d("Firestore", "User Not-updated. ERROR - $e")
//            }

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

        db.collection("users")
            .document(uid)
            .update(data)
            .addOnSuccessListener {
                Log.d("Firestore", "User Updated Successful")
            }
            .addOnFailureListener { e ->
                Log.d("Firestore", "User Not-updated $e")
            }
    }

}