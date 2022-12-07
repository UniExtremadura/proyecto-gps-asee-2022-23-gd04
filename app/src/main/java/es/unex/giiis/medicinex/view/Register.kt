package es.unex.giiis.medicinex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.databinding.ActivityRegisterBinding
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.utilities.SyntaxChecker
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel

@AndroidEntryPoint
class Register : AppCompatActivity()
{
    private var passwordVisible: Boolean = false
    private lateinit var binding: ActivityRegisterBinding
    private val medicineViewModel : MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        medicineViewModel.reristerResult.observe(this, Observer {
            when(it)
            {
                true -> {
                    runOnUiThread {
                        ScreenMessages.showDialog(this, R.string.account_created_title, R.string.account_created_message)
                        binding.emailField.setText("")
                        binding.userField.setText("")
                        binding.passwordField.setText("")
                        binding.nameField.setText("")
                        binding.registerButton.isEnabled = true
                    }
                }

                false -> {
                    runOnUiThread {
                        ScreenMessages.showDialog(this, R.string.account_already_exists_title, R.string.account_already_exists_message)
                        binding.emailField.setText("")
                        binding.registerButton.isEnabled = true
                    }
                }
            }
        })
    }

    fun closeButtonAction(view: View)
    {
        if(view.isEnabled)
        {
            onBackPressed()
        }
    }

    fun registerAction(view: View)
    {
        if (view.isEnabled && MedicinexApp.isThereInternet)
        {
            view.isEnabled = false

            val account = Account(binding.userField.text.toString(),  binding.emailField.text.toString(), binding.passwordField.text.toString(), binding.nameField.text.toString())

            if (SyntaxChecker.isValidAccountSyntax(account))
            {
                medicineViewModel.register(account)
            }
            else
            {
                ScreenMessages.showDialog(this, R.string.incorrect_credentials_title, R.string.account_invalid_message)
                binding.registerButton.isEnabled = true
            }

            view.isEnabled = true
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