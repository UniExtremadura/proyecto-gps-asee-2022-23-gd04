package es.unex.giiis.medicinex.view

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.*
import es.unex.giiis.medicinex.Notification
import es.unex.giiis.medicinex.databinding.FragmentRemindersBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import java.time.LocalTime
import java.util.*

class Reminders : Fragment()
{
    private lateinit var binding : FragmentRemindersBinding
    private var myMedicines : MutableList<String> = mutableListOf()
    private lateinit var fakRef : DatabaseReference
    private lateinit var database : FirebaseDatabase


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentRemindersBinding.inflate(inflater, container, false)

        if(MedicinexApp.isThereInternet)
        {
            database = Firebase.database
            fakRef = database.getReference("accounts/" + GeneralUtilities.getAccountNameByMail(
                FirebaseAuth.getInstance().currentUser?.email.toString()) + "/firstAidKit")


            updateUI()
        }
        else
        {
            ScreenMessages.showDialog(requireActivity(), R.string.no_internet, R.string.no_internet_message)
        }


        binding.addReminder.setOnClickListener {

            if(myMedicines.size == 0)
            {
                Toast.makeText(requireActivity(), R.string.no_medicines, Toast.LENGTH_SHORT).show()
            }
            else
            {
                scheduleNotification()
            }

            binding.txtInfoNotif.setText("")
        }


        createNotificationChannel()

        return binding.root
    }


    internal fun updateUI()
    {
        if(GeneralUtilities.isThereInternet(requireActivity()))
        {
            database = Firebase.database
            fakRef = database.getReference("accounts/" + GeneralUtilities.getAccountNameByMail(
                FirebaseAuth.getInstance().currentUser?.email.toString()) + "/firstAidKit")

            fakRef.get().addOnSuccessListener {

                myMedicines = mutableListOf()
                for(medicina in it.children)
                {
                    if(medicina.value != "-1")
                    {
                        myMedicines.add(medicina.value.toString())
                    }
                }

                val spinner = binding.medicinesSpinner
                val arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, myMedicines.toTypedArray())
                spinner.adapter = arrayAdapter
            }
        }
    }

    private fun scheduleNotification()
    {
        val intent = Intent(requireActivity().applicationContext, Notification::class.java)
        val title = "${binding.medicinesSpinner.selectedItem}"
        val message = binding.txtInfoNotif.text.toString()

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)
        intent.putExtra(notificationId,binding.medicinesSpinner.selectedItem.hashCode() + LocalTime.now().second)

        val pendingIntent = PendingIntent.getBroadcast(
            requireActivity().applicationContext,
            binding.medicinesSpinner.selectedItem.hashCode() + LocalTime.now().second,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

        ScreenMessages.reminderDone(requireActivity() ,time)
    }




    private fun getTime() : Long
    {
        val minute = binding.timerSelector.minute
        val hour = binding.timerSelector.hour
        val day = binding.dateSelector.dayOfMonth
        val month = binding.dateSelector.month
        val year = binding.dateSelector.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel()
    {
        val name = "Noif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc

        val notificacionManager = requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificacionManager.createNotificationChannel(channel)
    }
}