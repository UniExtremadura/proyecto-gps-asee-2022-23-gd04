package es.unex.giiis.medicinex.utilities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R

class NetworkChangeListener : BroadcastReceiver()
{
    override fun onReceive(context : Context, intent: Intent?)
    {
        if(!GeneralUtilities.isThereInternet(context))
        {
            MedicinexApp.isThereInternet = false
            ScreenMessages.showDialog(context, R.string.no_internet, R.string.no_internet_message)
        }
        else
        {
            MedicinexApp.isThereInternet = true
        }
    }
}