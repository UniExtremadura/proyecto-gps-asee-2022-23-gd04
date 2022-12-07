package es.unex.giiis.medicinex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.*
import es.unex.giiis.medicinex.databinding.ActivityProfileBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel

@AndroidEntryPoint
class Profile : AppCompatActivity()
{
    private lateinit var binding : ActivityProfileBinding
    private var passwordVisible : Boolean = false
    private var account : Account? = null
    private val medicineViewModel : MedicineViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        medicineViewModel.account.observe(this, Observer{
            account = it
            binding.nombreValue.text = account!!.fullName
            binding.passwordValue.setText(account!!.password)
            binding.emailValue.text = account!!.email
            binding.usuarioValue.text = account!!.username
        })

        fillData()
    }

    override fun onStart() {
        super.onStart()
        fillData()
    }

    private fun fillData()
    {
        if (MedicinexApp.isThereInternet)
        {
            medicineViewModel.getAccount()
        } else
        {
            ScreenMessages.showDialog(this, R.string.no_internet, R.string.no_internet_message)
        }
    }

    fun closeButtonAction(view: View)
    {
        if(view.isEnabled)
        {
            onBackPressed()
        }
    }

    fun showHidePassword(view : View)
    {
        try
        {
            if(!passwordVisible)
            {// Make visible
                binding.passwordValue.transformationMethod = HideReturnsTransformationMethod.getInstance()
                view.setBackgroundResource(R.drawable.hide)
            }
            else
            {// Make invisible
                binding.passwordValue.transformationMethod = PasswordTransformationMethod.getInstance()
                view.setBackgroundResource(R.drawable.show)
            }

            passwordVisible = !passwordVisible
        }
        catch(e: Exception){/**/}
    }

    fun modifyAccount(view : View)
    {
        if(view.isEnabled)
        {
            if(account != null)
            {
                val intent = Intent(this, EditProfile::class.java)
                intent.putExtra("EMAIL", account!!.email)
                intent.putExtra("USERNAME", account!!.username)
                intent.putExtra("PASSWORD", account!!.password)
                intent.putExtra("FULL_NAME", account!!.fullName)
                startActivity(intent)
            }
        }
    }

    fun manageAccount(view : View)
    {
        if(view.isEnabled)
        {
            if(account != null)
            {
                val intent = Intent(this, ManageProfile::class.java)
                intent.putExtra("EMAIL", account!!.email)
                intent.putExtra("USERNAME", account!!.username)
                intent.putExtra("PASSWORD", account!!.password)
                intent.putExtra("FULL_NAME", account!!.fullName)
                startActivity(intent)
            }
        }
    }

    fun logOutAction(view : View)
    {
        if(view.isEnabled)
        {
            if(MedicinexApp.isThereInternet)
            {
                val builder = AlertDialog.Builder(this)

                builder.setTitle(R.string.log_out_request_title).setMessage(R.string.log_out_request_message).setCancelable(true)
                    .setPositiveButton(R.string.yes) { _, _ ->

                        try
                        {
                            medicineViewModel.logOut()
                            val intent = Intent(this, Login::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.putExtra("logged_out", true)
                            startActivity(intent)
                        }
                        catch(e: Exception){/**/}
                    }
                    .setNegativeButton(R.string.no) { dialogInterface, _ ->
                        dialogInterface.cancel()
                    }
                    .show()
            }
        }
    }
}