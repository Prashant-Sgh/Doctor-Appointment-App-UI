package com.atul.doctorappointmentappui.core.repo

import android.util.Log
import com.atul.doctorappointmentappui.core.model.AppointmentModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppointmentRepo @Inject constructor() {
    private val db = Firebase.firestore

    fun getAppointmentsFlow(doctorId: String): Flow<List<AppointmentModel>> = callbackFlow {
        val listener = db.collection("appointments")
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

    /**
     * Updates the status of an appointment.
    */
    suspend fun updateAppointmentStatus(appointmentId: String, newStatus: String): Boolean{
        return try {
            db.collection("appointments")
                .document(appointmentId)
                .update("status", newStatus)
                .await()
            true
        } catch (e: Exception) {
            Log.d("AppointmentRepo", "Error updating appointment status")
            false
        }
    }
}