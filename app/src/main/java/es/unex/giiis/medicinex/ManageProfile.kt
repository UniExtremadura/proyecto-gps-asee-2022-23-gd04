package es.unex.giiis.medicinex

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
import es.unex.giiis.medicinex.databinding.ActivityManageProfileBinding

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
                                    ScreenMessages.accountDeleteFailure(this)
                                }.addOnSuccessListener {
                                    FirebaseAuth.getInstance().currentUser?.delete()
                                    val intent = Intent(this, Login::class.java)
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
