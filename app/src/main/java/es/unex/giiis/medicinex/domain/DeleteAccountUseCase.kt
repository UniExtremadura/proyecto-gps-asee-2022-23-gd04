package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import es.unex.giiis.medicinex.data.model.MedicineModel
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke(email : String) : Boolean
    {
        return repository.deleteAccount(email)
    }
}