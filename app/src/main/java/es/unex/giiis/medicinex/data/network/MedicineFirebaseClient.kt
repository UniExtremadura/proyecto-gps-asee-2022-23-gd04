package es.unex.giiis.medicinex.data.network

import es.unex.giiis.medicinex.data.model.MedicineModel

interface MedicineFirebaseClient
{
    suspend fun getCategorieMedicines(categorie : String) : List<MedicineModel>

    suspend fun getMedicine(categorie : String, medicineName : String, medicineNRegister : String) : MedicineModel

    suspend fun getMedicinesByLetter(letter : String) : List<MedicineModel>
}