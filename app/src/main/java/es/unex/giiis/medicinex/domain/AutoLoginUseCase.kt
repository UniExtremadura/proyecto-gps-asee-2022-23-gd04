package es.unex.giiis.medicinex.domain

import android.util.Log
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.MedicinexApp
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor()
{
    operator fun invoke(turning : Boolean, account : Account)
    {
        if(turning)
        {
            MedicinexApp.preferences.setAutoFill(true)
            MedicinexApp.preferences.setEmail(account.email)
            MedicinexApp.preferences.setPassword(account.password)
        }
        else
        {
            MedicinexApp.preferences.setAutoFill(false)
        }
    }
}