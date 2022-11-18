package es.unex.giiis.medicinex

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import es.unex.giiis.medicinex.databinding.ActivityLoginBinding

class Login : AppCompatActivity()
{
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(R.style.Theme_Medicinex)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    fun logIn(view : View)
    {
        if(view.isEnabled)
        {

        }
    }

    fun goForgottenPassword(view: View)
    {
        if(view.isEnabled)
        {

        }
    }

    fun goCreateAccount(view : View)
    {
        if(view.isEnabled)
        {

        }
    }
}