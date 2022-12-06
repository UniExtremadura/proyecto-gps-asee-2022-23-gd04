package es.unex.giiis.medicinex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.databinding.ActivityRegisterBinding
import es.unex.giiis.medicinex.utilities.AppExecutors
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.utilities.SyntaxChecker

class Register : AppCompatActivity()
{
    private var passwordVisible: Boolean = false
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun closeButtonAction(view: View)
    {
        if(view.isEnabled)
        {
            onBackPressed()
        }
    }

    private fun registerAccount(account: Account) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(account.email, account.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    runOnUiThread {
                        ScreenMessages.showDialog(this, R.string.account_created_title, R.string.account_created_message)
                        binding.emailField.setText("")
                        binding.userField.setText("")
                        binding.passwordField.setText("")
                        binding.nameField.setText("")
                        binding.registerButton.isEnabled = true

                        val database = Firebase.database
                        val accountsRef = database.getReference("accounts")
                        accountsRef.child(account.email.substring(0, account.email.indexOf('@'))).setValue(account)
                    }
                } else {
                    runOnUiThread {
                        ScreenMessages.showDialog(this, R.string.account_already_exists_title, R.string.account_already_exists_message)
                        binding.emailField.setText("")
                        binding.registerButton.isEnabled = true
                    }
                }
            }
    }

    fun registerAction(view: View)
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
                        registerAccount(account)
                    } else
                    {
                        runOnUiThread {
                            ScreenMessages.showDialog(this, R.string.incorrect_credentials_title, R.string.account_invalid_message)
                            binding.registerButton.isEnabled = true
                        }
                    }
                }
                else
                {
                    runOnUiThread {
                        view.isEnabled = true
                    }
                }
            }
        }
    }

    fun showHidePassword(view: View)
    {
        try
        {
            if (!passwordVisible)
            {// Make visible
                binding.passwordField.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                view.setBackgroundResource(R.drawable.hide)
            } else
            {// Make invisible
                binding.passwordField.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                view.setBackgroundResource(R.drawable.show)
            }
            passwordVisible = !passwordVisible
        } catch (e: Exception) {/**/}
    }
}