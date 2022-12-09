package es.unex.giiis.medicinex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.medicinex.view.adapter.PActivoAdapter
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.databinding.FragmentMedicinePActivosBinding

class MedicinePActivos(private var medicinaEntity: MedicinaEntity) : Fragment()
{
    private lateinit var binding : FragmentMedicinePActivosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMedicinePActivosBinding.inflate(inflater, container, false)

        val recyclerView = binding.pActivosRV
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = PActivoAdapter(medicinaEntity.pActivos?.toList()!!)

        return binding.root
    }
}