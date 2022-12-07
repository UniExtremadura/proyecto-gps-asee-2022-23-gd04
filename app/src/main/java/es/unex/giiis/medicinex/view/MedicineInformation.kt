package es.unex.giiis.medicinex.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.databinding.FragmentMedicineInformationBinding
import android.app.DownloadManager
import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.utilities.GeneralUtilities
import es.unex.giiis.medicinex.viewmodel.MedicineViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MedicineInformation(private var medicinaEntity : MedicinaEntity) : Fragment()
{
    private lateinit var binding : FragmentMedicineInformationBinding
    private val medicineViewModel : MedicineViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMedicineInformationBinding.inflate(inflater, container, false)

        try
        {
            val via = medicinaEntity.vAdministracion?.first()?.nombre + " : " + medicinaEntity.formaFarma?.nombre
            val lab = "Creado por " + medicinaEntity.labTitular
            val identifiers = "Nº registro: " + medicinaEntity.nRegistro + "\nCódigo nacional: " + medicinaEntity.presentaciones?.first()?.cn
            binding.name.text = medicinaEntity.nombre
            binding.presc.text = medicinaEntity.cPresc
            binding.via.text = via
            binding.dosis.text = medicinaEntity.dosis
            binding.lab.text = lab
            binding.identifiers.text = identifiers

            if(medicinaEntity.conduc == false) { binding.conducIv.setImageResource(R.drawable.save_driving) }
            else { binding.conducIv.setImageResource(R.drawable.unsafe_driving) }

            if(medicinaEntity.comerc == true) { binding.merchantableIv.setImageResource(R.drawable.merchantable) }
            else { binding.merchantableIv.setImageResource(R.drawable.not_merchantable) }

            if(medicinaEntity.receta == true) { binding.needsReceiptIv.setImageResource(R.drawable.prescription) }
            else { binding.needsReceiptIv.setImageResource(R.drawable.without_prescription) }


        }catch (e : Exception){/**/}


        medicineViewModel.saveMedicineResult.observe(requireActivity(), Observer {
            when(it)
            {
                true -> { Toast.makeText(requireActivity(), R.string.medicine_added_to_afk, Toast.LENGTH_SHORT).show() }

                false -> { Toast.makeText(requireActivity(),R.string.medicine_already_exists_in_afk, Toast.LENGTH_SHORT).show() }
            }
        })

        binding.downloadPdf.setOnClickListener {

            if(medicinaEntity.docs?.isNotEmpty() == true)
            {
                if (medicinaEntity.docs?.first()?.url != null)
                {
                    Toast.makeText(requireActivity(), R.string.downloading_file, Toast.LENGTH_SHORT).show()
                    downloadPdf(medicinaEntity.docs?.first()?.url!!, medicinaEntity.nombre!!)
                }
            }
            else
            {
                Toast.makeText(requireActivity(), R.string.pdf_does_not_exist, Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchOnWeb.setOnClickListener {

            if(medicinaEntity.docs?.isNotEmpty() == true)
            {
                if(medicinaEntity.docs?.first()?.urlHtml != null)
                {
                    searchOnWeb(medicinaEntity.docs?.first()?.urlHtml!!)
                }
            }
            else
            {
                Toast.makeText(requireActivity(), R.string.webpage_does_not_exist, Toast.LENGTH_SHORT).show()
            }
        }

        binding.addToFAK.setOnClickListener {
            addMedicineToAFK()
        }

        return binding.root
    }

    private fun downloadPdf(url : String, nombre : String)
    {
        lifecycleScope.launch {
            try
            {
                val request = DownloadManager.Request(Uri.parse(url))
                    .setTitle(nombre)
                    .setDescription("Descargando medicamento... ")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedOverMetered(true)

                val  dm: DownloadManager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                dm.enqueue(request)

            }catch(e : Exception) {/**/}
        }
    }

    private fun searchOnWeb(url: String)
    {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)

        } catch (e: Exception) {/**/}

    }

    private fun addMedicineToAFK()
    {
        try
        {
            if (MedicinexApp.isThereInternet && medicinaEntity.nombre != null)
            {
               medicineViewModel.saveMedicineAfk(medicinaEntity.nombre!!)
            }
        }catch(e : Exception) {/**/}
    }
}