package com.atul.doctorappointmentappui.core.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AuthViewModel: ViewModel() {

    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser
    fun updateUserId(userId: FirebaseUser?) {
        _currentUser.value = userId
    }

    init {
        viewModelScope.launch{
            _currentUser.value = checkCurrentUser()
        }
    }

    private val _UserName = MutableStateFlow("Authenticated Guest")
    val UserName: StateFlow<String> = _UserName

    fun updateUserName(name: String) {
        _UserName.value = name
    }

    suspend fun checkCurrentUser (): FirebaseUser? {
        val currentUser = auth.currentUser
        updateUserId(currentUser)
        return currentUser
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    suspend fun signInWithGoogle(credential: Credential, openAndPopUp: (String, String) -> Unit, context: Context) {
        viewModelScope.launch {
            try{
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
//                accountService.signInWithGoogle(googleIdTokenCredential.idToken)
                    val firebaseCredential =
                        GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                    auth.signInWithCredential(firebaseCredential).await()
//                openAndPopUp(NOTES_LIST_SCREEN, SIGN_IN_SCREEN)
                } else {
                    Log.e("AuthError", "UNEXPECTED_CREDENTIAL")
                }
            }
            catch (e: Exception) {
                Log.d("AuthError", e.message.orEmpty())
            }
        }
    }

    suspend fun authenticateWithEmailPassword(email: String, password: String, isLogin: Boolean, context: Context) {
        if (isLogin) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser
                        updateUserId(userId)
                        Toast.makeText(context, "Signing in successful", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        updateUserId(null)
                        Toast.makeText(context, "Signing in failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else {
            auth.createUserWithEmailAndPassword(email, password)
        }
    }

}