package com.atul.doctorappointmentappui.core.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.atul.doctorappointmentappui.core.model.CategoryModel
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Main ViewModel for the home screen.
 * Manages the state for categories, top doctors, and the current user's name.
 * Fetches data from Firebase Realtime Database.
 */
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    private val firestore = Firebase.firestore

    //<editor-fold desc="Category Section">
    private val _category = MutableStateFlow<List<CategoryModel>>(emptyList())
    /**
     * A state flow emitting the list of available doctor categories.
     */
    val category: StateFlow<List<CategoryModel>> = _category.asStateFlow()

    private var categoryLoaded = false

    /**
     * Loads the doctor categories from the database.
     * This operation is performed only once unless `force` is true.
     * @param force If true, re-fetches data even if it has been loaded before.
     */
    fun loadCategory(force: Boolean = false) {
        if (categoryLoaded && !force) return
        categoryLoaded = true

        db.getReference("Category").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<CategoryModel>()
                for (child in snapshot.children) {
                    child.getValue(CategoryModel::class.java)?.let { items.add(it) }
                }
                _category.value = items
            }

            override fun onCancelled(error: DatabaseError) {
                categoryLoaded = false // Allow retrying on failure
            }
        })
    }
    //</editor-fold>

    //<editor-fold desc="Doctors Section">
    private val _doctors = MutableStateFlow<List<DoctorModel>>(emptyList())
    /**
     * A state flow emitting the list of top doctors.
     */
    val doctors: StateFlow<List<DoctorModel>> = _doctors.asStateFlow()

    private var doctorsLoaded = false

    /**
     * Loads the list of top doctors from the database.
     * This operation is performed only once unless `force` is true.
     * @param force If true, re-fetches data even if it has been loaded before.
     */
//    fun loadDoctors(force: Boolean = false) {
//        if (doctorsLoaded && !force) return
//        doctorsLoaded = true
//
//        db.getReference("Doctors").addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val items = mutableListOf<DoctorModel>()
//                for (child in snapshot.children) {
//                    child.getValue(DoctorModel::class.java)?.let { items.add(it) }
//                }
//                _doctors.value = items
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                doctorsLoaded = false // Allow retrying on failure
//            }
//        })
//    }

    fun loadDoctors(force: Boolean = false) {
        if (doctorsLoaded && !force) return
        doctorsLoaded = true

        firestore.collection("sellers")
            .get()
            .addOnSuccessListener { doctors ->
                val items: List<DoctorModel> = doctors.toObjects(DoctorModel::class.java)
                _doctors.value = items
            }
            .addOnFailureListener { exception ->
                doctorsLoaded = false // Allow retrying on failure
                Log.d("MainViewModel","Fetching doctors failed with exception: $exception")
            }
    }

    //</editor-fold>

    //<editor-fold desc="User Section">
    private val _userName = MutableStateFlow("Guest")
    /**
     * A state flow emitting the name of the current user. Defaults to "Guest".
     */
    val userName: StateFlow<String> = _userName.asStateFlow()
}
