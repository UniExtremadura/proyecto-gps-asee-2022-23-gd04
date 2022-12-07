package es.unex.giiis.medicinex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.databinding.ActivityRecoverAccountBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.utilities.SyntaxChecker
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel

@AndroidEntryPoint
class RecoverAccount : AppCompatActivity()
{
    private lateinit var binding : ActivityRecoverAccountBinding
    private val medicineViewModel : MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        medicineViewModel.recoverPasswordResult.observe(this, Observer {
            when(it)
            {
                true -> {
                    runOnUiThread {
                        ScreenMessages.showDialog(this, R.string.reset_password_title, R.string.reset_password_message)
                        binding.recoverButton.isEnabled = true
                    }
                }
                false -> {
                    runOnUiThread {
                        ScreenMessages.showDialog(this, R.string.reset_password_error_title, R.string.reset_password_error_message)
                        binding.recoverButton.isEnabled = true
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

    fun recoverAction(view : View)
    {
        if(view.isEnabled)
        {
            view.isEnabled = false
            val email : String = binding.emailField.text.toString()

            if(MedicinexApp.isThereInternet)
            {
                if(SyntaxChecker.isValidMailSyntax(email))
                {
                    medicineViewModel.recoverAccountPassword(email)
                }
                else
                {
                    runOnUiThread{
                        Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show()
                    }
                }

                binding.recoverButton.isEnabled = true
            }
            else
            {
                view.isEnabled = true
            }
        }
    }
}