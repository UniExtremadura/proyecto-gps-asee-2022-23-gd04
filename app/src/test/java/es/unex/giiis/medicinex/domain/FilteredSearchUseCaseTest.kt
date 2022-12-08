package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import es.unex.giiis.medicinex.data.database.entities.FormaFarmaceutica
import es.unex.giiis.medicinex.data.database.entities.FormaFarmaceuticaSimplificada
import es.unex.giiis.medicinex.data.model.MedicineModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FilteredSearchUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var filteredSearchUseCase: FilteredSearchUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        filteredSearchUseCase = FilteredSearchUseCase(medicineRepository)
    }

    @Test
    fun `test of filtered search of medicines by query`() = runBlocking {
        // Given

        val apiretal =   MedicineModel("2", "Apiretal", "Laboratorios de apiretal SA", "Medicamento sujeto a prescripción médica", true, true, true, true, "20 ml",
            mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), FormaFarmaceutica(2, "Jarabe enbotellado"),
            FormaFarmaceuticaSimplificada(1, "Jarabe"), "Jarabe", 'a')

        val apiretal2 =  MedicineModel("3", "Apiretal de coca cola", "Laboratorios de dalsy SL", "Medicamento sujeto a prescripción médica", true, true, true, true, "20 ml",
            mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), FormaFarmaceutica(2, "Jarabe"),
            FormaFarmaceuticaSimplificada(1, "Jarabe"), "Jarabe", 'A')

        val medicines = mutableListOf(apiretal, apiretal2)

        coEvery { medicineRepository.getMedicinesByQuery("apiretal") } returns mutableListOf(apiretal, apiretal2)
        coEvery { medicineRepository.getMedicinesByQuery("apiretal de") } returns mutableListOf(apiretal2)

        // When
        val response = filteredSearchUseCase("apiretal")
        val response2 = filteredSearchUseCase("apiretal de")
        val response3 = filteredSearchUseCase("")

        // Then
        coVerify(exactly = 3) { medicineRepository.getMedicinesByQuery(any()) }
        assertTrue(response == medicines)
        assertTrue(response2 != medicines)
        assertTrue(response3.size == 0)
    }
}