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

class DeleteAccountUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var deleteAccountUseCase: DeleteAccountUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        deleteAccountUseCase = DeleteAccountUseCase(medicineRepository)
    }

    @Test
    fun `delete an account from email`() = runBlocking {
        // Given
        coEvery { medicineRepository.deleteAccount(any()) } returns true

        // When
        val email = "josejose@gmail.com"
        val response = deleteAccountUseCase(email)

        // Then
        coVerify(exactly = 1) { medicineRepository.deleteAccount(email) }
        assertTrue(response)
    }
}