package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import es.unex.giiis.medicinex.data.database.entities.CategorieEntity
import es.unex.giiis.medicinex.data.model.MedicineModel
import javax.inject.Inject

class GetSectionMedicines @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke(sectionName : String) : MutableList<MedicineModel>
    {
        return repository.getMedicinesBySection(CategorieEntity(sectionName))
    }
}