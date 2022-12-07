package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import javax.inject.Inject

class GetFakMedicines @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke() : MutableList<String>
    {
        return repository.getFakMedicines()
    }
}