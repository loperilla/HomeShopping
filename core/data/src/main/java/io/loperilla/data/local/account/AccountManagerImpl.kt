package io.loperilla.data.local.account

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.loperilla.core.data.BuildConfig
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.Credential
import io.loperilla.domain.model.repository.AccountManager
import java.security.MessageDigest
import java.util.UUID

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.local.account
 * Created By Manuel Lopera on 9/3/25 at 10:59
 * All rights reserved 2025
 */
class AccountManagerImpl(
    private val context: Context,
    private val credentialManager: CredentialManager
) : AccountManager {
    override suspend fun signInWithGoogle(): DomainResult<Credential.GoogleCredential> {
        return onGetCredentialRequest {
            val getGoogleCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(getSignInWithGoogleOption())
                .build()
            val credential = credentialManager.getCredential(
                context = context,
                request = getGoogleCredentialRequest
            ).credential

            if (credential.type != GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                return@onGetCredentialRequest DomainResult.Error(DomainError.EmptyCredential)
            }

            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            DomainResult.Success(
                Credential.GoogleCredential(
                    googleIdToken
                )
            )
        }
    }

    override suspend fun signIn(): DomainResult<Credential.DefaultCredential> {
        return onGetCredentialRequest {
            val credentialResponse = credentialManager.getCredential(
                context = context,
                request = GetCredentialRequest(
                    credentialOptions = listOf(GetPasswordOption())
                )
            )

            val credential = credentialResponse.credential as? PasswordCredential
                ?: return@onGetCredentialRequest DomainResult.Error(DomainError.EmptyCredential)

            DomainResult.Success(
                Credential.DefaultCredential(
                    email = credential.id,
                    password = credential.password
                )
            )
        }
    }

    override suspend fun signUp(email: String, password: String): DomainResult<Unit> {
        return try {
            credentialManager.createCredential(
                context = context,
                request = CreatePasswordRequest(
                    id = email,
                    password = password
                )
            )
            DomainResult.Success(Unit)
        } catch (e: CreateCredentialCancellationException) {
            e.printStackTrace()
            DomainResult.Error(DomainError.CredentialCancellationError(e))
        } catch (e: CreateCredentialException) {
            e.printStackTrace()
            DomainResult.Error(DomainError.CreateCredentialsError(e))
        }
    }

    override suspend fun clearCredential(): DomainResult<Unit> {
        return try {
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            DomainResult.Error(DomainError.UnknownError(e))
        }
    }

    private suspend fun <T> onGetCredentialRequest(block: suspend () -> DomainResult<T>): DomainResult<T> {
        return try {
            block()
        } catch (e: GetCredentialCancellationException) {
            e.printStackTrace()
            DomainResult.Error(DomainError.CredentialCancellationError(e))
        } catch (e: NoCredentialException) {
            e.printStackTrace()
            DomainResult.Error(DomainError.NoCredentialError(e))
        } catch (e: GetCredentialException) {
            e.printStackTrace()
            DomainResult.Error(DomainError.GetCredentialError(e))
        }
    }

    private fun getSignInWithGoogleOption(): GetSignInWithGoogleOption {
        return GetSignInWithGoogleOption.Builder(BuildConfig.GOOGLE_CLIENT_ID)
            .setNonce(getNonce())
            .build()
    }

    private fun getNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it ->
            str + "%02x".format(it)
        }
    }
}
