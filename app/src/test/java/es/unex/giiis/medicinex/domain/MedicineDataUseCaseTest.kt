package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import es.unex.giiis.medicinex.data.database.entities.FormaFarmaceutica
import es.unex.giiis.medicinex.data.database.entities.FormaFarmaceuticaSimplificada
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.data.model.MedicineModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MedicineDataUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var medicineDataUseCase: MedicineDataUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        medicineDataUseCase = MedicineDataUseCase(medicineRepository)
    }

    @Test
    fun `test retrieve medicine data by number of registry`() = runBlocking {
        // Given
        val nRegister = "123456"
        val nRegister2 = "654321"
        val med = MedicineModel(nRegister, "Dalsy", "Laboratorios de dalsy SL", "Medicamento sujeto a prescripción médica", true, true, true, true, "20 ml",
            mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), FormaFarmaceutica(2, "Jarabe enbotellado"),
            FormaFarmaceuticaSimplificada(1, "Jarabe"), "Jarabe", 'D')

        coEvery { medicineRepository.getMedicineByNRegister(nRegister) } returns med

        // When
        val response = medicineDataUseCase(nRegister)
        val response2 = medicineDataUseCase(nRegister2)

        // Then
        coVerify(exactly = 2) { medicineRepository.getMedicineByNRegister(any()) }
        assertTrue(response == med)
        assertTrue(response2 != med)
    }
}