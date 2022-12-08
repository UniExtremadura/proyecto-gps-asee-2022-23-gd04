package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.data.MedicineRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegisterUserUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var registerUserUseCase : RegisterUserUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        registerUserUseCase = RegisterUserUseCase(medicineRepository)
    }

    @Test
    fun `test register user with account`() = runBlocking {
        // Given
        coEvery { medicineRepository.createProfile(any()) } returns true

        // When
        val account = Account("josejose", "josejose@gmail.com", "jose1234", "josefino", mutableListOf("Parazetamol", "Dalsy", "Vispring"))
        val response = registerUserUseCase(account)

        // Then
        coVerify(exactly = 1) { medicineRepository.createProfile(account) }
        assertTrue(response)
    }
}