package es.unex.giiis.medicinex

import android.app.AlertDialog
import android.content.Context
import android.text.format.DateFormat
import java.util.*

abstract class ScreenMessages
{
    companion object
    {
        fun invalidPassword(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.invalid_password_title)
            builder.setMessage(R.string.invalid_password_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun invalidMailAddress(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.invalid_mail_title)
            builder.setMessage(R.string.invalid_mail_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun invalidMailPassword(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.invalid_mail_password_title)
            builder.setMessage(R.string.invalid_mail_password_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun incorrectCredentials(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.incorrect_credentials_title)
            builder.setMessage(R.string.incorrect_credentials_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun noInternetConnection(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.no_internet)
            builder.setMessage(R.string.no_internet_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun accountCreatedSuccessfully(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.account_created_title)
            builder.setMessage(R.string.account_created_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun accountAlreadyExists(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.account_already_exists_title)
            builder.setMessage(R.string.account_already_exists_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun accountInvalidCredentials(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.incorrect_credentials_title)
            builder.setMessage(R.string.account_invalid_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun resetEmailSent(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.reset_password_title)
            builder.setMessage(R.string.reset_password_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun resetEmailSentError(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.reset_password_error_title)
            builder.setMessage(R.string.reset_password_error_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun accountUpdatedSuccessfully(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.account_updated_successfully_title)
            builder.setMessage(R.string.account_updated_successfully_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun accountUpdateFailed(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.account_updated_failed_title)
            builder.setMessage(R.string.account_updated_failed_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun accountDeleteFailure(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.account_updated_failed_title)
            builder.setMessage(R.string.account_updated_failed_message)
            builder.setPositiveButton(R.string.accept, null)

            val dial : AlertDialog = builder.create()
            dial.show()
        }

        fun firstAidKitCleaned(context : Context)
        {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.first_aid_kit_cleaned_title)
            builder.setMessage(R.string.first_aid_kit_cleaned_message)
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