package com.atul.doctorappointmentappui.feature.auth

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.atul.doctorappointmentappui.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

suspend fun launchCredManButtonUI(
    context: Context,
    onRequestResult: (Credential) -> Unit
) {
    try {
        val signInWithGoogleOption = GetSignInWithGoogleOption
            .Builder(serverClientId = context.getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        val result = CredentialManager.create(context)
            .getCredential(context = context, request = request)

        onRequestResult(result.credential)
    }
    catch (e: NoCredentialException) {
        Log.d("AuthError", e.message.orEmpty())
        SnackbarManager.showMessage(context.getString(R.string.no_accounts_error))
    }
    catch (e: GetCredentialException) {
        Log.d("AuthError", e.message.orEmpty())
    }
}


suspend fun launchCredManBottomSheet(
    context: Context,
    hasFilter: Boolean = true,
    onRequestResult: (Credential) -> Unit
) {
    try {
        val googleId = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(hasFilter)
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleId)
            .build()

        val result = CredentialManager.create(context)
            .getCredential(context = context, request = request)

        onRequestResult(result.credential)
    }
    catch (e: NoCredentialException) {
        Log.d("AuthError", e.message.orEmpty())
        if (hasFilter) {
            launchCredManBottomSheet(context, hasFilter = false, onRequestResult)
        }
    }
    catch (e: GetCredentialException) {
        Log.d("AuthError", e.message.orEmpty())
    }
}


object SnackbarManager {
    private val messages: MutableStateFlow<String?> = MutableStateFlow(null)
    val snackbarMessages: StateFlow<String?>
        get() = messages

    fun showMessage(message: String) {
        messages.value = message
    }

    fun clearSnackbarState() {
        messages.value = null
    }
}