package es.unex.giiis.medicinex

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import es.unex.giiis.medicinex.databinding.ActivityMenuBinding

class Menu : AppCompatActivity()
{
    private lateinit var binding: ActivityMenuBinding
    private lateinit var fragmentManager: FragmentManager
    private var mainMenu : MainMenu? = null
    private var reminders : Reminders? = null
    private var firstAidKit : FirstAidKitFragment? = null
    private var activeFragments = booleanArrayOf(false, false, false, false) // MainMenu / Reminders / First aid kit / Maps
    private lateinit var netManager : NetworkChangeListener

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        netManager = NetworkChangeListener()
        fragmentManager = supportFragmentManager

        addOrShowFragment(0)

        binding.bottomNavView.setOnItemSelectedListener {

            when (it.itemId)
            {
                R.id.init ->
                {
                    addOrShowFragment( 0)
                }

                R.id.reminders ->
                {
                    addOrShowFragment( 1)
                }

                R.id.first_aid_kit ->
                {
                    addOrShowFragment(2)
                }
            }
            true
        }
    }

    override fun onStart()
    {
        @Suppress("DEPRECATION") val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(netManager, intentFilter)
        super.onStart()
    }


    override fun onStop()
    {
        unregisterReceiver(netManager)
        super.onStop()
    }

    override fun onBackPressed() {}

    private fun hideFragment(fragment: Fragment?, pos : Int)
    {
        if(fragment != null)
        {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.hide(fragment)
            fragmentTransaction.commit()
            activeFragments[pos] = false
        }
    }

    private fun addOrShowFragment(pos : Int)
    {
        for(i in 0..3)
        {
            if(i != pos)
            {
                if(activeFragments[i]) { hideFragment(getFragmentByPos(i),i)}
            }
        }

        val fragmentTransaction = fragmentManager.beginTransaction()

        if(getFragmentByPos(pos) == null)
        {

            when(pos)
            {
                0 -> { mainMenu = MainMenu();  fragmentTransaction.add(binding.frameLayout.id, mainMenu!!) }
                1 -> { reminders = Reminders();  fragmentTransaction.add(binding.frameLayout.id, reminders!!) }
                2 -> { firstAidKit = FirstAidKitFragment(); fragmentTransaction.add(binding.frameLayout.id, firstAidKit!!) }
            }
        }
        else
        {
            fragmentTransaction.show(getFragmentByPos(pos)!!)

            when(pos)
            {
                1 -> reminders!!.updateUI()
                2 -> firstAidKit!!.updateUI()
            }
        }

        fragmentTransaction.commit()
        activeFragments[pos] = true
    }


    private fun getFragmentByPos(pos : Int) : Fragment?
    {
       when(pos)
       {
           0 -> { return mainMenu }
           1 -> { return reminders }
           2 -> { return firstAidKit }
       }
        return null
    }

    internal fun openSettings()
    {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
    }

    internal fun showMedicineInfo(nregistro : String)
    {
        val intent = Intent(this, MedicineInfo::class.java)
        intent.putExtra("nregistro", nregistro)
        startActivity(intent)
    }
}