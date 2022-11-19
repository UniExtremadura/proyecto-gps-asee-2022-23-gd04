package es.unex.giiis.medicinex

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
import es.unex.giiis.medicinex.database.Medicina
import es.unex.giiis.medicinex.databinding.FragmentMedicineInformationBinding
import android.app.DownloadManager
import android.content.Context
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MedicineInformation(private var medicina : Medicina) : Fragment()
{
    private lateinit var binding : FragmentMedicineInformationBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMedicineInformationBinding.inflate(inflater, container, false)

        try
        {
            val via = medicina.vAdministracion?.first()?.nombre + " : " + medicina.formaFarma?.nombre
            val lab = "Creado por " + medicina.labTitular
            val identifiers = "Nº registro: " + medicina.nRegistro + "\nCódigo nacional: " + medicina.presentaciones?.first()?.cn
            binding.name.text = medicina.nombre
            binding.presc.text = medicina.cPresc
            binding.via.text = via
            binding.dosis.text = medicina.dosis
            binding.lab.text = lab
            binding.identifiers.text = identifiers

            if(medicina.conduc == true) { binding.conducIv.setImageResource(R.drawable.save_driving) }
            else { binding.conducIv.setImageResource(R.drawable.unsafe_driving) }

            if(medicina.comerc == true) { binding.merchantableIv.setImageResource(R.drawable.merchantable) }
            else { binding.merchantableIv.setImageResource(R.drawable.not_merchantable) }

            if(medicina.receta == true) { binding.needsReceiptIv.setImageResource(R.drawable.prescription) }
            else { binding.needsReceiptIv.setImageResource(R.drawable.without_prescription) }


        }catch (e : Exception){/**/}

        binding.downloadPdf.setOnClickListener {

            if(medicina.docs?.isNotEmpty() == true)
            {
                if (medicina.docs?.first()?.url != null)
                {
                    Toast.makeText(requireActivity(), R.string.downloading_file, Toast.LENGTH_SHORT).show()
                    downloadPdf(medicina.docs?.first()?.url!!, medicina.nombre!!)
                }
            }
            else
            {
                Toast.makeText(requireActivity(), R.string.pdf_does_not_exist, Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchOnWeb.setOnClickListener {

            if(medicina.docs?.isNotEmpty() == true)
            {
                if(medicina.docs?.first()?.urlHtml != null)
                {
                    searchOnWeb(medicina.docs?.first()?.urlHtml!!)
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
        var exists = false
        try
        {
            if (GeneralUtilities.isThereInternet(requireActivity()))
            {
                val database = Firebase.database

                val fakRef = database.getReference("accounts/" + GeneralUtilities.getAccountNameByMail(FirebaseAuth.getInstance().currentUser?.email.toString()) + "/firstAidKit")

                fakRef.get().addOnSuccessListener {

                    for(child in it.children)
                    {
                        if(child.value == medicina.nombre) { exists = true; break; }
                    }
                    if(!exists)
                    {
                        fakRef.push().setValue(medicina.nombre)
                        Toast.makeText(requireActivity(), R.string.medicine_added_to_afk, Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(requireActivity(), R.string.medicine_already_exists_in_afk, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }catch(e : Exception) {/**/}
    }
}