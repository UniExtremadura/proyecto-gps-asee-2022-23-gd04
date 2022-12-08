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

class EditProfileUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var editProfileUseCase: EditProfileUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        editProfileUseCase = EditProfileUseCase(medicineRepository)
    }

    @Test
    fun `edit profile with a given account`() = runBlocking {
        // Given
        coEvery { medicineRepository.editProfile(any()) } returns true

        // When
        val account = Account("josejose", "josejose@gmail.com", "jose1234", "josefino", mutableListOf("Parazetamol", "Dalsy", "Vispring"))
        val response = editProfileUseCase(account)

        // Then
        coVerify(exactly = 1) { medicineRepository.editProfile(account) }
        assertTrue(response)
    }
}