package com.atul.doctorappointmentappui.feature.profileTab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.feature.profileTab.components.ProfileTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BecomeSellerScreen(
    onRegisterClicked: (DoctorModel) -> Unit,
    onNavigateBack: () -> Unit
) {
    // Form Data State
    var name by remember { mutableStateOf("") }
    var speciality by remember { mutableStateOf("") }
    var site by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var picture by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var biography by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    // Error State (Tracks if a field is invalid)
    var isNameError by remember { mutableStateOf(false) }
    var isSpecialityError by remember { mutableStateOf(false) }
    var isSiteError by remember { mutableStateOf(false) }
    var isPhoneError by remember { mutableStateOf(false) }
    var isLocationError by remember { mutableStateOf(false) }
    var isExperienceError by remember { mutableStateOf(false) }
    var isBiographyError by remember { mutableStateOf(false) }
    var isAddressError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Become a Doctor") },
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) { Text("Cancel") }
                }
            )
        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text(
                text = "Join our network of specialists.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // ---------- Profile Picture Section ----------
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(120.dp)
                        .background(colorResource(R.color.puurple), CircleShape)
                        .clip(CircleShape),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    if (picture.isNotEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(picture).crossfade(true).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) { Text("No Image", style = MaterialTheme.typography.labelSmall) }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = picture,
                onValueChange = { picture = it },
                label = { Text("Profile Picture URL") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // ----------- Registration Fields with Error Handling -----------

            // We create a helper lambda to make the code cleaner below
            val commonModifier = Modifier.fillMaxWidth()

            // Name
            ProfileTextField("Full Name", name, onValueChange = {
                name = it
                if (it.isNotBlank()) isNameError = false // Clear error when user types
            }, null)
            if (isNameError) Text("Name is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)

            // Speciality
            ProfileTextField("Speciality (e.g. Cardiologist)", speciality, onValueChange = {
                speciality = it
                if (it.isNotBlank()) isSpecialityError = false
            }, null)
            if (isSpecialityError) Text("Speciality is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)

            // Site
            ProfileTextField("Clinic / Hospital Name", site, onValueChange = {
                site = it
                if (it.isNotBlank()) isSiteError = false
            }, null)
            if (isSiteError) Text("Clinic name is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)

            // Location
            ProfileTextField("City / Location", location, onValueChange = {
                location = it
                if (it.isNotBlank()) isLocationError = false
            }, null)
            if (isLocationError) Text("Location is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)

            // Experience
            ProfileTextField(
                label = "Years of Experience",
                value = experience,
                onValueChange = null,
                onIntValueChange = {
                    experience = it.toString()
                    isExperienceError = false
                }
            )
            if (isExperienceError) Text("Experience is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)

            // Phone
            ProfileTextField("Mobile Number", phone, onValueChange = {
                phone = it
                if (it.isNotBlank()) isPhoneError = false
            }, null)
            if (isPhoneError) Text("Phone number is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)

            // Address
            ProfileTextField("Full Address", address, onValueChange = {
                address = it
                if (it.isNotBlank()) isAddressError = false
            }, null)
            if (isAddressError) Text("Address is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)

            // Biography
            OutlinedTextField(
                value = biography,
                onValueChange = {
                    biography = it
                    if (it.isNotBlank()) isBiographyError = false
                },
                label = { Text("Professional Biography") },
                isError = isBiographyError, // Built-in error state support
                placeholder = { Text("Tell patients about your background...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )
            if (isBiographyError) Text("Biography is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)

            Spacer(Modifier.height(32.dp))

            // ----------- Submit Logic -----------
            Button(
                onClick = {
                    // 1. Reset all errors
                    isNameError = name.isBlank()
                    isSpecialityError = speciality.isBlank()
                    isSiteError = site.isBlank()
                    isPhoneError = phone.isBlank()
                    isLocationError = location.isBlank()
                    isExperienceError = experience.isBlank()
                    isBiographyError = biography.isBlank()
                    isAddressError = address.isBlank()

                    // 2. Check if ANY error exists
                    val hasError = isNameError || isSpecialityError || isSiteError ||
                            isPhoneError || isLocationError || isExperienceError ||
                            isBiographyError || isAddressError

                    // 3. Only proceed if NO errors
                    if (!hasError) {
                        val newDoctorProfile = DoctorModel(
                            special = speciality,
                            site = site,
                            rating = 5.0,
                            patients = 0,
                            phone = phone,
                            name = name,
                            picture = picture,
                            location = location,
                            experience = experience.toIntOrNull() ?: 0,
                            biography = biography,
                            address = address,
                            id = 0
                        )
                        onRegisterClicked(newDoctorProfile)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp)
                // Remove 'enabled = ...' so the button is always clickable
            ) {
                Text("Submit Application")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BecomeSellerScreenPreview() {
    BecomeSellerScreen(
        onRegisterClicked = {},
        onNavigateBack = {}
    )
}

