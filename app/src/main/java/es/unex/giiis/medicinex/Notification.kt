package es.unex.giiis.medicinex

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val notificationId = "notifId"
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messsageExtra"

class Notification : BroadcastReceiver()
{
    override fun onReceive(context : Context, intent : Intent)
    {
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.remidner)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(intent.getIntExtra(notificationId, 1), notification)

    }
}