package com.atul.doctorappointmentappui.core.viewmodel

import androidx.lifecycle.ViewModel
import com.atul.doctorappointmentappui.core.model.CategoryModel
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {
    private val db = FirebaseDatabase.getInstance()

    private val _category = MutableStateFlow<List<CategoryModel>>(emptyList())
    val category: StateFlow<List<CategoryModel>> = _category

    private var categoryLoaded = false

    fun loadCategory(force: Boolean = false) {
        if (categoryLoaded && !force) return

        categoryLoaded = true

        val ref = db.getReference("Category")

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val  items = mutableListOf<CategoryModel>()
                for (child in snapshot.children) {
                    child.getValue(CategoryModel::class.java)?.let { items.add(it) }
                }
                _category.value = items
            }

            override fun onCancelled(error: DatabaseError) {
                categoryLoaded = false
            }
        })
    }

    private val _doctors = MutableStateFlow<List<DoctorModel>>(emptyList())
    val doctors: StateFlow<List<DoctorModel>> = _doctors

    private var doctorsLoaded = false

    fun loadDoctors(force: Boolean = false) {
        if (doctorsLoaded && !force) return

        doctorsLoaded = true

        val ref = db.getReference("Doctors")

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<DoctorModel>()
                for (child in snapshot.children) {
                    child.getValue(DoctorModel::class.java)?.let { items.add(it) }
                }

                _doctors.value = items
            }

            override fun onCancelled(error: DatabaseError) {
                doctorsLoaded = false
            }
        })
    }

    private val _UserName = MutableStateFlow("Guest")
    val UserName: StateFlow<String> = _UserName

    fun updateUserName(name: String) {
        _UserName.value
    }

}