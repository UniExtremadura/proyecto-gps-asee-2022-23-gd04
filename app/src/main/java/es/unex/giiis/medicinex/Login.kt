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
    private var passwordVisible : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(R.style.Theme_Medicinex)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                if(GeneralUtilities.isThereInternet(this))
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
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener{

                                    if(it.isSuccessful)
                                    {
                                        runOnUiThread {
                                            binding.loginButton.isEnabled = true
                                            goMainMenu()
                                        }
                                    }
                                    else
                                    {
                                        runOnUiThread{
                                            ScreenMessages.incorrectCredentials(this)
                                            binding.loginButton.isEnabled = true
                                        }
                                    }
                                }
                            }catch (e: Exception) {/**/}
                        }
                        else if(!emailOk && !passwordOk)
                        {// Los dos están mal formados
                            runOnUiThread {
                                ScreenMessages.invalidMailPassword(this)
                                binding.loginButton.isEnabled = true
                            }
                        }
                        else if(emailOk)
                        {// El email está bien pero la contraseña no
                            runOnUiThread {
                                ScreenMessages.invalidPassword(this)
                                binding.loginButton.isEnabled = true
                            }
                        }
                        else
                        {// El email está mal pero la contraseña no
                            runOnUiThread {
                                ScreenMessages.invalidMailAddress(this)
                                binding.loginButton.isEnabled = true
                            }
                        }
                    }
                }
                else
                {
                    runOnUiThread {
                        ScreenMessages.noInternetConnection(this)
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

        }
    }

    fun goCreateAccount(view : View)
    {
        if(view.isEnabled)
        {

        }
    }

    private fun goMainMenu()
    {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
}