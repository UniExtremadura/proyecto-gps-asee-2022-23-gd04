package es.unex.giiis.medicinex.data.network

interface AccountsFirebaseClient
{
    suspend fun signInWithMailAndPassowrd()

    suspend fun resetAccountPassword()

    suspend fun logOut()
}