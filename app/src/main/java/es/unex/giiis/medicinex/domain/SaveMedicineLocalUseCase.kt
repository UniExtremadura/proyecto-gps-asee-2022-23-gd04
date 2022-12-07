package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import javax.inject.Inject

class SaveMedicineLocalUseCase @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke(med : MedicinaEntity) : Long
    {
        return repository.insertMedicineInLocal(med)
    }
}