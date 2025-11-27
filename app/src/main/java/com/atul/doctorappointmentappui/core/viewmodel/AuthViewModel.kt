package com.atul.doctorappointmentappui.core.viewmodel

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AuthViewModel: ViewModel() {

    val firebase = Firebase
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()
    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password

    suspend fun signInWithGoogle(credential: Credential, openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try{
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
//                accountService.signInWithGoogle(googleIdTokenCredential.idToken)
                    val firebaseCredential =
                        GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                    firebase.auth.signInWithCredential(firebaseCredential).await()
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

    suspend fun authenticateWithEmailPassword(email: String, password: String, isLogin: Boolean) {
        if (isLogin) {
            firebase.auth.signInWithEmailAndPassword(email, password)
        }
        else {
            firebase.auth.createUserWithEmailAndPassword(email, password)
        }
    }

}