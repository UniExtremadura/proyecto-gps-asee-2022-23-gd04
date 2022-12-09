package es.unex.giiis.medicinex.data.network

import es.unex.giiis.medicinex.Account

interface AccountsFirebaseClient
{
    suspend fun editAccount(account : Account)

    suspend fun registerAccount(account : Account)

    suspend fun saveMedicineInAfk(medicineName : String) : Boolean

    suspend fun removeUser(email : String) : Boolean

    suspend fun cleanAccount(email : String) : Boolean
}