package es.unex.giiis.medicinex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.medicinex.view.adapter.ExcipienteAdapter
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.databinding.FragmentMedicineExcipientesBinding

class MedicineExcipientes(private var medicinaEntity: MedicinaEntity) : Fragment()
{
    private lateinit var binding : FragmentMedicineExcipientesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMedicineExcipientesBinding.inflate(inflater, container, false)

        val recyclerView = binding.excipientesRV
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = ExcipienteAdapter(medicinaEntity.excipientes?.toList()!!)

        return binding.root
    }
}