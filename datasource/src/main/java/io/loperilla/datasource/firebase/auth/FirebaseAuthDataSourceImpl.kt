package io.loperilla.datasource.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.loperilla.datasource.datastore.DataStoreUtils.Constants.UID_PREFERENCE
import io.loperilla.datasource.datastore.IUserDataStoreDataSource
import io.loperilla.model.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthDataSourceImpl @Inject constructor(
    private val dataStore: IUserDataStoreDataSource
) : IFirebaseAuthDataSource {
    private val TAG = FirebaseAuthDataSourceImpl::class.java.simpleName
    private val auth: FirebaseAuth
        get() = Firebase.auth

    private var currentAuthUser: FirebaseUser? = null
        private set(value) {
            field = value
            runBlocking {
                saveAuthToken(value)
            }
        }

    private suspend fun saveAuthToken(firebaseUser: FirebaseUser?) = withContext(Dispatchers.IO) {
        firebaseUser?.let {
            dataStore.insertString(UID_PREFERENCE, firebaseUser.uid)
        }
    }

    override suspend fun doFirebaseLogin(
        email: String,
        password: String
    ): AuthResult {
        return try {
            this@FirebaseAuthDataSourceImpl.currentAuthUser =
                auth.signInWithEmailAndPassword(email, password).await().user
            AuthResult.AuthSuccess
        } catch (ex: Exception) {
            AuthResult.AuthFail(ex.message ?: "Hubo un error")
        }
    }

    override fun getUserAuth(): FirebaseUser? = this.auth.currentUser
    override suspend fun doFirebaseRegister(email: String, password: String): AuthResult {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            saveAuthToken(authResult.user)
            AuthResult.AuthSuccess
        } catch (ex: Exception) {
            AuthResult.AuthFail(ex.message ?: "Error al registrar usuario")
        }
    }

    override suspend fun doLogout() {
        withContext(Dispatchers.IO) {
            auth.signOut()
            dataStore.clearUid()
        }
    }

    override suspend fun reloadUser(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                auth.currentUser?.reload()?.await()
                true
            } catch (ex: Exception) {
                ex.printStackTrace()
                false
            }
        }
    }
}
