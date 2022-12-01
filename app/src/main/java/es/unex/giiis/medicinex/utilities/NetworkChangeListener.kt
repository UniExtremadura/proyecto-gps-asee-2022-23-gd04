package es.unex.giiis.medicinex.utilities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import es.unex.giiis.medicinex.MedicinexApp

class NetworkChangeListener : BroadcastReceiver()
{
    override fun onReceive(context : Context, intent: Intent?)
    {
        if(!GeneralUtilities.isThereInternet(context))
        {
            MedicinexApp.isThereInternet = false
            ScreenMessages.noInternetConnection(context)
        }
        else
        {
            MedicinexApp.isThereInternet = true
        }
    }
}