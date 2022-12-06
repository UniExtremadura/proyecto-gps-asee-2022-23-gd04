package es.unex.giiis.medicinex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.databinding.ActivityManageProfileBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.utilities.SyntaxChecker

class ManageProfile : AppCompatActivity()
{
    private lateinit var binding: ActivityManageProfileBinding
    private var passwordVisible: Boolean = false
    private lateinit var account: Account

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
                MedicinexApp.preferences.setAutoFill(true)
                MedicinexApp.preferences.setEmail(account.email)
                MedicinexApp.preferences.setPassword(account.password)
            }
            else
            {
                MedicinexApp.preferences.setAutoFill(false)
            }
        }
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
        if (view.isEnabled)
        {
            val path: String = "accounts/" + GeneralUtilities.getAccountNameByMail(account.email) + "/firstAidKit"
            val accountFirstAidKitRef = Firebase.database.getReference(path)

            accountFirstAidKitRef.get().addOnSuccessListener {

                for (cure in it.children)
                {
                    if(cure.value.toString() != "-1")
                    {
                        Firebase.database.getReference(path + "/${cure.key}").removeValue()
                    }
                }
                ScreenMessages.showDialog(this, R.string.first_aid_kit_cleaned_title, R.string.first_aid_kit_cleaned_message)
            }
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
            if (GeneralUtilities.isThereInternet(this))
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
                                Firebase.database.getReference(
                                    "accounts/" + GeneralUtilities.getAccountNameByMail(
                                        account.email
                                    )
                                ).removeValue().addOnFailureListener {
                                    ScreenMessages.showDialog(this, R.string.account_updated_failed_title, R.string.account_updated_failed_message)
                                }.addOnSuccessListener {
                                    FirebaseAuth.getInstance().currentUser?.delete()
                                    val intent = Intent(this, Login::class.java)
                                    MedicinexApp.preferences.clear()
                                    intent.putExtra("logged_out", true)
                                    startActivity(intent)
                                    finish()
                                }
                            } catch (e: Exception){/**/}
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
