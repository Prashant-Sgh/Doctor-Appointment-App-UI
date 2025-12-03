package com.atul.doctorappointmentappui.core.repo

import android.util.Log
import com.atul.doctorappointmentappui.core.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import javax.inject.Inject


class UserDataRepo @Inject constructor(){
    private val db = Firebase.firestore

    fun fetchUserData(uid: String, onResult: (UserModel?) -> Unit) {
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
//                Log.d("Firestore", "Success")
                val user = document.toObject(UserModel::class.java)
//                Log.d("Firestore", "Raw Data: ${document.data}")
                onResult(user)
//                Log.d("Firestore", "Address: ${user?.address}")
            }
            .addOnFailureListener {e ->
                Log.d("Firestore", "Failed to fetch user", e)
                onResult(null)
            }
    }

}