package com.atul.doctorappointmentappui.feature.profileTab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.DoctorModel
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocProfileManageScreen(
    doctorFlow: StateFlow<DoctorModel>,
    onSaveConfirmed: (DoctorModel) -> Unit
) {
    val doctor by doctorFlow.collectAsState()

    // Local editable states
    var speciality by remember { mutableStateOf("") }
    var site by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var patients by remember { mutableStateOf(0) }
    var phone by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var picture by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var biography by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    // Sync form fields from doctor object ONLY when doctor changes
    LaunchedEffect(doctor) {
        speciality = doctor.special
        site = doctor.site
        rating = doctor.rating.toString()
        patients = doctor.patients
        phone = doctor.phone
        name = doctor.name
        picture = doctor.picture
        location = doctor.location
        experience = doctor.experience.toString()
        biography = doctor.biography
        address = doctor.address
    }

    var showConfirmDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Manage Doctor Profile") })
        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // ---------- Profile Picture ----------
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(120.dp)
                    .background(colorResource(R.color.puurple), CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(picture)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(12.dp))

            // Picture URL
            OutlinedTextField(
                value = picture,
                onValueChange = { picture = it },
                label = { Text("Picture URL") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // ----------- Editable Fields -----------

            ProfileTextField("Name", name, onValueChange = { name = it }, null)

            ProfileTextField("Speciality", speciality, onValueChange = { speciality = it }, null)

            ProfileTextField("Clinic / Site", site, onValueChange = { site = it }, null)

            ProfileTextField("Location", location, onValueChange = { location = it }, null)

            ProfileTextField(
                label = "Experience (years)",
                value = experience,
                onValueChange = null,
                onIntValueChange = { experience = it.toString() }
            )

            ProfileTextField(
                label = "Patients Attended",
                value = patients.toString(),
                onValueChange = null,
                onIntValueChange = { patients = it }
            )

            ProfileTextField("Rating", rating, onValueChange = { rating = it }, null)

            ProfileTextField("Mobile", phone, onValueChange = { phone = it }, null)

            ProfileTextField("Address", address, onValueChange = { address = it }, null)

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

            // Save Button
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

    // ---------- Confirm Dialog ----------
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
                                special = speciality,
                                site = site,
                                rating = rating.toDoubleOrNull() ?: 0.0,
                                patients = patients,
                                phone = phone,
                                name = name,
                                picture = picture,
                                location = location,
                                experience = experience.toIntOrNull() ?: 0,
                                biography = biography,
                                address = address,
                                id = doctor.id // keep original
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
fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: ((String) -> Unit)?,
    onIntValueChange: ((Int) -> Unit)?
) {
    if (onValueChange != null) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )

    } else if (onIntValueChange != null) {

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onIntValueChange(newValue.toIntOrNull() ?: 0)
            },
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )
    }
}
