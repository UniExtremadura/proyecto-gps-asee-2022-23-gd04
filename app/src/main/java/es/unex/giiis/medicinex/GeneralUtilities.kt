package es.unex.giiis.medicinex

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue
import es.unex.giiis.medicinex.database.*

abstract class GeneralUtilities
{
    companion object
    {
        fun isThereInternet(context: Context): Boolean
        {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null)
            {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                {
                    return true
                }
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                {
                    return true
                }
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                {
                    return true
                }
            }
            return false
        }

        fun getAccountNameByMail(email : String) : String
        {
            return email.substring(0, email.indexOf('@'))
        }
    }
}