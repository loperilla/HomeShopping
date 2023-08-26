package io.loperilla.datasource.firebase.auth

import com.google.firebase.auth.FirebaseUser
import io.loperilla.model.auth.AuthResult

interface IFirebaseAuthDataSource {
    suspend fun doFirebaseLogin(email: String, password: String): AuthResult

    fun getUserAuth(): FirebaseUser?
    suspend fun doFirebaseRegister(email: String, password: String): AuthResult
    suspend fun doLogout()
    suspend fun reloadUser(): Boolean
}