package es.unex.giiis.medicinex.view

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.databinding.ActivityLoginBinding
import es.unex.giiis.medicinex.utilities.AppExecutors
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.utilities.SyntaxChecker
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel


@AndroidEntryPoint
class Login : AppCompatActivity()
{
    private lateinit var binding : ActivityLoginBinding
    private var passwordVisible : Boolean = false
    private val medicineViewModel : MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(R.style.Theme_Medicinex)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hasLoggedOut = intent.getBooleanExtra("logged_out", false)

        if(MedicinexApp.preferences.getAutoFill() && !hasLoggedOut)
        {
            val autoEmail = MedicinexApp.preferences.getEmail()
            val autoPassword = MedicinexApp.preferences.getPassword()

            try
            {
                if(MedicinexApp.isThereInternet)
                {
                    medicineViewModel.logIn(autoEmail, autoPassword)
                }
            }
            catch (e : Exception){/**/}
        }

        medicineViewModel.loginResult.observe(this, Observer {
            when(it)
            {
                true -> {
                    val email = binding.usermail.text.toString()
                    val password = binding.password.text.toString()
                    goMainMenu(email, password)}

                false -> {
                    runOnUiThread{
                        ScreenMessages.showDialog(this, R.string.incorrect_credentials_title, R.string.incorrect_credentials_message)
                    }
                }
            }
        })
    }

    fun showHidePassword(view : View)
    {
        try
        {
            if(!passwordVisible)
            {// Make visible
                binding.password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                view.setBackgroundResource(R.drawable.hide)
            }
            else
            {// Make invisible
                binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                view.setBackgroundResource(R.drawable.show)
            }

            passwordVisible = !passwordVisible
        }
        catch(e: Exception){/**/}
    }

    fun logIn(view : View)
    {
        if(view.isEnabled)
        {
            view.isEnabled = false
            AppExecutors.instance?.networkIO()?.execute {
                if(MedicinexApp.isThereInternet)
                {
                    val email = binding.usermail.text.toString()
                    val password = binding.password.text.toString()

                    if(email.isEmpty() || password.isEmpty())
                    {
                        runOnUiThread {
                            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show()
                            binding.loginButton.isEnabled = true
                        }
                    }
                    else
                    {
                        val emailOk = SyntaxChecker.isValidMailSyntax(email)
                        val passwordOk = SyntaxChecker.isValidPasswordSyntax(password)

                        if(emailOk && passwordOk)
                        {
                            try
                            {
                                medicineViewModel.logIn(email, password)
                                binding.loginButton.isEnabled = true
                            }catch (e: Exception) {/**/}
                        }
                        else if(!emailOk && !passwordOk)
                        {// Los dos est??n mal formados
                            runOnUiThread {
                                ScreenMessages.showDialog(this, R.string.invalid_mail_password_title, R.string.invalid_mail_password_message)
                                binding.loginButton.isEnabled = true
                            }
                        }
                        else if(emailOk)
                        {// El email est?? bien pero la contrase??a no
                            runOnUiThread {
                                ScreenMessages.showDialog(this, R.string.invalid_password_title, R.string.invalid_password_message)
                                binding.loginButton.isEnabled = true
                            }
                        }
                        else
                        {// El email est?? mal pero la contrase??a no
                            runOnUiThread {
                                ScreenMessages.showDialog(this, R.string.invalid_mail_title, R.string.invalid_mail_message)
                                binding.loginButton.isEnabled = true
                            }
                        }
                    }
                }
                else
                {
                    runOnUiThread {
                        binding.loginButton.isEnabled = true
                    }
                }
            }
        }
    }

    fun goForgottenPassword(view: View)
    {
        if(view.isEnabled)
        {
            val intent = Intent(this, RecoverAccount::class.java)
            startActivity(intent)
        }
    }

    fun goCreateAccount(view : View)
    {
        if(view.isEnabled)
        {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun goMainMenu(email : String?, password : String?)
    {
        if(email != null && password != null)
        {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }
}