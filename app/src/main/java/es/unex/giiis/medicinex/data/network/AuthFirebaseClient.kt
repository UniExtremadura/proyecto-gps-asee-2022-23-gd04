package es.unex.giiis.medicinex.data.network

interface AuthFirebaseClient
{
    suspend fun registerAccount(email : String, password : String) : Boolean

    suspend fun signIn(email : String, password : String) : Boolean
}