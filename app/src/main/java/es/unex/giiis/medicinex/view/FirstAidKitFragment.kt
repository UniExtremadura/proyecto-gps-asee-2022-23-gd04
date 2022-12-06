package es.unex.giiis.medicinex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.view.adapter.ItemFAKAdapter
import es.unex.giiis.medicinex.data.database.MedicinexDB
import es.unex.giiis.medicinex.databinding.FragmentFirstAidKitBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstAidKitFragment : Fragment()
{
    private lateinit var binding : FragmentFirstAidKitBinding
    private lateinit var database : FirebaseDatabase
    private var medicinas : MutableList<String> = mutableListOf()
    private lateinit var adapter : ItemFAKAdapter
    private lateinit var fakRef : DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentFirstAidKitBinding.inflate(inflater, container, false)

        database = Firebase.database

        updateUI()

        return binding.root
    }


    internal fun updateUI()
    {
        if(MedicinexApp.isThereInternet)
        {
            fakRef = database.getReference("accounts/" + GeneralUtilities.getAccountNameByMail(FirebaseAuth.getInstance().currentUser?.email.toString()) + "/firstAidKit")

            medicinas = mutableListOf()

            fakRef.get().addOnSuccessListener {

                for(medicina in it.children)
                {
                    if(medicina.value != "-1")
                    {
                        medicinas.add(medicina.value.toString())
                    }
                }

                lifecycleScope.launch(Dispatchers.Main) {

                    val recyclerView = binding.fakRV
                    recyclerView.layoutManager = LinearLayoutManager(activity)

                    adapter = ItemFAKAdapter(
                        medicinas,
                        onClickListener = { medicina -> onItemSelected(medicina) },
                        onClickDelete = { position -> onDeletedItem(position) })

                    recyclerView.adapter = adapter
                }
            }
        }
        else
        {
            ScreenMessages.showDialog(requireActivity(), R.string.no_internet, R.string.no_internet_message)
        }
    }

    private fun onItemSelected(medicina: String)
    {// Apertura de los datos del medicamento seleccionado del botiquín en la actividad de información de medicamento.
        lifecycleScope.launch(Dispatchers.IO) {
            val medi =  MedicinexDB.getDatabase(requireActivity()).medicineDao().buscarPorNombre(medicina)
            if(medi != null)
            {
                if(medi.size > 0)
                {
                    (activity as Menu).showMedicineInfo(medi.first().nRegistro)
                }
            }
        }
    }

    private fun onDeletedItem(position : Int)
    {// Eliminación de un medicamento dada su posición en el botiquín.

        val name: String = medicinas[position]
        medicinas.removeAt(position)
        adapter.notifyItemRemoved(position)

        fakRef.get().addOnSuccessListener {
            for (medicina in it.children)
            {
                if (medicina.value == name)
                {
                    Firebase.database.getReference(
                        "accounts/" + GeneralUtilities.getAccountNameByMail(FirebaseAuth.getInstance().currentUser?.email.toString()) + "/firstAidKit" + "/${medicina.key}"
                    ).removeValue()
                }
            }
        }
    }
}