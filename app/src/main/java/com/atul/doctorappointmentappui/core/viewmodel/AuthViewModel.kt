package com.atul.doctorappointmentappui.core.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId
    fun updateUserId(userId: String?) {
        _currentUserId.value = userId
    }

    init {
        viewModelScope.launch{
            val userId = checkCurrentUser()
            updateUserId(userId)
        }
    }

    private val _UserName = MutableStateFlow("Authenticated Guest")
    val UserName: StateFlow<String> = _UserName

    fun updateUserName(name: String) {
        _UserName.value = name
    }

    suspend fun checkCurrentUser (): String? {
        val currentUser = firebaseAuth.currentUser?.uid
        updateUserId(currentUser)
        return currentUser
    }

    suspend fun signInWithGoogle(credential: Credential, openAndPopUp: (String, String) -> Unit, context: Context) {
        viewModelScope.launch {
            try{
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
//                accountService.signInWithGoogle(googleIdTokenCredential.idToken)
                    val firebaseCredential =
                        GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                    firebaseAuth.signInWithCredential(firebaseCredential).await()
//                openAndPopUp(NOTES_LIST_SCREEN, SIGN_IN_SCREEN)
                    val result = firebaseAuth.signInWithCredential(firebaseCredential).await()
                    updateUserId(result.user?.uid)
                } else {
                    updateUserId(null)
                    Log.e("AuthError", "UNEXPECTED_CREDENTIAL")
                }
            }
            catch (e: Exception) {
                updateUserId(null)
                Log.d("AuthError", e.message.orEmpty())
            }
        }
    }

    suspend fun authenticateWithEmailPassword(email: String, password: String, isLogin: Boolean, context: Context, onSuccessful: () -> Unit) {
        if (isLogin) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid
                        updateUserId(userId)
                        onSuccessful()
                        Toast.makeText(context, "Signing in successful", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        updateUserId(null)
                        Toast.makeText(context, "Signing in failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid
                        updateUserId(userId)
                        onSuccessful
                    }
                }
        }
    }

    suspend fun signOut () {
        firebaseAuth.signOut()
    }

}