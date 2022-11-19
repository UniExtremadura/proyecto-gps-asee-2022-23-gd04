package es.unex.giiis.medicinex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import es.unex.giiis.medicinex.databinding.FragmentMainMenuBinding

class MainMenu : Fragment()
{
    private lateinit var _binding : FragmentMainMenuBinding
    private val binding get() = _binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        binding.openSettings.setOnClickListener{
            (activity as Menu).openSettings()
        }

        return binding.root
    }
}