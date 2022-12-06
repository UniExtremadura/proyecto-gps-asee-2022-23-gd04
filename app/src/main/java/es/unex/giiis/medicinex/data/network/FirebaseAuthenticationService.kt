package es.unex.giiis.medicinex.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.utilities.ScreenMessages
import kotlinx.coroutines.tasks.await

abstract class FirebaseAuthenticationService(private val auth : FirebaseAuth) : AuthFirebaseClient
{
    override suspend fun createAccount(email: String, password: String): Boolean
    {
        var success = false
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            success = it.isSuccessful
        }.await()

        return success
    }

    override suspend fun signIn(email: String, password: String): Boolean
    {
        var success = false
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            success = it.isSuccessful
        }.await()

        return success
    }

    override suspend fun logOut()
    {
        auth.signOut()
    }

    override suspend fun resetAccountPassword(email : String) : Boolean
    {
        var success = false
        auth.sendPasswordResetEmail(email).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    success = true
                }
            }.await()
        return success
    }

    override suspend fun updatePassword(password : String) : Boolean
    {
       var success = false

        auth.currentUser?.updatePassword(password)?.addOnCompleteListener {
            if(it.isSuccessful) {
                success = true
            }
        }?.await()

        return success
    }

}