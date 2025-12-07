package com.atul.doctorappointmentappui.feature.profileTab

import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocProfileManageScreen(
    doctor: DoctorModel,
    onSaveConfirmed: (DoctorModel) -> Unit
) {
    var speciality by remember { mutableStateOf(doctor.special) }
    var site by remember { mutableStateOf(doctor.site) }
    var rating by remember { mutableStateOf(doctor.rating.toString()) }
    var patients by remember { mutableStateOf(doctor.patients) }
    var phone by remember { mutableStateOf(doctor.phone) }
    var name by remember { mutableStateOf(doctor.name) }
    var picture by remember { mutableStateOf(doctor.picture) }
    var location by remember { mutableStateOf(doctor.location) }
    var experience by remember { mutableStateOf(doctor.experience.toString()) }
    var biography by remember { mutableStateOf(doctor.biography) }
    var address by remember { mutableStateOf(doctor.address) }

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
                    .background(
                        color = colorResource(R.color.puurple),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(picture).crossfade(true).build(),
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

            ProfileTextField("Name", name, onValueChange = { name = it }, null)
            ProfileTextField("Speciality", speciality, onValueChange = { speciality = it }, null)
            ProfileTextField("Clinic / Site", site, onValueChange = { site = it }, null)
            ProfileTextField("Location", location, onValueChange = { location = it }, null)
            ProfileTextField("Experience (years)", experience, onValueChange = { experience = it }, null)
            ProfileTextField("Rating", rating, onValueChange = { rating = it }, null)
            ProfileTextField("Patients Attended", "$patients", onValueChange = null, { { patients = it } })
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
                                special = speciality,
                                site = site,
                                rating = rating.toDouble(),
                                patients = patients,
                                phone = phone,
                                name = name,
                                picture = picture,
                                location = location,
                                experience = experience.toInt(),
                                biography = biography,
                                address = address,
                                id = doctor.id // unchanged
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
fun ProfileTextField(label: String, value: String, onValueChange: ((String) -> Unit)?, onIntValueChange: ((Int) -> Unit)?) {
    if (onValueChange != null) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )
    }
    else if (onIntValueChange !=null) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onIntValueChange(newValue.toIntOrNull()?: 0)

            },
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )
    }

}