package es.unex.giiis.medicinex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.adapter.MedicinaAdapter
import es.unex.giiis.medicinex.database.Categorie
import es.unex.giiis.medicinex.database.Medicina
import es.unex.giiis.medicinex.database.MedicinexDB
import es.unex.giiis.medicinex.databinding.ActivitySectionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Section : AppCompatActivity()
{
    companion object
    {
        private const val SECTION = "section"
    }

    private lateinit var binding : ActivitySectionBinding
    private var sectionName: String? = null
    private var categorieMedicines : MutableList<Medicina> = mutableListOf()
    private lateinit var adapter : MedicinaAdapter
    private lateinit var db : MedicinexDB
    private lateinit var med : Medicina

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MedicinexDB.getDatabase(this)

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


        lifecycleScope.launch {

            val categorie = db.sectionDao().getCategorie(Categorie(sectionName!!))

            if(categorie != null)
            {// Ya existía la categoría:

                val medicinasExistentes = db.medicineDao().buscarPorCategoria(sectionName!!)

                withContext(Dispatchers.Main)
                {

                    for (medicina in medicinasExistentes)
                    {
                        categorieMedicines.add(medicina)
                        adapter.notifyItemInserted(categorieMedicines.size - 1)
                    }
                }
            }
            else
            {// No existía la categoría: bajarla de Firebase.

                if(GeneralUtilities.isThereInternet(binding.sectionName.context))
                {
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(binding.sectionName.context, R.string.downloading_data_please_wait, Toast.LENGTH_SHORT).show()

                        val categoria = Firebase.database.getReference(sectionName!!).get().await()

                        for(letra in categoria.children)
                        {
                            for(medicina in letra.children)
                            {
                                med = GeneralUtilities.parseMedicine(medicina)
                                db.medicineDao().insert(med)

                                categorieMedicines.add(med)
                                adapter.notifyItemInserted(categorieMedicines.size - 1)
                            }
                        }

                        db.sectionDao().insertCategorie(Categorie(sectionName!!))
                    }
                }
                else
                {
                    withContext(Dispatchers.Main)
                    {
                        ScreenMessages.noInternetConnection(binding.sectionName.context)
                    }
                }
            }
        }
    }

    private fun closeSection()
    {
        onBackPressed()
    }

    private fun onItemSelected(medicina : Medicina)
    {
        val intent = Intent(this, MedicineInfo::class.java)
        intent.putExtra("nregistro", medicina.nRegistro)
        startActivity(intent)
    }
}