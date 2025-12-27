package com.atul.doctorappointmentappui.feature.registerAsDoctor

import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.DoctorModel
import com.atul.doctorappointmentappui.core.viewmodel.SellerDataViewModel
import kotlinx.coroutines.launch

/**
 * Stateful composable that handles the logic for doctor profile creation.
 * It holds the form's state and communicates with the ViewModel.
 */
@Composable
fun DoctorRegistrationRoute(
    viewModel: SellerDataViewModel = hiltViewModel(),
    userId: String,
    onNavigateBack: () -> Unit,
) {
    // This holds the state of the form fields as the user types.
    var formState by remember { mutableStateOf(DoctorModel()) }

    // This derived state checks if the form is valid based on your DoctorModel logic.
    val isFormComplete by remember {
        derivedStateOf { formState.isProfileInformationFilled }
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope ()

    DoctorRegistrationScreen(
        doctorData = formState,
        onDataChange = { updatedData -> formState = updatedData },
        isSaveEnabled = isFormComplete,
        onSaveClick = { doctorData ->
            // Here you would call your ViewModel to create the profile
            // For example: viewModel.createDoctorProfile(formState)
            scope.launch{
                viewModel.createSellerProfile(userId, doctorData, context)
            }
        },
        onBackClick = onNavigateBack
    )
}

/**
 * A stateless, previewable screen for a doctor to fill in their professional details.
 *
 * @param doctorData The current state of the doctor's profile form.
 * @param onDataChange A lambda that is invoked with the updated DoctorModel when any field changes.
 * @param isSaveEnabled A boolean that controls if the 'Create Profile' button is enabled.
 * @param onSaveClick A lambda to be executed when the user clicks the save button.
 * @param onBackClick A lambda to navigate back.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorRegistrationScreen(
    doctorData: DoctorModel,
    onDataChange: (DoctorModel) -> Unit,
    isSaveEnabled: Boolean,
    onSaveClick: (DoctorModel) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Your Professional Profile") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Navigate back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(130.dp)
                    .background(Color.White, CircleShape)
                    .padding(4.dp)
                    .clip(CircleShape),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                if (doctorData.picture.isNotBlank() && doctorData.picture != "") {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(doctorData.picture)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorResource(R.color.puurple)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }

            Text(
                text = "Please provide your details to get started. All fields are required.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            // --- Form Fields ---
            val fieldModifier = Modifier.fillMaxWidth()

            OutlinedTextField(
                value = doctorData.picture,
                onValueChange = { onDataChange(doctorData.copy(picture = it)) },
                label = { Text("Picture URL") },
                modifier = fieldModifier,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = doctorData.name,
                onValueChange = { onDataChange(doctorData.copy(name = it)) },
                label = { Text("Full Name") },
                modifier = fieldModifier,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = doctorData.address,
                onValueChange = { onDataChange(doctorData.copy(address = it)) },
                label = { Text("Clinic Address") },
                modifier = fieldModifier,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = doctorData.experience.toString().takeIf { it != "0" } ?: "",
                onValueChange = { onDataChange(doctorData.copy(experience = it.toIntOrNull() ?: 0)) },
                label = { Text("Years of Experience") },
                modifier = fieldModifier,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = doctorData.location,
                onValueChange = { onDataChange(doctorData.copy(location = it)) },
//                label = { Text("City, State (e.g., New York, NY)") },
                label = { Text("Location - URL") },
                modifier = fieldModifier,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = doctorData.phone,
                onValueChange = { onDataChange(doctorData.copy(phone = it)) },
                label = { Text("Contact Phone") },
                modifier = fieldModifier,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = doctorData.patients.toString(),
                onValueChange = { onDataChange(doctorData.copy(patients = it.toIntOrNull() ?: 0)) },
                label = { Text("Patients  (e.g., 200)") },
                modifier = fieldModifier,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = doctorData.rating.toString(),
                onValueChange = { /* No value change for rating */},
                readOnly = true,
                enabled = false,
                label = { Text("Rating") },
                modifier = fieldModifier,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = doctorData.site,
                onValueChange = { onDataChange(doctorData.copy(site = it)) },
                label = { Text("Website (e.g., www.example.com)") },
                modifier = fieldModifier,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = doctorData.special,
                onValueChange = { onDataChange(doctorData.copy(special = it)) },
                label = { Text("Speciality (e.g., Cardiologist)") },
                modifier = fieldModifier,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = doctorData.biography,
                onValueChange = { onDataChange(doctorData.copy(biography = it)) },
                label = { Text("Short Biography") },
                modifier = fieldModifier.height(120.dp),
                maxLines = 4
            )

            Spacer(Modifier.height(16.dp))

            // --- Action Button ---
            Button(
                onClick = { onSaveClick(doctorData) },
                enabled = isSaveEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Create Profile")
            }
        }
    }
}

// --- Previews ---

//@Preview(showBackground = true, name = "Empty Form", heightDp = 1200)
//@Composable
//private fun DoctorRegistrationScreenEmptyPreview() {
//    MaterialTheme {
//        DoctorRegistrationScreen(
//            doctorData = DoctorModel(),
//            onDataChange = {},
//            isSaveEnabled = false, // Button is disabled
//            onSaveClick = {},
//            onBackClick = {}
//        )
//    }
//}
//
//@Preview(showBackground = true, name = "Filled Form", heightDp = 1200)
//@Composable
//private fun DoctorRegistrationScreenFilledPreview() {
//    val filledDoctor = DoctorModel(
//        name = "Dr. Jane Doe",
//        address = "123 Health St, San Francisco, CA",
//        biography = "Dedicated pediatrician with a focus on early childhood development and care.",
//        experience = 8,
//        location = "San Francisco, CA",
//        phone = "555-123-4567",
//        site = "Sunshine Pediatrics",
//        special = "Pediatrician",
//    )
//
//    MaterialTheme {
//        DoctorRegistrationScreen(
//            doctorData = filledDoctor,
//            onDataChange = {},
//            isSaveEnabled = true, // Button is enabled
//            onSaveClick = {},
//            onBackClick = {}
//        )
//    }
//}