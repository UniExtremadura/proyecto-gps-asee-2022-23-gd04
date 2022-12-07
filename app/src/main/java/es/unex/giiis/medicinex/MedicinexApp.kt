package es.unex.giiis.medicinex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import kotlin.text.Typography.dagger

@HiltAndroidApp
class MedicinexApp : Application()
{
    companion object
    {
        lateinit var preferences : Preference
        var isThereInternet = false
    }

    override fun onCreate()
    {
        super.onCreate()
        isThereInternet = GeneralUtilities.isThereInternet(applicationContext)
        preferences = Preference(applicationContext)
    }
}