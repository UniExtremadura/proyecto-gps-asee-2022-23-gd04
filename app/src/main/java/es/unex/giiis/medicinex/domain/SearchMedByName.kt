package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.data.MedicineRepository
import es.unex.giiis.medicinex.data.model.MedicineModel
import javax.inject.Inject

class SearchMedByName @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke(name : String) : MedicineModel?
    {
        return repository.getMedicineByName(name)
    }
}