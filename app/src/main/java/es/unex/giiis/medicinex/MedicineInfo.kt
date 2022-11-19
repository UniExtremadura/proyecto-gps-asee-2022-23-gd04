package es.unex.giiis.medicinex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import es.unex.giiis.medicinex.database.Medicina
import es.unex.giiis.medicinex.database.MedicinexDB
import es.unex.giiis.medicinex.databinding.ActivityMedicineInfoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.Intent

class MedicineInfo : AppCompatActivity()
{
    companion object
    {
        private const val N_REGISTRO = "nregistro"
    }

    private var nregistro: String? = null
    private lateinit var binding : ActivityMedicineInfoBinding
    private lateinit var medicamento : Medicina
    private lateinit var db : MedicinexDB


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nregistro = intent.getStringExtra(N_REGISTRO)
        db = MedicinexDB.getDatabase(this)

        binding.closeButtonImage.setOnClickListener {
            closeAction()
        }

        binding.shareButton.setOnClickListener {
            shareMedicine()
        }

        val viewPager = binding.viewPager
        val tablayout = binding.tablayout


        lifecycleScope.launch(Dispatchers.Main)
        {

            withContext(Dispatchers.IO)
            {
                medicamento = db.medicineDao().buscarPorNRegistro(nregistro!!)
            }

            withContext(Dispatchers.Main)
            {

                binding.medicineName.text = medicamento.nombre
                val fragmentAdapter = FragmentAdapter(supportFragmentManager)
                fragmentAdapter.addFragment(MedicineInformation(medicamento), "INFO")
                fragmentAdapter.addFragment(MedicineExcipientes(medicamento), "Excipientes")
                fragmentAdapter.addFragment(MedicinePActivos(medicamento), "P. Activos")

                viewPager.adapter = fragmentAdapter
                tablayout.setupWithViewPager(viewPager)
            }
        }
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
                putExtra(Intent.EXTRA_TEXT, medicamento.toString())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }catch(e : Exception){/**/}
    }
}