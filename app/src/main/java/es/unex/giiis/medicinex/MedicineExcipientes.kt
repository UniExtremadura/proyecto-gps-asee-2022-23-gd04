package es.unex.giiis.medicinex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.medicinex.adapter.ExcipienteAdapter
import es.unex.giiis.medicinex.database.Medicina
import es.unex.giiis.medicinex.databinding.FragmentMedicineExcipientesBinding

class MedicineExcipientes(private var medicina: Medicina) : Fragment()
{
    private lateinit var binding : FragmentMedicineExcipientesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMedicineExcipientesBinding.inflate(inflater, container, false)

        val recyclerView = binding.excipientesRV
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = ExcipienteAdapter(medicina.excipientes?.toList()!!)

        return binding.root
    }
}