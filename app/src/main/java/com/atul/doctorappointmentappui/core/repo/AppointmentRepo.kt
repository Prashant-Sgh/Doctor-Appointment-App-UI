package com.atul.doctorappointmentappui.core.repo

import android.util.Log
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.atul.doctorappointmentappui.core.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppointmentRepo @Inject constructor() {
    private val db = Firebase.firestore
    private val collection =db.collection("appointments")

    fun getAppointmentsFlow(doctorId: String): Flow<List<AppointmentModel>> = callbackFlow {
        val listener = collection
            .whereEqualTo("doctorId", doctorId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.d("AppointmentRepo", "Listen failed.", error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val appointments = snapshot.toObjects(AppointmentModel::class.java)
                    trySend(appointments)
                } else {
                    trySend(emptyList())
                }
            }
        // Close the listener when viewmodel is cleared/screen closed
        awaitClose { listener.remove() }
    }

    suspend fun createAppointment(appointmentData: AppointmentModel): Result<Unit> {
        return try {
            // 1. Create a reference to a new document. This generates a unique ID on the client.
            //    This is the key step you described.
            val newDocRef = collection.document()

            // 2. Get the auto-generated ID from the reference.
            val docId = newDocRef.id

            val finalData = appointmentData.copy(appointmentId = docId)

            // 4. Use .set() on the reference to write the data.
            //    This creates the document with your chosen ID and with the ID field inside it.
            newDocRef.set(finalData).await()

            Result.success(Unit)// Success
        } catch (e: Exception) {
            // Important for handling network errors, permissions issues, etc.
            Result.failure(e)
        }
    }

    /**
     * Updates the status of an appointment.
    */
    suspend fun updateAppointmentStatus(appointmentId: String, newStatus: String): Boolean{
        return try {
            collection
                .document(appointmentId)
                .update("status", newStatus)
                .await()
            true
        } catch (e: Exception) {
            Log.d("AppointmentRepo", "Error updating appointment status")
            false
        }
    }

    /**
     * Gets the user data using UID
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
}