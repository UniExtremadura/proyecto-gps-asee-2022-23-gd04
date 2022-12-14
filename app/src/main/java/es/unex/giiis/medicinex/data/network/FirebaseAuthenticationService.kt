package es.unex.giiis.medicinex.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.utilities.ScreenMessages
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthenticationService @Inject constructor(private val auth : FirebaseAuth) : AuthFirebaseClient
{
    override suspend fun createAccount(email: String, password: String): Boolean
    {
        var success = false

        try
        {
            auth.createUserWithEmailAndPassword(email, password).await()
            success = true

        }catch(_: Exception){}

        return success
    }

    override suspend fun signIn(email: String, password: String): Boolean
    {
        var success = false

        try
        {
            auth.signInWithEmailAndPassword(email, password).await()
            success = true

        }catch(_: Exception) {}

        return success
    }

    override suspend fun logOut()
    {
        auth.signOut()
    }

    override suspend fun resetAccountPassword(email : String) : Boolean
    {
        var success = false

        try
        {
            auth.sendPasswordResetEmail(email).await()
            success = true
        }catch(_: Exception) {}

        return success
    }

    override suspend fun updatePassword(password : String) : Boolean
    {
       var success = false

        try
        {
            auth.currentUser?.updatePassword(password)?.await()
            success = true
        }
        catch (_ : Exception){}

        return success
    }

    override suspend fun deleteProfile()
    {
        auth.currentUser?.delete()!!.await()
    }
}