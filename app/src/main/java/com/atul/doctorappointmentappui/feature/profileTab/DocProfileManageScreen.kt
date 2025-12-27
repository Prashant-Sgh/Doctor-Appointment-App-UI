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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.SellerDataViewModel
import com.atul.doctorappointmentappui.feature.profileTab.components.ProfileTextField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocProfileManageScreen(
    sellerDataViewModel: SellerDataViewModel,
    sellerId: String
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        sellerDataViewModel.getData(sellerId, context)
    }
    val doctor by sellerDataViewModel.sellerData.collectAsState()

    // Local editable states
    var speciality by remember(doctor) { mutableStateOf(doctor.special) }
    var site by remember (doctor) { mutableStateOf(doctor.site) }
    var rating by remember (doctor) { mutableStateOf(doctor.rating.toString()) }
    var patients by remember (doctor) { mutableStateOf(doctor.patients) }
    var phone by remember (doctor) { mutableStateOf(doctor.phone) }
    var name by remember (doctor) { mutableStateOf(doctor.name) }
    var picture by remember (doctor) { mutableStateOf(doctor.picture) }
    var location by remember (doctor) { mutableStateOf(doctor.location) }
    var experience by remember (doctor) { mutableStateOf(doctor.experience.toString()) }
    var biography by remember (doctor) { mutableStateOf(doctor.biography) }
    var address by remember (doctor) { mutableStateOf(doctor.address) }

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
                        val newData = DoctorModel(
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
                        scope.launch{
                            sellerDataViewModel.updateSellerDetails(sellerId, context, newData)
                        }
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

//@Preview(showBackground = true)
//@Composable
//private fun DocProfileManageScreenPreview() {
//    // Create a dummy doctor for the preview
//    val dummyDoctor = DoctorModel(
//        id = "1",
//        name = "Dr. Atul Singh",
//        special = "Cardiologist",
//        site = "City Hospital",
//        rating = 4.8,
//        patients = 120,
//        phone = "9876543210",
//        picture = "", // Empty for placeholder
//        location = "New York, NY",
//        experience = 10,
//        biography = "Experienced cardiologist with over 10 years of practice in treating heart diseases.",
//        address = "123 Medical Lane, NY"
//    )
//
//    // Pass it as a StateFlow
//    DocProfileManageScreen(
//        doctorFlow = MutableStateFlow(dummyDoctor),
//        onSaveConfirmed = {},
//    )
//}