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

class LogOutUseCaseTest
{
    @RelaxedMockK
    private lateinit var medicineRepository: MedicineRepository

    lateinit var logOutUseCase: LogOutUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        logOutUseCase = LogOutUseCase(medicineRepository)
    }

    @Test
    fun `loggin out test`() = runBlocking {
        // Given
        coEvery { medicineRepository.logOut() } returns Unit

        // When
        val response = logOutUseCase()

        // Then
        coVerify(exactly = 1) { medicineRepository.logOut() }
        assertTrue(response == Unit)
    }
}