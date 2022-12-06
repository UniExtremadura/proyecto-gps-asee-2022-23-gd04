package es.unex.giiis.medicinex.utilities

import android.app.AlertDialog
import android.content.Context
import android.text.format.DateFormat
import es.unex.giiis.medicinex.R
import java.util.*

abstract class ScreenMessages
{
    companion object
    {
        fun showDialog(context : Context, titleId : Int, messageId : Int)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(titleId)
            builder.setMessage(messageId)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun reminderDone(context : Context, time : Long)
        {
            val date = Date(time)
            val dateFormat = DateFormat.getLongDateFormat(context)
            val timeFormat = DateFormat.getTimeFormat(context)

            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.reminder_created)
            builder.setMessage("El recordatorio está programado para el: " + dateFormat.format(date) + " a las " + timeFormat.format(date))
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }
    }
}