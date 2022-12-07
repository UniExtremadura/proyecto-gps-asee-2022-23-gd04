package es.unex.giiis.medicinex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.databinding.ActivityEditProfileBinding
import es.unex.giiis.medicinex.utilities.AppExecutors
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.utilities.SyntaxChecker
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel

@AndroidEntryPoint
class EditProfile : AppCompatActivity()
{
    private lateinit var binding : ActivityEditProfileBinding
    private var passwordVisible : Boolean = false
    private lateinit var account : Account
    private val medicineViewModel : MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrieveAccount()

        medicineViewModel.editProfileResult.observe(this, Observer {
            when(it)
            {
                true -> {
                    runOnUiThread {

                        medicineViewModel.getAccount()
                        ScreenMessages.showDialog(this, R.string.account_updated_successfully_title, R.string.account_updated_successfully_message)
                    }
                }

                false -> {
                    runOnUiThread{
                        ScreenMessages.showDialog(this, R.string.account_updated_failed_title, R.string.account_updated_failed_message)
                    }
                }
            }
        })
    }

    private fun retrieveAccount()
    {
        account = Account(intent.getStringExtra("USERNAME").toString(), intent.getStringExtra("EMAIL").toString(), intent.getStringExtra("PASSWORD").toString(), intent.getStringExtra("FULL_NAME").toString())

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

                val account = Account(binding.userField.text.toString(), binding.emailField.text.toString(), binding.passwordField.text.toString(), binding.nameField.text.toString())

                if (MedicinexApp.isThereInternet)
                {
                    if (SyntaxChecker.isValidAccountSyntax(account))
                    {
                        medicineViewModel.editAccount(account)
                    } else
                    {
                        runOnUiThread {
                            ScreenMessages.showDialog(this, R.string.incorrect_credentials_title, R.string.account_invalid_message)
                        }
                    }

                    runOnUiThread{
                        binding.updateButton.isEnabled = true
                    }
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