package io.loperilla.data.network

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.RegisterProvider
import io.loperilla.domain.model.auth.User
import io.loperilla.domain.model.auth.UserUpdateRequest
import io.loperilla.domain.model.getOrNull
import io.loperilla.domain.repository.AccountManager
import io.loperilla.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.network
 * Created By Manuel Lopera on 2/2/25 at 18:45
 * All rights reserved 2025
 */
class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val accountManager: AccountManager,

    ) : AuthRepository {
    override suspend fun doLogin(email: String, password: String): DomainResult<User> {
        return try {
            manageAuthResult(firebaseAuth.signInWithEmailAndPassword(email, password).await())
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    override suspend fun doRegister(provider: RegisterProvider): DomainResult<User> {
        return when (provider) {
            RegisterProvider.Google -> doRegisterWitGoogle()
            is RegisterProvider.MailPassword -> doEmailPasswordRegister(
                provider.email,
                provider.password
            )
        }
    }

    private suspend fun doEmailPasswordRegister(
        email: String,
        password: String
    ): DomainResult<User> {
        return try {
            manageAuthResult(firebaseAuth.createUserWithEmailAndPassword(email, password).await())
        } catch (ex: FirebaseAuthException) {
            ex.printStackTrace()
            val domainError = getDomainErrorByFirebaseException(ex.errorCode)
            DomainResult.Error(domainError)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    override suspend fun updateUser(user: UserUpdateRequest): DomainResult<Unit> {
        return try {
            firebaseAuth.currentUser?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(user.displayName)
                    .build()
            )
            return DomainResult.Success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    private suspend fun doRegisterWitGoogle(): DomainResult<User> {
        return try {
            val googleIdToken =
                accountManager.signInWithGoogle().getOrNull() ?: return DomainResult.Error(
                    DomainError.UnknownError()
                )
            val googleAuthCredential =
                GoogleAuthProvider.getCredential(googleIdToken.googleIdToken, null)

            manageAuthResult(firebaseAuth.signInWithCredential(googleAuthCredential).await())
        } catch (ex: FirebaseAuthException) {
            ex.printStackTrace()
            val domainError = getDomainErrorByFirebaseException(ex.errorCode)
            DomainResult.Error(domainError)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    private fun manageAuthResult(authResult: AuthResult): DomainResult<User> {
        return authResult.user?.let {
            DomainResult.Success(parseFirebaseUser(it))
        } ?: DomainResult.Error(DomainError.UnknownError())
    }

    override suspend fun doLogout(): DomainResult<Unit> {
        return try {
            firebaseAuth.signOut()
            DomainResult.Success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    override suspend fun refreshUser(): DomainResult<User> {
        return try {
            firebaseAuth.currentUser?.let {
                it.reload().await()
                DomainResult.Success(parseFirebaseUser(it))
            } ?: return DomainResult.Error(DomainError.UnknownError())
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    private fun parseFirebaseUser(user: FirebaseUser): User {
        return User(
            uid = user.uid,
            name = user.displayName.orEmpty(),
            email = user.email.orEmpty(),
            photoUrl = user.photoUrl?.toString().orEmpty()
        )
    }

    private fun getDomainErrorByFirebaseException(errorCode: String): DomainError {
        return when (errorCode) {
            FIREBASE_AUTH_ERROR_EMAIL_ALREADY_IN_USE -> DomainError.EmailAlreadyInUse()
            else -> DomainError.UnknownError()
        }
    }
}