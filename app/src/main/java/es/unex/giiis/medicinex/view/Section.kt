package es.unex.giiis.medicinex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.view.adapter.MedicinaAdapter
import es.unex.giiis.medicinex.data.database.entities.CategorieEntity
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.data.database.MedicinexDB
import es.unex.giiis.medicinex.data.model.MedicineModel
import es.unex.giiis.medicinex.databinding.ActivitySectionBinding
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.utilities.ScreenMessages
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class Section : AppCompatActivity()
{
    companion object
    {
        private const val SECTION = "section"
    }

    private lateinit var binding : ActivitySectionBinding
    private var sectionName: String? = null
    private var categorieMedicines : MutableList<MedicineModel> = mutableListOf()
    private lateinit var adapter : MedicinaAdapter
    private lateinit var med : MedicineModel
    private val medicineViewModel : MedicineViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sectionName = intent.getStringExtra(SECTION)

        binding.closeButtonImage.setOnClickListener {
            closeSection()
        }

        binding.sectionName.text = sectionName

        val recyclerView = binding.sectionRV
        recyclerView.layoutManager = LinearLayoutManager(binding.sectionName.context)
        adapter = MedicinaAdapter(categorieMedicines) { medicina ->
            onItemSelected(medicina)
        }
        recyclerView.adapter = adapter

        medicineViewModel.sectionMedicinesResult.observe(this, Observer{

            for (medicina in it)
            {
                categorieMedicines.add(medicina)
                adapter.notifyItemInserted(categorieMedicines.size - 1)
            }

        })

        lifecycleScope.launch(Dispatchers.IO) {

            if(MedicinexApp.isThereInternet)
            {
                withContext(Dispatchers.Main)
                {
                    Toast.makeText(binding.sectionName.context, R.string.downloading_data_please_wait, Toast.LENGTH_SHORT).show()
                }

                medicineViewModel.retrieveSectionMedicines(sectionName!!)
            }
            else
            {
                withContext(Dispatchers.Main)
                {
                        ScreenMessages.showDialog(binding.sectionName.context, R.string.no_internet, R.string.no_internet_message)
                }
            }
        }
    }

    private fun closeSection()
    {
        onBackPressed()
    }

    private fun onItemSelected(medicinaModel : MedicineModel)
    {
        val intent = Intent(this, MedicineInfo::class.java)
        intent.putExtra("nregistro", medicinaModel.nRegistro)
        startActivity(intent)
    }
}