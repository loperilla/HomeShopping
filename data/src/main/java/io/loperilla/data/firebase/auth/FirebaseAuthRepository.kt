package io.loperilla.data.firebase.auth

import com.google.firebase.auth.FirebaseUser
import io.loperilla.datasource.firebase.auth.IFirebaseAuthDataSource
import io.loperilla.model.auth.AuthResult
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.firebase.auth
 * Created By Manuel Lopera on 23/4/23 at 18:16
 * All rights reserved 2023
 */
class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: IFirebaseAuthDataSource
) {
    fun getCurrentUserAuth(): FirebaseUser? = firebaseAuth.getUserAuth()
    suspend fun doLogin(email: String, password: String): AuthResult {
        return firebaseAuth.doFirebaseLogin(email, password)
    }

    suspend fun doRegister(email: String, password: String): AuthResult {
        return firebaseAuth.doFirebaseRegister(email, password)
    }
}