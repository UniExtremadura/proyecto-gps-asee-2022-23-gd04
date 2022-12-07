package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import javax.inject.Inject

class DeleteMedicineInFak @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke(name : String) : Boolean
    {
        return repository.deleteMedicineFromFak(name)
    }
}