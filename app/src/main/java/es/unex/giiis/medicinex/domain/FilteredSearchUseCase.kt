package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import es.unex.giiis.medicinex.data.model.MedicineModel
import javax.inject.Inject

class FilteredSearchUseCase @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke(query : String) : MutableList<MedicineModel>
    {
        return repository.getMedicinesByQuery(query)
    }
}