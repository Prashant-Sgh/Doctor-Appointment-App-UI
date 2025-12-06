package com.atul.doctorappointmentappui.feature.profileTab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.atul.doctorappointmentappui.core.model.DoctorModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocProfileManageScreen(
    doctor: DoctorModel,
    onSaveConfirmed: (DoctorModel) -> Unit
) {
    var speciality by remember { mutableStateOf(doctor.Special) }
    var site by remember { mutableStateOf(doctor.Site) }
    var rating by remember { mutableStateOf(doctor.Rating.toString()) }
    var patients by remember { mutableStateOf(doctor.Patiens.toString()) }
    var phone by remember { mutableStateOf(doctor.Mobile) }
    var name by remember { mutableStateOf(doctor.Name) }
    var picture by remember { mutableStateOf(doctor.Picture) }
    var location by remember { mutableStateOf(doctor.Location) }
    var experience by remember { mutableStateOf(doctor.Expriense.toString()) }
    var biography by remember { mutableStateOf(doctor.Biography) }
    var address by remember { mutableStateOf(doctor.Address) }

    var showConfirmDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Doctor Profile") }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // ----------- Profile Picture -----------
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                AsyncImage(
                    model = picture,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = picture,
                onValueChange = { picture = it },
                label = { Text("Picture URL") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // ----------- Editable Form Fields -----------

            ProfileTextField("Name", name) { name = it }
            ProfileTextField("Speciality", speciality) { speciality = it }
            ProfileTextField("Clinic / Site", site) { site = it }
            ProfileTextField("Location", location) { location = it }
            ProfileTextField("Experience (years)", experience) { experience = it }
            ProfileTextField("Rating", rating) { rating = it }
            ProfileTextField("Patients Attended", patients) { patients = it }
            ProfileTextField("Mobile", phone) { phone = it }
            ProfileTextField("Address", address) { address = it }

            OutlinedTextField(
                value = biography,
                onValueChange = { biography = it },
                label = { Text("Biography") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )

            Spacer(Modifier.height(32.dp))

            // ----------- Save Button -----------
            Button(
                onClick = { showConfirmDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Changes")
            }
        }
    }

    // ----------- Confirmation Popup Dialog -----------
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Confirm Update") },
            text = { Text("Do you want to save changes to your profile?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        onSaveConfirmed(
                            DoctorModel(
                                Special = speciality,
                                Site = site,
                                Rating = rating.toDouble(),
                                Patiens = patients,
                                Mobile = phone,
                                Name = name,
                                Picture = picture,
                                Location = location,
                                Expriense = experience.toInt(),
                                Biography = biography,
                                Address = address,
                                Id = doctor.Id // unchanged
                            )
                        )
                    }
                ) { Text("Confirm") }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    )
}