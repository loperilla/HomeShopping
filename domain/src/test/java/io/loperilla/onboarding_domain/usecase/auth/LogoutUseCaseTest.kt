package io.loperilla.onboarding_domain.usecase.auth

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.auth
 * Created By Manuel Lopera on 7/9/24 at 18:50
 * All rights reserved 2024
 */

import assertk.assertThat
import assertk.assertions.isSuccess
import io.loperilla.onboarding_domain.repository.AuthRepository
import io.loperilla.onboarding_domain.usecase.query.QueryModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LogoutUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: AuthRepository

    @RelaxedMockK
    private lateinit var queryModel: QueryModel
    private lateinit var logoutUseCase: LogoutUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        logoutUseCase = LogoutUseCase(repository, queryModel)
    }

    @Test
    fun `invoke should return success when logout and delete all queryModel succeed`() = runTest {
        // Given
        coEvery { repository.doLogout() } returns Result.success(Unit)
        coEvery { queryModel.deleteAll() } returns Unit

        // When
        val result = logoutUseCase()

        // Then
        assertThat(result).isSuccess()
    }

    @Test
    fun `invoke should return failure when logout fails`() = runTest {
        // Given
        var exception = Exception("Logout failed")
        coEvery { repository.doLogout() } returns Result.failure(exception)
        coEvery { queryModel.deleteAll() } returns Unit

        // When
        val result = logoutUseCase()

        // Then
        assertThat(result).isSuccess()
    }
}