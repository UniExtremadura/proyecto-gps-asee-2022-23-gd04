package es.unex.giiis.medicinex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
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
        preferences = Preference(applicationContext)
    }
}