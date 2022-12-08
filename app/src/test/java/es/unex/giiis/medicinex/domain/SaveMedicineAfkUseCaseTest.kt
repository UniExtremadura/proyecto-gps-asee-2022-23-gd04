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

class SaveMedicineAfkUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var saveMedicineAfkUseCase: SaveMedicineAfkUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        saveMedicineAfkUseCase = SaveMedicineAfkUseCase(medicineRepository)
    }

    @Test
    fun `save medicine into first aid kit on firebase repo`() = runBlocking {
        // Given
        coEvery { medicineRepository.saveMedicineIntoAfk(any()) } returns true

        // When
        val medicineName = "dalsy"
        val response = saveMedicineAfkUseCase(medicineName)

        // Then
        coVerify(exactly = 1) { medicineRepository.saveMedicineIntoAfk(medicineName) }
        assertTrue(response)
    }
}