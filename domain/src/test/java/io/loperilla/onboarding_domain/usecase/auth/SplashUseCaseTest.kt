package io.loperilla.onboarding_domain.usecase.auth

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.auth
 * Created By Manuel Lopera on 7/9/24 at 18:49
 * All rights reserved 2024
 */
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.loperilla.onboarding_domain.repository.AuthRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SplashUseCaseTest {

    @RelaxedMockK
    private lateinit var authRepository: AuthRepository

    private lateinit var splashUseCase: SplashUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        splashUseCase = SplashUseCase(authRepository)
    }

    @Test
    fun `invoke calls refreshUser on authRepository`() = runTest {
        // Arrange
        coEvery { authRepository.refreshUser() } returns Result.success(Unit)

        // Act
        val result = splashUseCase()

        // Assert
        assertThat(result).isEqualTo(Result.success(Unit))
    }

    @Test
    fun `invoke returns failure when refreshUser fails`() = runTest {
        // Arrange
        val exception = Exception("Test exception")
        coEvery { authRepository.refreshUser() } returns Result.failure(exception)

        // Act
        val result = splashUseCase()

        // Assert
        assertThat(result).isEqualTo(Result.failure(exception))
    }
}