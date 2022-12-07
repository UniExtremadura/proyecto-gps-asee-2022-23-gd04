package es.unex.giiis.medicinex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.databinding.ActivityManageProfileBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.utilities.SyntaxChecker
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel

@AndroidEntryPoint
class ManageProfile : AppCompatActivity()
{
    private lateinit var binding: ActivityManageProfileBinding
    private var passwordVisible: Boolean = false
    private lateinit var account: Account
    private val medicineViewModel : MedicineViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityManageProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrieveAccount()

        if(MedicinexApp.preferences.getAutoFill())
        {
            binding.autoLogin.isChecked = true
        }

        binding.autoLogin.setOnCheckedChangeListener { _, b ->
            if(b)
            {
                medicineViewModel.autoLog(true, account)
            }
            else
            {
                medicineViewModel.autoLog(false, account)
            }
        }

        medicineViewModel.deleteUserResult.observe(this, Observer{
            when(it)
            {
                true -> {}

                false -> {
                    ScreenMessages.showDialog(this, R.string.account_updated_failed_title, R.string.account_updated_failed_message)
                }
            }
        })

        medicineViewModel.cleanAccountResult.observe(this, Observer {
            when(it)
            {
                true -> {
                    ScreenMessages.showDialog(this, R.string.first_aid_kit_cleaned_title, R.string.first_aid_kit_cleaned_message)
                }

                false -> {
                    ScreenMessages.showDialog(this, R.string.first_aid_kit_not_cleaned_title, R.string.first_aid_kit_not_cleaned_message)
                }
            }
        })

    }

    private fun retrieveAccount()
    {
        account = Account(
            intent.getStringExtra("USERNAME").toString(),
            intent.getStringExtra("EMAIL").toString(),
            intent.getStringExtra("PASSWORD").toString(),
            intent.getStringExtra("FULL_NAME").toString()
        )
    }

    fun closeButtonAction(view: View)
    {
        if (view.isEnabled) {
            onBackPressed()
        }
    }

    fun cleanAccount(view: View)
    {
        if (view.isEnabled && MedicinexApp.isThereInternet)
        {
            medicineViewModel.cleanProfile(account.email)
        }
    }

    fun showHidePassword(view: View)
    {
        try
        {
            if (!passwordVisible)
            {// Contraseña visible
                binding.password.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                view.setBackgroundResource(R.drawable.hide)
            } else
            {// Contraseña invisible
                binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                view.setBackgroundResource(R.drawable.show)
            }

            passwordVisible = !passwordVisible
        } catch (e: Exception) {/**/}
    }

    fun deleteAccount(view: View)
    {
        if (view.isEnabled)
        {
            if (MedicinexApp.isThereInternet)
            {
                val pass: String = binding.password.text.toString()
                if (SyntaxChecker.isValidPasswordSyntax(pass) && pass == account.password)
                {
                    val builder = AlertDialog.Builder(this)

                    builder.setTitle(R.string.delete_user_title)
                        .setMessage(R.string.delete_user_message).setCancelable(true)
                        .setPositiveButton(R.string.yes) { _, _ ->

                            try
                            {
                                medicineViewModel.deleteAccount(account.email)

                                val intent = Intent(this, Login::class.java)
                                intent.putExtra("logged_out", true)
                                startActivity(intent)
                                finish()
                            }catch (e: Exception){/**/}
                        }
                        .setNegativeButton(R.string.no) { dialogInterface, _ ->
                            dialogInterface.cancel()
                        }.show()
                }
                else
                {
                    Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
