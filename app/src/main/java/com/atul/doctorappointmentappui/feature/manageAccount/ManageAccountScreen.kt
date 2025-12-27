package com.atul.doctorappointmentappui.feature.manageAccount

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.UserModel
import com.atul.doctorappointmentappui.core.viewmodel.UserDataViewModel
import com.atul.doctorappointmentappui.feature.manageAccount.components.GenderSelectionRow
import com.atul.doctorappointmentappui.feature.manageAccount.components.IncompleteProfileBanner
import kotlinx.coroutines.launch

/**
 * The stateful entry point for the Manage Account feature.
 * It connects to the ViewModel, manages local UI state, and handles user events.
 */
@Composable
fun ManageAccountRoute(
    viewModel: UserDataViewModel,
    userId: String,
    onSignOut: () -> Unit,
    // This would be provided by your navigation graph
    onNavigateToSellerRegistration: () -> Unit
) {
    val userData by viewModel.userData.collectAsState()

    // Local UI state that is not directly part of the data model
    var editable by rememberSaveable { mutableStateOf(false) }
    var showIncompleteProfileBanner by rememberSaveable { mutableStateOf(true) }
    val isProfileIncomplete = remember(userData) { !userData.isProfileInformationFilled }
    var formState by remember { mutableStateOf(userData) }

    LaunchedEffect(userData) {
        formState = userData
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope ()

    ManageAccountScreen(
        userData = formState,
        isEditable = editable,
        isProfileIncomplete = isProfileIncomplete && showIncompleteProfileBanner,
        onUserDataChange = { updatedUser -> formState = updatedUser },
        onEditClick = { editable = true },
        onSaveClick = {
            scope.launch{
                viewModel.updateUserDetails(userId, context, formState, onSuccess = {})
                editable = false
            }
        },
        onCancelClick = {
            // Revert changes by resetting form state to the original data
            formState = userData
            editable = false
        },
        onSignOutClick = onSignOut,
        onDismissBanner = { showIncompleteProfileBanner = false },
        // Add the new lambda for the seller button
        onBecomeSellerClick = onNavigateToSellerRegistration
    )
}


/**
 * The stateless, previewable UI for the Manage Account screen.
 * It receives all data as parameters and surfaces user events via lambdas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAccountScreen(
    userData: UserModel,
    isEditable: Boolean,
    isProfileIncomplete: Boolean,
    onUserDataChange: (UserModel) -> Unit,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onDismissBanner: () -> Unit,
    // Add the new parameter for the seller action
    onBecomeSellerClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", fontWeight = FontWeight.SemiBold) },
                actions = {
                    IconButton(onClick = onSignOutClick) {
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
            if (!isEditable) {
                FloatingActionButton(
                    onClick = onEditClick,
                    containerColor = colorResource(R.color.puurple),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.lightPuurple).copy(alpha = 0.1f))
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .animateContentSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- INCOMPLETE PROFILE BANNER ---
            IncompleteProfileBanner(
                isVisible = isProfileIncomplete,
                onDismiss = onDismissBanner,
                onActionClick = {
                    onEditClick() // Enter edit mode
                    onDismissBanner() // And hide the banner
                }
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
                    if (userData.imageURL.isNotBlank() && userData.imageURL != "null") {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(userData.imageURL)
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

            if (!userData.seller && !isEditable) {
                BecomeDoctorCard(
                    onSetupProfileClick = onBecomeSellerClick,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            if (isEditable) {
                OutlinedTextField(
                    value = userData.imageURL,
                    onValueChange = { onUserDataChange(userData.copy(imageURL = it)) },
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
                value = userData.userName,
                onValueChange = { onUserDataChange(userData.copy(userName = it)) },
                label = { Text("Full Name") },
                readOnly = !isEditable,
                modifier = fieldModifier
            )

            OutlinedTextField(
                value = userData.email,
                onValueChange = { onUserDataChange(userData.copy(email = it)) },
                label = { Text("Email") },
                readOnly = true, // Email should generally not be editable
                modifier = fieldModifier
            )

            // ---------- Age & Gender Row ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = userData.age,
                    onValueChange = { onUserDataChange(userData.copy(age = it)) },
                    label = { Text("Age") },
                    readOnly = !isEditable,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = "Gender",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                    )
                    GenderSelectionRow(
                        isMale = userData.male,
                        isEditable = isEditable,
                        onGenderSelected = { onUserDataChange(userData.copy(male = it)) }
                    )
                }
            }

            OutlinedTextField(
                value = userData.phone,
                onValueChange = { onUserDataChange(userData.copy(phone = it)) },
                label = { Text("Phone Number") },
                readOnly = !isEditable,
                modifier = fieldModifier
            )

            OutlinedTextField(
                value = userData.address,
                onValueChange = { onUserDataChange(userData.copy(address = it)) },
                label = { Text("Address") },
                readOnly = !isEditable,
                modifier = fieldModifier,
                maxLines = 3
            )

            Spacer(Modifier.height(32.dp))

            // ---------- Action Buttons ----------
            if (isEditable) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = onCancelClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = onSaveClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

// --- PREVIEW ---
@Preview(showBackground = true, name = "View Mode")
@Composable
private fun ManageAccountScreenPreview() {
    val previewUser = UserModel(
        userName = "Atul Kumar",
        email = "atul@example.com",
        age = "28",
        phone = "+1 123-456-7890",
        address = "123 Innovation Drive, Tech City",
        imageURL = "", // Intentionally blank for previewing placeholder
        male = true,
        profileCompleted = true
    )

    MaterialTheme {
        ManageAccountScreen(
            userData = previewUser,
            isEditable = false,
            isProfileIncomplete = false,
            onUserDataChange = {},
            onEditClick = {},
            onSaveClick = {},
            onCancelClick = {},
            onSignOutClick = {},
            onDismissBanner = {},
            onBecomeSellerClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Edit Mode with Incomplete Banner")
@Composable
private fun ManageAccountScreenEditModePreview() {
    val previewUser = UserModel(
        userName = "Atul Kumar",
        email = "atul@example.com",
        age = "", // Incomplete data
        phone = "", // Incomplete data
        address = "",
        imageURL = "",
        male = true,
        profileCompleted = false
    )

    MaterialTheme {
        ManageAccountScreen(
            userData = previewUser,
            isEditable = true,
            isProfileIncomplete = true,
            onUserDataChange = {},
            onEditClick = {},
            onSaveClick = {},
            onCancelClick = {},
            onSignOutClick = {},
            onDismissBanner = {},
            onBecomeSellerClick = {}
        )
    }
}