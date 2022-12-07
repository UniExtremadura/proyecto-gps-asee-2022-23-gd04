package es.unex.giiis.medicinex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.CatalogueProvider
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.view.adapter.MedicinaAdapter
import es.unex.giiis.medicinex.data.database.entities.LetterEntity
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.data.database.MedicinexDB
import es.unex.giiis.medicinex.data.model.MedicineModel
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import es.unex.giiis.medicinex.databinding.FragmentMainMenuBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel

@AndroidEntryPoint
class MainMenu : Fragment()
{
    private lateinit var _binding : FragmentMainMenuBinding
    private val binding get() = _binding
    private lateinit var adapter : MedicinaAdapter
    private var results : MutableList<MedicineModel> = mutableListOf()
    private var filteredResults : MutableList<MedicineModel> = mutableListOf()
    private lateinit var med: MedicinaEntity

    private val medicineViewModel : MedicineViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        binding.checkBoxReceipt.isVisible = false
        val recyclerView = binding.medicineRv
        recyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = MedicinaAdapter(results) { medicina ->
            onItemSelected(
                medicina
            )
        }
        recyclerView.adapter = adapter

        binding.checkBoxReceipt.setOnClickListener {
            checkForFilter()
        }

        medicineViewModel.medicinesSearched.observe(requireActivity(), Observer{

            for(med in it)
            {
                if(!results.contains(med))
                {
                    results.add(med)
                    addItemToRV(med)
                }
            }

           if (results.size == 0) { Toast.makeText(requireActivity(), R.string.no_results_found, Toast.LENGTH_SHORT).show() }

        })

        binding.searchButton.setOnClickListener {
            if(binding.searchButton.isEnabled)
            {
                if(binding.queryText.text.isNotEmpty())
                {
                    val letra: Char = binding.queryText.text.toString().first().uppercaseChar()
                    val query: String = binding.queryText.text.toString()

                    if (binding.queryText.text.toString().length >= 4 && (letra != ' '))
                    {
                        checkForFilter()

                        binding.searchButton.isEnabled = false
                        binding.queryText.isEnabled = false

                        lifecycleScope.launch {

                            if (MedicinexApp.isThereInternet)
                            {
                                withContext(Dispatchers.Main)
                                {
                                    Toast.makeText(requireActivity(), R.string.searching, Toast.LENGTH_LONG).show()
                                    clearRV()
                                    binding.checkBoxReceipt.isVisible = false
                                    binding.checkBoxReceipt.isChecked = false
                                    adapter.updateMedicines(results)
                                }

                                medicineViewModel.searchWithFilter(query)
                            } else {
                                ScreenMessages.showDialog(
                                    requireActivity(),
                                    R.string.no_internet,
                                    R.string.no_internet_message
                                )
                            }

                            withContext(Dispatchers.Main)
                            {
                                binding.checkBoxReceipt.isVisible = true
                                binding.searchButton.isEnabled = true
                                binding.queryText.isEnabled = true
                            }
                        }
                    } else
                    {
                        Toast.makeText(
                            requireActivity(),
                            R.string.insuficient_query_length,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                else
                {
                    Toast.makeText(requireActivity(), R.string.insuficient_query_length, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.openCatalogue.setOnClickListener {
            (activity as Menu).openCatalogue()
        }

        binding.openSettings.setOnClickListener{
            (activity as Menu).openSettings()
        }

        return binding.root
    }

    private fun checkForFilter()
    {
        if (binding.checkBoxReceipt.isChecked)
        {
            filteredResults = results.filter { medicina -> medicina.receta == false } as MutableList<MedicineModel>
            adapter.updateMedicines(filteredResults)
        }
        else
        {
            adapter.updateMedicines(results)
        }
    }

    private fun clearRV()
    {
        val size: Int = if(binding.checkBoxReceipt.isChecked) {filteredResults.size} else { results.size }
        results.clear()
        filteredResults.clear()
        adapter.notifyItemRangeRemoved(0, size)
    }

    private fun addItemToRV(medicineModel : MedicineModel)
    {
        results.add(medicineModel)
        adapter.notifyItemInserted(results.size - 1)
    }

    private fun onItemSelected(medicineModel: MedicineModel)
    {// Aquí irán las acciones que se producirán cuando se pulse sobre el frame de una medicina.
        (activity as Menu).showMedicineInfo(medicineModel.nRegistro)
    }
}