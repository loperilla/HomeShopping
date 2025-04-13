package io.loperilla.data.repository

import io.loperilla.data.local.database.dao.UserDao
import io.loperilla.data.local.database.entities.UserEntity
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.User
import io.loperilla.domain.repository.LocalDataRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.repository
 * Created By Manuel Lopera on 22/2/25 at 11:07
 * All rights reserved 2025
 */
class LocalDataRepositoryImpl(
    private val userDao: UserDao
) : LocalDataRepository {
    override suspend fun persistUser(user: User): DomainResult<Unit> {
        return try {
            userDao.insertUser(user.toEntity())
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            DomainResult.Error(DomainError.UnknownError(e))
        }
    }

    override suspend fun getUser(): DomainResult<User> {
        return try {
            userDao.getUser()?.let {
                DomainResult.Success(it.toDomain())
            } ?: run {
                DomainResult.Error(DomainError.EmptyUser)
            }
        } catch (e: Exception) {
            DomainResult.Error(DomainError.UnknownError(e))
        }
    }

    override suspend fun clearUser(): DomainResult<Unit> {
        return try {
            userDao.deleteAll()
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            DomainResult.Error(DomainError.UnknownError(e))
        }
    }

    private fun User.toEntity(): UserEntity = UserEntity(uid, name, email, photoUrl)

    private fun UserEntity.toDomain(): User = User(uid, name, email, photoUrl)

}