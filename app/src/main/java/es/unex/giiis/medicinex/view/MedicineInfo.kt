package es.unex.giiis.medicinex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.data.database.MedicinexDB
import es.unex.giiis.medicinex.databinding.ActivityMedicineInfoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.FragmentAdapter
import es.unex.giiis.medicinex.data.database.entities.toModel
import es.unex.giiis.medicinex.data.model.toEntity
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel

@AndroidEntryPoint
class MedicineInfo : AppCompatActivity()
{
    companion object
    {
        private const val N_REGISTRO = "nregistro"
    }

    private var nregistro: String? = null
    private lateinit var binding : ActivityMedicineInfoBinding
    private lateinit var medicamento : MedicinaEntity
    private val medicineViewModel : MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nregistro = intent.getStringExtra(N_REGISTRO)

        val viewPager = binding.viewPager
        val tablayout = binding.tablayout

        binding.closeButtonImage.setOnClickListener {
            closeAction()
        }

        binding.shareButton.setOnClickListener {
            shareMedicine()
        }

        medicineViewModel.viewMedicineData(nregistro!!)

        medicineViewModel.medicineModel.observe(this, Observer{

            if(it != null)
            {
                medicamento = it.toEntity()

                binding.medicineName.text = medicamento.nombre
                val fragmentAdapter = FragmentAdapter(supportFragmentManager)
                fragmentAdapter.addFragment(MedicineInformation(medicamento), "INFO")
                fragmentAdapter.addFragment(MedicineExcipientes(medicamento), "Excipientes")
                fragmentAdapter.addFragment(MedicinePActivos(medicamento), "P. Activos")

                viewPager.adapter = fragmentAdapter
                tablayout.setupWithViewPager(viewPager)
            }
        })
    }

    private fun closeAction()
    {
        onBackPressed()
    }

    private fun shareMedicine()
    {
        try
        {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, medicamento.toModel().toString())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }catch(e : Exception){/**/}
    }
}