package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import es.unex.giiis.medicinex.data.database.entities.FormaFarmaceutica
import es.unex.giiis.medicinex.data.database.entities.FormaFarmaceuticaSimplificada
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SaveMedicineLocalUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var saveMedicineLocalUseCase: SaveMedicineLocalUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        saveMedicineLocalUseCase = SaveMedicineLocalUseCase(medicineRepository)
    }

    @Test
    fun `save medicine into the local database to avoid future researches`() = runBlocking {
        // Given
        val result : Long = 1
        coEvery { medicineRepository.insertMedicineInLocal(any()) } returns result

        // When
        val med = MedicinaEntity("123456", "Dalsy", "Laboratorios de dalsy SL", "Medicamento sujeto a prescripción médica", true, true, true, true, "20 ml",
            mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), FormaFarmaceutica(2, "Jarabe enbotellado"),
            FormaFarmaceuticaSimplificada(1, "Jarabe"), "Jarabe", 'D')

        val response = saveMedicineLocalUseCase(med)

        // Then
        coVerify(exactly = 1) { medicineRepository.insertMedicineInLocal(med) }
        assertTrue(response == result)
    }
}