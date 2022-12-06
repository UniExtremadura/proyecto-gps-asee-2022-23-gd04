package es.unex.giiis.medicinex.data.network

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.CatalogueProvider
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.data.database.entities.toModel
import es.unex.giiis.medicinex.data.model.MedicineModel
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MedicineFBService(private val realtime : FirebaseDatabase) : MedicineFirebaseClient, AccountsFirebaseClient
{
    override suspend fun getCategorieMedicines(categorie : String) : MutableList<MedicineModel>
    {
        var medicines : MutableList<MedicineModel> = mutableListOf()

        if(MedicinexApp.isThereInternet)
        {
            withContext(Dispatchers.Main)
            {
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

        var letter : Char = name[0]

        if(MedicinexApp.isThereInternet)
        {
            val secciones = CatalogueProvider.categories
            var found = false

            for (section in secciones)
            {
                if (MedicinexApp.isThereInternet)
                {
                    val secc = realtime.getReference("$section/$letter").get().await()

                    if (secc != null)
                    {
                        for (medicamento in secc.children)
                        {
                            for(child in medicamento.children)
                            {
                                when (child.key)
                                {
                                    "Nombre" -> {
                                        if ((child.value as String) == name)
                                        {
                                            medicine = GeneralUtilities.parseMedicine(medicamento).toModel()
                                            found = true
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if(found) break
                }
                else
                {
                    break
                }
            }
        }

        return medicine
    }

    override suspend fun editAccount(account : Account)
    {
        var userRef = realtime.getReference("accounts/" + account.email.substring(0, account.email.indexOf('@')))

        userRef.child("fullName").setValue(account.fullName) // Firebase realtime account fullName update.
        userRef.child("password").setValue(account.password) // Firebase realtime account password update.
        userRef.child("username").setValue(account.username) // Firebase realtime account username update.
    }

    override suspend fun registerAccount(account : Account)
    {
        val accountsRef = realtime.getReference("accounts")
        accountsRef.child(account.email.substring(0, account.email.indexOf('@'))).setValue(account)
    }
}