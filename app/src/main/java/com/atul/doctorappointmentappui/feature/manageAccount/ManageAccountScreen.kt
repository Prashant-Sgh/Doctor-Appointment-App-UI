package com.atul.doctorappointmentappui.feature.manageAccount

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.atul.doctorappointmentappui.feature.manageAccount.components.GenderOption
import com.atul.doctorappointmentappui.feature.manageAccount.components.IncompleteProfileBanner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAccountScreen(
    userDataFlow: StateFlow<UserModel>,
    saveUserData: (UserModel) -> Unit,
    signOutUser: () -> Unit
) {
    val userData by userDataFlow.collectAsState()

    // Local State - Initialize with CURRENT userData
    var userImage by remember(userData) { mutableStateOf(userData.imageURL) }
    var userName by remember(userData) { mutableStateOf(userData.userName) }
    var userAge by remember(userData) { mutableStateOf(userData.age) }
    var userPhone by remember(userData) { mutableStateOf(userData.phone) }
    var userEmail by remember(userData) { mutableStateOf(userData.email) }
    var userAddress by remember(userData) { mutableStateOf(userData.address) }
    var gender by remember(userData) { mutableStateOf(userData.male) } // true = Male

    // UI State
    var editable by remember { mutableStateOf(false) }

    // --- NOTIFICATION STATE ---
    // Check if profile is technically incomplete (you can adjust this logic)
    val isProfileIncomplete = remember(userData) {
        !userData.profileCompleted || userData.age.isBlank() || userData.address.isBlank()
    }

    // Allow user to dismiss it for this session
    var showNotification by remember { mutableStateOf(true) }

    // REACTIVE UPDATE
    LaunchedEffect(userData) {
        userImage = userData.imageURL
        userName = userData.userName
        userAge = userData.age
        userPhone = userData.phone
        userEmail = userData.email
        userAddress = userData.address
        gender = userData.male
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", fontWeight = FontWeight.SemiBold) },
                actions = {
                    IconButton(onClick = signOutUser) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Sign Out",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (!editable) {
                FloatingActionButton(
                    onClick = { editable = true },
                    containerColor = colorResource(R.color.puurple),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                }
            }
        }
    ) { padding ->

        Box(modifier = Modifier.fillMaxSize()
            .padding(padding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.lightPuurple).copy(alpha = 0.1f))
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                if (editable) {
                    OutlinedTextField(
                        value = userImage,
                        onValueChange = { userImage = it },
                        label = { Text("Profile Image URL") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(Modifier.height(16.dp))
                }

                // ---------- Form Fields ----------
                val fieldModifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)

                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Full Name") },
                    readOnly = !editable,
                    modifier = fieldModifier
                )

                OutlinedTextField(
                    value = userEmail,
                    onValueChange = { userEmail = it },
                    label = { Text("Email") },
                    readOnly = !editable,
                    modifier = fieldModifier
                )

                // ---------- Age & Gender Row ----------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Age Field
                    OutlinedTextField(
                        value = userAge,
                        onValueChange = { userAge = it },
                        label = { Text("Age") },
                        readOnly = !editable,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )

                    // Custom Gender Selection
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        // Small Label similar to OutlineTextField label
                        Text(
                            text = "Gender",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                        )

                        GenderSelectionRow(
                            isMale = gender,
                            isEditable = editable,
                            onGenderSelected = { selectedMale -> gender = selectedMale }
                        )
                    }
                }

                OutlinedTextField(
                    value = userPhone,
                    onValueChange = { userPhone = it },
                    label = { Text("Phone Number") },
                    readOnly = !editable,
                    modifier = fieldModifier
                )

                OutlinedTextField(
                    value = userAddress,
                    onValueChange = { userAddress = it },
                    label = { Text("Address") },
                    readOnly = !editable,
                    modifier = fieldModifier,
                    maxLines = 3
                )

                Spacer(Modifier.height(32.dp))

                // ---------- Action Buttons ----------
                if (editable) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                editable = false
                                // Revert changes
                                userImage = userData.imageURL
                                userName = userData.userName
                                userAge = userData.age
                                userPhone = userData.phone
                                userEmail = userData.email
                                userAddress = userData.address
                                gender = userData.male
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Cancel")
                        }

                        Button(
                            onClick = {
                                val newData = UserModel(
                                    address = userAddress,
                                    age = userAge,
                                    email = userEmail,
                                    imageURL = userImage,
                                    male = gender,
                                    phone = userPhone,
                                    totalAppointments = userData.totalAppointments,
                                    userName = userName,
                                    seller = userData.seller,
                                    profileCompleted = true
                                )
                                saveUserData(newData)
                                editable = false
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.puurple))
                        ) {
                            Text("Save")
                        }
                    }
                }
            }

            // --- INSERT BANNER HERE ---
            // It will sit at the top of the scrollable content
            IncompleteProfileBanner(
                isVisible = showNotification && isProfileIncomplete,
                onDismiss = { showNotification = false },
                onActionClick = {
                    editable = true // Automatically open edit mode
                    showNotification = false // Hide banner since they are acting on it
                }
            )

        }
    }
}

// -------------------------------------------------------------------------
// Custom Composable for Gender Selection
// -------------------------------------------------------------------------
@Composable
fun GenderSelectionRow(
    isMale: Boolean,
    isEditable: Boolean,
    onGenderSelected: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Match standard TextField height
            .clip(RoundedCornerShape(4.dp)) // Match TextField default corners
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Male Button
        GenderOption(
            text = "Male",
            icon = Icons.Default.Male,
            isSelected = isMale,
            isEditable = isEditable,
            modifier = Modifier.weight(1f),
            onClick = { onGenderSelected(true) }
        )

        // Female Button
        GenderOption(
            text = "Female",
            icon = Icons.Default.Female,
            isSelected = !isMale,
            isEditable = isEditable,
            modifier = Modifier.weight(1f),
            onClick = { onGenderSelected(false) }
        )
    }
}

//@Preview
//@Composable
//private fun ManageAccountScreenPreview() {
//    val dummyUser = UserModel(
//        userName = "Atul",
//        email = "atul@example.com",
//        age = "25",
//        phone = "1234567890",
//        address = "123 Main St",
//        imageURL = "",
//        male = true
//    )
//    ManageAccountScreen(
//        userDataFlow = MutableStateFlow(dummyUser),
//        saveUserData = {},
//        signOutUser = {}
//    )
//}