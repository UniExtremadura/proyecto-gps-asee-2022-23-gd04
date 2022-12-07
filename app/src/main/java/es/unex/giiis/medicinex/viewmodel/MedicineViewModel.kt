package es.unex.giiis.medicinex.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.data.model.MedicineModel
import es.unex.giiis.medicinex.domain.*
import es.unex.giiis.medicinex.utilities.SyntaxChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor
(
    private val registerUser : RegisterUserUseCase,
    private val saveMedicine : SaveMedicineAfkUseCase,
    private val recoverPassword : RecoverPasswordUseCase,
    private val saveMedicineLocal : SaveMedicineLocalUseCase,
    private val login : LoginUseCase,
    private val editProfile : EditProfileUseCase,
    private val medicineData : MedicineDataUseCase,
    private val filteredSearch : FilteredSearchUseCase,
    private val deleteUser : DeleteAccountUseCase,
    private val cleanAccount : CleanAccountUseCase,
    private val loginOut : LogOutUseCase,
    private val autoLogin : AutoLoginUseCase,
    private val medicineByName : SearchMedByName,
    private val getFak : GetFakMedicines,
    private val deleteMedicineFak : DeleteMedicineInFak,
    private val sectionMedicines : GetSectionMedicines,
    private val retrieveAccount : RetrieveAccount
): ViewModel()
{

    val reristerResult = MutableLiveData<Boolean>() // CU01: Registrar usuario
    val saveMedicineResult = MutableLiveData<Boolean>() // CU02: Guardar medicamento en botiquín (Firebase)
    val recoverPasswordResult = MutableLiveData<Boolean>() // CU03: Recuperar contraseña
    val saveMedicineLocalResult = MutableLiveData<Long>() // CU04: Almacenar medicamento (local - Room: ahorro futuras búsquedas)
    val loginResult = MutableLiveData<Boolean>() // CU05: Iniciar sesión
    val editProfileResult = MutableLiveData<Boolean>() // CU06: Editar cuenta
    val medicineModel = MutableLiveData<MedicineModel?>() // CU07: Ver datos medicamento
    val medicinesSearched = MutableLiveData<MutableList<MedicineModel>>() // CU08: Búsqueda con filtros (alfabétic., categoría + filtros)
    val deleteUserResult = MutableLiveData<Boolean>() // CU10: Eliminar cuenta
    val cleanAccountResult = MutableLiveData<Boolean>() // CU11: Limpiar cuenta
    /////////////////////////
    val medicineFromName = MutableLiveData<MedicineModel>()
    val medicinesFAK = MutableLiveData<MutableList<String>>()
    val deleteMedicineFakResult = MutableLiveData<Boolean>()
    val sectionMedicinesResult = MutableLiveData<MutableList<MedicineModel>>()
    val account = MutableLiveData<Account>()

    // CU01: Registrar usuario
    fun register(account : Account)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = registerUser(account)
            reristerResult.postValue(result)
        }
    }

    // CU02: Guardar medicamento en botiquín (Firebase)
    fun saveMedicineAfk(medicineName : String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = saveMedicine(medicineName)
            saveMedicineResult.postValue(result)
        }
    }

    // CU03: Recuperar contraseña
    fun recoverAccountPassword(email : String)
    {
        viewModelScope.launch(Dispatchers.IO){
            val result = recoverPassword(email)
            recoverPasswordResult.postValue(result)
        }
    }

    // CU04: Almacenar medicamento (local - Room: ahorro futuras búsquedas)
    fun saveMedicineRoom(med : MedicinaEntity)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = saveMedicineLocal(med)
            saveMedicineLocalResult.postValue(result)
        }
    }

    // CU05: Iniciar sesión
    fun logIn(email : String, password : String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            if(email != null && password != null)
            {
                val result = login(email, password)
                loginResult.postValue(result)
            }
        }
    }

    // CU06: Editar cuenta
    fun editAccount(account : Account)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = editProfile(account)
            editProfileResult.postValue(result)
        }
    }

    // CU07: Ver datos medicamento
    fun viewMedicineData(nRegistro : String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = medicineData(nRegistro)
            medicineModel.postValue(result)
        }
    }

    // CU08: Búsqueda con filtros (alfabétic., categoría + filtros)
    fun searchWithFilter(query : String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = filteredSearch(query)
            medicinesSearched.postValue(result)
        }
    }

    // CU10: Eliminar cuenta
    fun deleteAccount(email : String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = deleteUser(email)
            deleteUserResult.postValue(result)
        }
    }

    // CU11: Limpiar cuenta
    fun cleanProfile(email : String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = cleanAccount(email)
            cleanAccountResult.postValue(result)
        }
    }

    // CU12: Cerrar sesión
    fun logOut()
    {
        viewModelScope.launch(Dispatchers.IO) {
            loginOut()
        }
    }

    // CU14: Auto-login
    fun autoLog(turning : Boolean, account : Account)
    {
        viewModelScope.launch {
            autoLogin(turning, account)
        }
    }

    ////////////// ANOTHER USE CASES //////////////////

    fun getMedicineByName(name : String)
    {
        viewModelScope.launch(Dispatchers.IO){
            val med = medicineByName(name)
            medicineFromName.postValue(med!!)
        }
    }

    fun getFAKMedicines()
    {
        viewModelScope.launch(Dispatchers.IO){
            val result = getFak()
            medicinesFAK.postValue(result)
        }
    }

    fun deleteMedicine(name : String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            val result = deleteMedicineFak(name)
            deleteMedicineFakResult.postValue(result)
        }
    }

    fun retrieveSectionMedicines(section : String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = sectionMedicines(section)
            sectionMedicinesResult.postValue(result)
        }
    }

    fun getAccount()
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = retrieveAccount()
            account.postValue(result)
        }
    }
}