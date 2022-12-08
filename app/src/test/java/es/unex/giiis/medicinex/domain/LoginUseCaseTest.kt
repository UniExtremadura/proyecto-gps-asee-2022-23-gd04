package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var loginUseCase : LoginUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        loginUseCase = LoginUseCase(medicineRepository)
    }

    @Test
    fun `test log in with email and password`() = runBlocking {
        // Given
        coEvery { medicineRepository.logIn(any(), any()) } returns true

        // When
        val email = "josejose@gmail.com"
        val password = "jose1234"
        val response = loginUseCase(email, password)

        // Then
        coVerify(exactly = 1) { medicineRepository.logIn(email, password) }
        assertTrue(response)
    }

    @Test
    fun `test log in with wrong email and password fields`() = runBlocking {
        // Given
        coEvery { medicineRepository.logIn(any(), any()) } returns true

        // When
        val email = ""
        val password = "1234"
        val response = loginUseCase(email, password)

        // Then
        coVerify(exactly = 0) { medicineRepository.logIn(email, password) }
        assertTrue(!response)
    }
}