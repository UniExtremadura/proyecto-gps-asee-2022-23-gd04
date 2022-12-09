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

class CleanAccountUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var cleanAccountUseCase: CleanAccountUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        cleanAccountUseCase = CleanAccountUseCase(medicineRepository)
    }

    @Test
    fun `clean the first aid kit of an account`() = runBlocking {
        // Given
        coEvery { medicineRepository.cleanAccount(any()) } returns true

        // When
        val email = "josejose@gmail.com"
        val response = cleanAccountUseCase(email)

        // Then
        coVerify(exactly = 1) { medicineRepository.cleanAccount(email) }
        assertTrue(response)
    }
}