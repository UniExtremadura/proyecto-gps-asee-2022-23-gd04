package es.unex.giiis.medicinex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity()
{
    private lateinit var binding : ActivityEditProfileBinding
    private var passwordVisible : Boolean = false
    private lateinit var account : Account


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrieveAccount()
    }

    private fun retrieveAccount()
    {
        account = Account(intent.getStringExtra("USERNAME").toString(),
            intent.getStringExtra("EMAIL").toString(),
            intent.getStringExtra("PASSWORD").toString(),
            intent.getStringExtra("FULL_NAME").toString()
        )

        binding.userField.setText(account.username)
        binding.emailField.setText(account.email)
        binding.passwordField.setText(account.password)
        binding.nameField.setText(account.fullName)
    }

    fun updateAccount(view : View)
    {
        if (view.isEnabled)
        {
            view.isEnabled = false
            AppExecutors.instance?.networkIO()?.execute {

                val account = Account(
                    binding.userField.text.toString(),
                    binding.emailField.text.toString(),
                    binding.passwordField.text.toString(),
                    binding.nameField.text.toString()
                )

                if (GeneralUtilities.isThereInternet(this))
                {
                    if (SyntaxChecker.isValidAccountSyntax(account))
                    {
                        updateAccountAuth(account) // Firebase auth update.

                    } else
                    {
                        runOnUiThread {
                            ScreenMessages.accountInvalidCredentials(this)
                            binding.updateButton.isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun updateAccountAuth(account : Account)
    {
        FirebaseAuth.getInstance().currentUser?.updatePassword(account.password)?.addOnCompleteListener {

            if(it.isSuccessful)
            {
                runOnUiThread {

                    val database = Firebase.database
                    val accountRef = database.getReference("accounts/" + account.email.substring(0, account.email.indexOf('@')))
                    accountRef.child("fullName").setValue(account.fullName) // Firebase realtime account fullName update.
                    accountRef.child("password").setValue(account.password) // Firebase realtime account password update.
                    accountRef.child("username").setValue(account.username) // Firebase realtime account username update.
                    ScreenMessages.accountUpdatedSuccessfully(this)
                    binding.updateButton.isEnabled = true
                }
            }
            else
            {
                runOnUiThread{
                    ScreenMessages.accountUpdateFailed(this)
                    binding.updateButton.isEnabled = true
                }
            }
        }
    }


    fun showHidePassword(view : View)
    {
        try
        {
            if(!passwordVisible)
            {// Contraseña visible
                binding.passwordField.transformationMethod = HideReturnsTransformationMethod.getInstance()
                view.setBackgroundResource(R.drawable.hide)
            }
            else
            {// Contraseña invisible
                binding.passwordField.transformationMethod = PasswordTransformationMethod.getInstance()
                view.setBackgroundResource(R.drawable.show)
            }

            passwordVisible = !passwordVisible
        }
        catch(e: Exception){/**/}
    }

    fun closeButtonAction(view: View)
    {
        if(view.isEnabled)
        {
            onBackPressed()
        }
    }
}