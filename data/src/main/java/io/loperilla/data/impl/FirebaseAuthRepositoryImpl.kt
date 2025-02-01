package io.loperilla.data.impl

import com.google.firebase.auth.FirebaseAuth
import io.loperilla.onboarding_domain.repository.AuthRepository
import io.loperilla.onboarding_domain.repository.UserDataStore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.firebase.auth
 * Created By Manuel Lopera on 23/4/23 at 18:16
 * All rights reserved 2023
 */
class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userDataStore: UserDataStore
) : AuthRepository {
    override suspend fun doLogin(email: String, password: String): Result<Unit> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Login failed"))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun doRegister(email: String, password: String): Result<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Register failed"))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun doLogout(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            userDataStore.clearPreferences()
            Result.success(Unit)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun refreshUser(): Result<Unit> {
        return try {
            firebaseAuth.currentUser?.let {
                it.reload().await()
                Result.success(Unit)
            } ?: return Result.failure(Exception("User not logged in"))
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}
