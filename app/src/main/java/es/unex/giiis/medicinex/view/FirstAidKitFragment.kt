package es.unex.giiis.medicinex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.view.adapter.ItemFAKAdapter
import es.unex.giiis.medicinex.data.database.MedicinexDB
import es.unex.giiis.medicinex.databinding.FragmentFirstAidKitBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstAidKitFragment : Fragment()
{
    private lateinit var binding : FragmentFirstAidKitBinding
    private var medicinas : MutableList<String> = mutableListOf()
    private lateinit var adapter : ItemFAKAdapter

    private val medicineViewModel : MedicineViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentFirstAidKitBinding.inflate(inflater, container, false)

        updateUI()

        medicineViewModel.medicineFromName.observe(requireActivity(), Observer{
            val medi =  it

            if(medi != null)
            {
                (activity as Menu).showMedicineInfo(medi.nRegistro)
            }
        })

        medicineViewModel.medicinesFAK.observe(requireActivity(), Observer{

            medicinas = it

            lifecycleScope.launch(Dispatchers.Main) {

                val recyclerView = binding.fakRV
                recyclerView.layoutManager = LinearLayoutManager(activity)

                adapter = ItemFAKAdapter(medicinas, onClickListener = { medicina -> onItemSelected(medicina) }, onClickDelete = { position -> onDeletedItem(position) })

                recyclerView.adapter = adapter
            }
        })

        medicineViewModel.deleteUserResult.observe(requireActivity(), Observer{
            when(it)
            {
                true -> { Toast.makeText(requireActivity(), R.string.delete_medicine_success, Toast.LENGTH_SHORT).show() }
                false -> { Toast.makeText(requireActivity(), R.string.delete_medicine_fail, Toast.LENGTH_SHORT).show() }
            }
        })

        return binding.root
    }


    internal fun updateUI()
    {
        if(MedicinexApp.isThereInternet)
        {
            medicinas = mutableListOf()

            medicineViewModel.getFAKMedicines()
        }
        else
        {
            ScreenMessages.showDialog(requireActivity(), R.string.no_internet, R.string.no_internet_message)
        }
    }

    private fun onItemSelected(medicina: String)
    {// Apertura de los datos del medicamento seleccionado del botiquín en la actividad de información de medicamento.
        lifecycleScope.launch(Dispatchers.IO) {
            medicineViewModel.getMedicineByName(medicina)
        }
    }

    private fun onDeletedItem(position : Int)
    {// Eliminación de un medicamento dada su posición en el botiquín.
        val name: String = medicinas[position]
        medicinas.removeAt(position)
        adapter.notifyItemRemoved(position)
        medicineViewModel.deleteMedicine(name)
    }
}