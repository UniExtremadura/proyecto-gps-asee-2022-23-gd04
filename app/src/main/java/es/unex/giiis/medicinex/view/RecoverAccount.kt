package es.unex.giiis.medicinex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.databinding.ActivityRecoverAccountBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.utilities.SyntaxChecker

class RecoverAccount : AppCompatActivity()
{
    private lateinit var binding : ActivityRecoverAccountBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            if(GeneralUtilities.isThereInternet(this))
            {
                if(SyntaxChecker.isValidMailSyntax(email))
                {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener{
                                task ->
                            if(task.isSuccessful)
                            {
                                runOnUiThread {
                                    ScreenMessages.showDialog(this, R.string.reset_password_title, R.string.reset_password_message)
                                    binding.recoverButton.isEnabled = true
                                }
                            }
                            else
                            {
                                runOnUiThread {
                                    ScreenMessages.showDialog(this, R.string.reset_password_error_title, R.string.reset_password_error_message)
                                    binding.recoverButton.isEnabled = true
                                }
                            }
                        }
                }
                else
                {
                    runOnUiThread{
                        Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show()
                        binding.recoverButton.isEnabled = true
                    }
                }
            }
            else
            {
                view.isEnabled = true
            }
        }
    }
}