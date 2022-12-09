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

class RecoverPasswordUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var recoverPasswordUseCase: RecoverPasswordUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        recoverPasswordUseCase = RecoverPasswordUseCase(medicineRepository)
    }

    @Test
    fun `recover password by sending reset password mail`() = runBlocking {
        // Given
        coEvery { medicineRepository.sendResetPasswordMail(any()) } returns true

        // When
        val email = "josejose@gmail.com"
        val response = recoverPasswordUseCase(email)

        // Then
        coVerify(exactly = 1) { medicineRepository.sendResetPasswordMail(email) }
        assertTrue(response)
    }
}