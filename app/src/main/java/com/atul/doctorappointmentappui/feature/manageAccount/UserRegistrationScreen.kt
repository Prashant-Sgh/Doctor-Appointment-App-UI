package com.atul.doctorappointmentappui.feature.manageAccount

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.feature.manageAccount.components.GenderSelectionRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteProfileScreen(
    // We pass the partial user data (e.g. email/name from Google Auth)
    initialUser: UserModel,
    onProfileCompleted: (UserModel) -> Unit
) {
    val context  = LocalContext.current
    // Form State (Pre-filled with whatever Google/Auth provided)
    var userName by remember { mutableStateOf(initialUser.userName) }
    var userEmail by remember { mutableStateOf(initialUser.email) }
    var userImage by remember { mutableStateOf(initialUser.imageURL) }

    // Fields that need to be filled
    var userAge by remember { mutableStateOf("") }
    var userPhone by remember { mutableStateOf("") }
    var userAddress by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(true) } // true = Male

    // Error States
    var isNameError by remember { mutableStateOf(false) }
    var isAgeError by remember { mutableStateOf(false) }
    var isPhoneError by remember { mutableStateOf(false) }
    var isAddressError by remember { mutableStateOf(false) }
    var isImageError by remember { mutableStateOf(false) }

    LaunchedEffect(initialUser) {
        userName = initialUser.userName
        userEmail = initialUser.email
        userImage = initialUser.imageURL
        userAge = initialUser.age
        userPhone = initialUser.phone
        userAddress = initialUser.address
        gender = initialUser.male
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Complete Your Profile", fontWeight = FontWeight.SemiBold) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Please provide a few more details to get started.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // ---------- Profile Picture ----------
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.padding(bottom = 24.dp)
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
                    if (userImage.isNotBlank() && userImage != "null") {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(userImage)
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
            }

            // Image URL Input (Optional)
            OutlinedTextField(
                value = userImage,
                onValueChange = { userImage = it },
                label = { Text("Profile Image URL (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(16.dp))

            // ---------- Form Fields ----------
            val fieldModifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)

            // Name
            OutlinedTextField(
                value = userName,
                onValueChange = {
                    userName = it
                    if(it.isNotBlank()) isNameError = false
                },
                label = { Text("Full Name") },
                isError = isNameError,
                modifier = fieldModifier
            )
            if (isNameError) Text("Name is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)

            // Email (Read Only - from Auth)
            OutlinedTextField(
                value = userEmail,
                onValueChange = { },
                label = { Text("Email") },
                readOnly = true,
                enabled = false,
                modifier = fieldModifier
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                // Age
                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    OutlinedTextField(
                        value = userAge,
                        onValueChange = {
                            // Only allow numbers
                            if (it.all { char -> char.isDigit() }) {
                                userAge = it
                                if(it.isNotBlank()) isAgeError = false
                            }
                        },
                        label = { Text("Age") },
                        isError = isAgeError,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (isAgeError) Text("Required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
                }

                // Gender
                GenderSelectionRow(
                    isMale = gender,
                    isEditable = true,
                    onGenderSelected = { selectedMale -> gender = selectedMale }
                )
            }

            // Phone
            OutlinedTextField(
                value = userPhone,
                onValueChange = {
                    userPhone = it
                    if(it.isNotBlank()) isPhoneError = false
                },
                label = { Text("Phone Number") },
                isError = isPhoneError,
                modifier = fieldModifier
            )
            if (isPhoneError) Text("Phone is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)

            // Address
            OutlinedTextField(
                value = userAddress,
                onValueChange = {
                    userAddress = it
                    if(it.isNotBlank()) isAddressError = false
                },
                label = { Text("Full Address") },
                isError = isAddressError,
                modifier = fieldModifier,
                maxLines = 3
            )
            if (isAddressError) Text("Address is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)

            Spacer(Modifier.height(32.dp))

            // Submit Button
            Button(
                onClick = {
                    // Validation
                    isNameError = userName.isBlank()
                    isAgeError = userAge.isBlank()
                    isPhoneError = userPhone.isBlank()
                    isAddressError = userAddress.isBlank()
                    isImageError = userImage.isBlank()

                    val hasError = isNameError || isAgeError || isPhoneError || isAddressError || isImageError

                    if (!hasError) {
                        val completeUser = initialUser.copy(
                            userName = userName,
                            imageURL = userImage,
                            age = userAge,
                            phone = userPhone,
                            address = userAddress,
                            male = gender,
                            profileCompleted = true // IMPORTANT: Mark as complete
                        )
                        onProfileCompleted(completeUser)
                    }
                    else {
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.puurple))
            ) {
                Text("Complete Registration")
            }
        }
    }
}
