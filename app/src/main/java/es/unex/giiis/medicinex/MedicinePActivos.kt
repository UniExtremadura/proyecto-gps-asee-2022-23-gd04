package es.unex.giiis.medicinex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.medicinex.adapter.PActivoAdapter
import es.unex.giiis.medicinex.database.Medicina
import es.unex.giiis.medicinex.databinding.FragmentMedicinePActivosBinding

class MedicinePActivos(private var medicina: Medicina) : Fragment()
{
    private lateinit var binding : FragmentMedicinePActivosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMedicinePActivosBinding.inflate(inflater, container, false)

        val recyclerView = binding.pActivosRV
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = PActivoAdapter(medicina.pActivos?.toList()!!)

        return binding.root
    }
}