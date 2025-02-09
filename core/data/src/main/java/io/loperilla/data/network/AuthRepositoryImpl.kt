package io.loperilla.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.network
 * Created By Manuel Lopera on 2/2/25 at 18:45
 * All rights reserved 2025
 */
class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun doLogin(email: String, password: String): DomainResult<Unit> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                DomainResult.Success(Unit)
            } else {
                DomainResult.Error(DomainError.UnknownError())
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    override suspend fun doRegister(email: String, password: String): DomainResult<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                DomainResult.Success(Unit)
            } else {
                DomainResult.Error(DomainError.UnknownError())
            }
        } catch (ex: FirebaseAuthException) {
            ex.printStackTrace()
            val domainError = getDomainErrorByFirebaseException(ex.errorCode)
            DomainResult.Error(domainError)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
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

    override suspend fun refreshUser(): DomainResult<Unit> {
        return try {
            firebaseAuth.currentUser?.let {
                it.reload().await()
                DomainResult.Success(Unit)
            } ?: return DomainResult.Error(DomainError.UnknownError())
        } catch (ex: Exception) {
            ex.printStackTrace()
            DomainResult.Error(DomainError.UnknownError(ex))
        }
    }

    private fun getDomainErrorByFirebaseException(errorCode: String): DomainError {
        return when (errorCode) {
            FIREBASE_AUTH_ERROR_EMAIL_ALREADY_IN_USE -> DomainError.EmailAlreadyInUse()
            else -> DomainError.UnknownError()
        }
    }
}