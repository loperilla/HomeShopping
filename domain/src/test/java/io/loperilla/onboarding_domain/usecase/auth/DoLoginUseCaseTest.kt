package io.loperilla.onboarding_domain.usecase.auth

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.auth
 * Created By Manuel Lopera on 7/9/24 at 18:29
 * All rights reserved 2024
 */
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.loperilla.onboarding_domain.repository.AuthRepository
import io.loperilla.testutils.validEmail
import io.loperilla.testutils.validPassword
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DoLoginUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: AuthRepository

    private lateinit var useCase: DoLoginUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DoLoginUseCase(repository)
    }

    @Test
    fun `test successful login`() = runTest {
        // Arrange
        val email = validEmail
        val password = validPassword
        coEvery { repository.doLogin(email, password) } returns Result.success(Unit)

        // Act
        val result = useCase(email, password)

        // Assert
        assertThat(result).isEqualTo(Result.success(Unit))
    }

    @Test
    fun `test failed login`() = runTest {
        // Arrange
        val email = validEmail
        val password = validPassword
        val exception = Exception("Login failed")
        coEvery { repository.doLogin(email, password) } returns Result.failure(exception)

        // Act
        val result = useCase(email, password)

        // Assert
        assertThat(result).isEqualTo(Result.failure(exception))
    }
}