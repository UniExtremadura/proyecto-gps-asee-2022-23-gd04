package es.unex.giiis.medicinex

import android.app.Application

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