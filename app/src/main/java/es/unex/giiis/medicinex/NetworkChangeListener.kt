package es.unex.giiis.medicinex

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

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