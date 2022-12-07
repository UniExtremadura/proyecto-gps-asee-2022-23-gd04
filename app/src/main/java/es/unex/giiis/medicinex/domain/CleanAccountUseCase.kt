package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import javax.inject.Inject

class CleanAccountUseCase @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke(email : String) : Boolean
    {
        return repository.cleanAccount(email)
    }
}