package es.unex.giiis.medicinex.data.network

interface AuthFirebaseClient
{
    suspend fun createAccount(email : String, password : String) : Boolean

    suspend fun signIn(email : String, password : String) : Boolean

    suspend fun resetAccountPassword(email : String) : Boolean

    suspend fun updatePassword(password : String) : Boolean

    suspend fun logOut()
}