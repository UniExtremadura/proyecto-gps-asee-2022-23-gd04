package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.data.model.MedicineModel
import javax.inject.Inject

class MedicineDataUseCase @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke(nRegistro : String) : MedicineModel?
    {
        return repository.getMedicineByNRegister(nRegistro)
    }
}