package es.unex.giiis.medicinex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.CatalogueProvider
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.view.adapter.MedicinaAdapter
import es.unex.giiis.medicinex.data.database.entities.LetterEntity
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.data.database.MedicinexDB
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import es.unex.giiis.medicinex.databinding.FragmentMainMenuBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages

class MainMenu : Fragment()
{
    private lateinit var _binding : FragmentMainMenuBinding
    private val binding get() = _binding
    private lateinit var adapter : MedicinaAdapter
    private var results : MutableList<MedicinaEntity> = mutableListOf()
    private var filteredResults : MutableList<MedicinaEntity> = mutableListOf()
    private lateinit var db : MedicinexDB
    private lateinit var med: MedicinaEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        db = MedicinexDB.getDatabase(activity)

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

        binding.searchButton.setOnClickListener {
            if(binding.searchButton.isEnabled)
            {
                if(binding.queryText.text.isNotEmpty())
                {
                    val letra : Char = binding.queryText.text.toString().first().uppercaseChar()

                    val query : String = binding.queryText.text.toString()

                    if(binding.queryText.text.toString().length >= 4 && (letra != ' '))
                    {
                        checkForFilter()

                        binding.searchButton.isEnabled = false
                        binding.queryText.isEnabled = false

                        lifecycleScope.launch  {

                            withContext(Dispatchers.Main)
                            {
                                clearRV()
                                binding.checkBoxReceipt.isVisible = false
                                binding.checkBoxReceipt.isChecked = false
                                adapter.updateMedicines(results)
                            }

                            val letterEntity : LetterEntity? = db.letterDao().getLetter(letra)


                            if(letterEntity != null)
                            {// Ya existía esa letra

                                val results = db.medicineDao().buscarPorNombre(query)

                                if(results != null)
                                {

                                    if(results.size > 0)
                                    {
                                        for(item in results)
                                        {
                                            withContext(Dispatchers.Main)
                                            {
                                                addItemToRV(item)
                                            }
                                        }
                                    }
                                    else
                                    {
                                        withContext(Dispatchers.Main)
                                        {
                                            Toast.makeText(requireActivity(),
                                                R.string.no_results_found, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                            else
                            {// No existía la letra, por tanto, hay que descargar la letra de Firebase.
                                // Descargar la letra supone bajarse todos los medicamentos que empiecen por esa letra.

                                var failed = false

                                if(MedicinexApp.isThereInternet)
                                {
                                    val secciones = CatalogueProvider.categories
                                    withContext(Dispatchers.Main)
                                    {
                                        Toast.makeText(requireActivity(), R.string.searching, Toast.LENGTH_LONG).show()
                                    }

                                    for (section in secciones)
                                    {
                                        if(MedicinexApp.isThereInternet)
                                        {
                                            val secc = Firebase.database.getReference("$section/$letra").get().await()

                                            if (secc != null)
                                            {
                                                for (medicamento in secc.children)
                                                {
                                                    med = GeneralUtilities.parseMedicine(medicamento)
                                                    db.medicineDao().insert(med)

                                                    if (med.nombre!!.startsWith(query, true))
                                                    {
                                                        withContext(Dispatchers.Main)
                                                        {
                                                            addItemToRV(med)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else
                                        {
                                            failed = true
                                            break
                                        }
                                    }

                                    if(!failed)
                                    {
                                        db.letterDao().insertLetter(LetterEntity(letra))
                                    }

                                    if(results.size == 0)
                                    {
                                        withContext(Dispatchers.Main)
                                        {
                                            Toast.makeText(requireActivity(),
                                                R.string.no_results_found, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                                else
                                {
                                    ScreenMessages.showDialog(requireActivity(), R.string.no_internet, R.string.no_internet_message)
                                }
                            }

                            withContext(Dispatchers.Main)
                            {
                                binding.checkBoxReceipt.isVisible = true
                                binding.searchButton.isEnabled = true
                                binding.queryText.isEnabled = true
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(requireActivity(), R.string.insuficient_query_length, Toast.LENGTH_LONG).show()
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
            filteredResults = results.filter { medicina -> medicina.receta == false } as MutableList<MedicinaEntity>
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

    private fun addItemToRV(medicinaEntity : MedicinaEntity)
    {
        results.add(medicinaEntity)
        adapter.notifyItemInserted(results.size - 1)
    }

    private fun onItemSelected(medicinaEntity: MedicinaEntity)
    {// Aquí irán las acciones que se producirán cuando se pulse sobre el frame de una medicina.
        (activity as Menu).showMedicineInfo(medicinaEntity.nRegistro)
    }
}