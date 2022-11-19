package es.unex.giiis.medicinex

import android.content.Context

class Preference(val context : Context)
{
    private val shareDb = "MedSDB"
    private val emailField = "email"
    private val passwordField = "password"
    private val filledField = "fill_login"

    private val sharedPreferences = context.getSharedPreferences(shareDb, 0)

    fun setEmail(email : String)
    {
        sharedPreferences.edit().putString(emailField, email).apply()
    }

    fun setPassword(password : String)
    {
        sharedPreferences.edit().putString(passwordField, password).apply()
    }

    fun setAutoFill(autoFill : Boolean)
    {
        sharedPreferences.edit().putBoolean(filledField, autoFill).apply()
    }

    fun getEmail() : String
    {
        return sharedPreferences.getString(emailField, "")!!
    }

    fun getPassword() : String
    {
        return sharedPreferences.getString(passwordField, "")!!
    }

    fun getAutoFill() : Boolean
    {
        return sharedPreferences.getBoolean(filledField, false)
    }

    fun clear()
    {
        sharedPreferences.edit().clear().apply()
    }
}