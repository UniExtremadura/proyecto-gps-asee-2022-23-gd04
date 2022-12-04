package es.unex.giiis.medicinex.data.network

import es.unex.giiis.medicinex.data.model.MedicineModel

interface MedicineFirebaseClient
{
    suspend fun getCategorieMedicines(categorie : String) : MutableList<MedicineModel>

    suspend fun getMedicine(categorie : String, medicineName : String, medicineNRegister : String) : MedicineModel?

    suspend fun getMedicinesByLetter(letter : Char) : MutableList<MedicineModel>

    suspend fun getMedicineByName(name : String) : MedicineModel?
}