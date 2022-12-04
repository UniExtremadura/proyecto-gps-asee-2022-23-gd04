package es.unex.giiis.medicinex.data.network

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.CatalogueProvider
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.data.database.entities.CategorieEntity
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.data.database.entities.toModel
import es.unex.giiis.medicinex.data.model.MedicineModel
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MedicineFBService
(
    private val auth : FirebaseAuth,
    private val realtime : FirebaseDatabase
) : MedicineFirebaseClient
{
    override suspend fun getCategorieMedicines(categorie : String) : MutableList<MedicineModel>
    {
        var medicines : MutableList<MedicineModel> = mutableListOf()

        if(MedicinexApp.isThereInternet)
        {
            withContext(Dispatchers.Main)
            {
                //Toast.makeText(binding.sectionName.context, R.string.downloading_data_please_wait, Toast.LENGTH_SHORT).show()

                val categoria = Firebase.database.getReference(categorie).get().await()

                for(letra in categoria.children)
                {
                    for(medicina in letra.children)
                    {
                        medicines.add(GeneralUtilities.parseMedicine(medicina).toModel())
                        //adapter.notifyItemInserted(categorieMedicines.size - 1)
                    }
                }
            }
        }
        else
        {
            withContext(Dispatchers.Main)
            {
                //ScreenMessages.noInternetConnection(binding.sectionName.context)
            }
        }

        return medicines
    }

    override suspend fun getMedicine(categorie : String, medicineName : String, medicineNRegister : String) : MedicineModel?
    {
        var medicine : MedicineModel? = null

        val med = realtime.getReference(categorie + "/" + medicineName.first().toUpperCase() + "/" + medicineNRegister).get().await()

        medicine = GeneralUtilities.parseMedicine(med).toModel()

        return medicine
    }

    override suspend fun getMedicinesByLetter(letter : Char) : MutableList<MedicineModel>
    {
        var medicines : MutableList<MedicineModel> = mutableListOf()

        if(letter != ' ')
        {
            var failed = false

            if(MedicinexApp.isThereInternet)
            {
                val secciones = CatalogueProvider.categories

                withContext(Dispatchers.Main)
                {
                    //Toast.makeText(requireActivity(), R.string.searching, Toast.LENGTH_LONG).show()
                }

                for (section in secciones)
                {
                    if (MedicinexApp.isThereInternet)
                    {
                        val secc = realtime.getReference("$section/$letter").get().await()

                        if (secc != null)
                        {
                            for (medicamento in secc.children)
                            {
                                var med = GeneralUtilities.parseMedicine(medicamento)
                                medicines.add(med.toModel())
                            }
                        }
                    }
                    else
                    {
                        failed = true
                        break
                    }
                }

                if(!failed)
                {
                    //db.letterDao().insertLetter(LetterEntity(letra))
                }
            }
        }

        return medicines
    }

    override suspend fun getMedicineByName(name : String) : MedicineModel?
    {
        var medicine : MedicineModel? = null



        return medicine
    }
}