package es.unex.giiis.medicinex.data.network

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.CatalogueProvider
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.data.database.entities.toModel
import es.unex.giiis.medicinex.data.model.MedicineModel
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.view.Login
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MedicineFBService @Inject constructor(private val realtime : FirebaseDatabase) : MedicineFirebaseClient, AccountsFirebaseClient
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

        val med = realtime.getReference(categorie + "/" + medicineName.first().uppercaseChar() + "/" + medicineNRegister).get().await()

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

        userRef.child("fullName").setValue(account.fullName).await() // Firebase realtime account fullName update.
        userRef.child("password").setValue(account.password).await() // Firebase realtime account password update.
        userRef.child("username").setValue(account.username).await() // Firebase realtime account username update.
    }

    override suspend fun registerAccount(account : Account)
    {
        val accountsRef = realtime.getReference("accounts")
        accountsRef.child(account.email.substring(0, account.email.indexOf('@'))).setValue(account).await()
    }

    override suspend fun saveMedicineInAfk(medicineName : String) : Boolean
    {
        var success = false
        var exists = false
        val fakRef = realtime.getReference("accounts/" + GeneralUtilities.getAccountNameByMail(FirebaseAuth.getInstance().currentUser?.email.toString()) + "/firstAidKit")

        try
        {
            val response = fakRef.get().await()

            for (child in response.children)
            {
                if (child.value == medicineName)
                {
                    exists = true;
                }
            }

            if (!exists)
            {
                fakRef.push().setValue(medicineName)
                success = true
            }
        }catch(e : Exception) {success = false}

        return success
    }

    override suspend fun removeUser(email: String): Boolean
    {
        var success = false

        try
        {
            realtime.getReference("accounts/" + GeneralUtilities.getAccountNameByMail(email)).removeValue().await()
            MedicinexApp.preferences.clear()
            success = true
        }
        catch(e : Exception) {}

        return success
    }

    override suspend fun cleanAccount(email : String) : Boolean
    {
        var success = false
        val path: String = "accounts/" + GeneralUtilities.getAccountNameByMail(email) + "/firstAidKit"
        val accountFirstAidKitRef = realtime.getReference(path)

        try
        {
            val response = accountFirstAidKitRef.get().await()

            for (cure in response.children)
            {
                if(cure.value.toString() != "-1")
                {
                    Firebase.database.getReference(path + "/${cure.key}").removeValue()
                }
            }
            success = true

        }catch(e : Exception) { success = false}

        return success
    }

    override suspend fun getFakMedicines(): MutableList<String>
    {
        val fakRef =  realtime.getReference("accounts/" + GeneralUtilities.getAccountNameByMail(FirebaseAuth.getInstance().currentUser?.email.toString()) + "/firstAidKit")

        var medicinas = mutableListOf<String>()

        try
        {
            val response = fakRef.get().await()

            for (medicina in response.children)
            {
                if (medicina.value != "-1")
                {
                    medicinas.add(medicina.value.toString())
                }
            }
        }catch (e : Exception) {/**/}

        return medicinas
    }

    override suspend fun deleteMedicineFromFak(name : String) : Boolean
    {
        var success = false

        val fakRef =  realtime.getReference("accounts/" + GeneralUtilities.getAccountNameByMail(FirebaseAuth.getInstance().currentUser?.email.toString()) + "/firstAidKit")

        val response = fakRef.get().await()

        for (medicina in response.children)
        {
            if (medicina.value == name)
            {
                Firebase.database.getReference(
                    "accounts/" + GeneralUtilities.getAccountNameByMail(FirebaseAuth.getInstance().currentUser?.email.toString()) + "/firstAidKit" + "/${medicina.key}"
                ).removeValue()

                success = true
            }
        }
        return success
    }

    override suspend fun getAccount() : Account
    {
        var ac = Account("aaa", "", "", "")

        val accountRef = realtime.getReference("accounts/" + GeneralUtilities.getAccountNameByMail(FirebaseAuth.getInstance().currentUser?.email.toString()))

        val ref =  accountRef.get().await()

        for (child in ref.children)
        {
            when (child.key) {
                "email" -> ac.email = child.value as String
                "fullName" -> ac.fullName = child.value as String
                "password" -> ac.password = child.value as String
                "username" -> ac.username = child.value as String
                "firstAidKit" -> {}
                else -> {}
            }
        }
        return ac
    }
}