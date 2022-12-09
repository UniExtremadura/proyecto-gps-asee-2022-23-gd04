package es.unex.giiis.medicinex.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.data.database.entities.Excipiente

import es.unex.giiis.medicinex.databinding.ItemExcipienteBinding

class ExcipienteViewHolder(view : View) : RecyclerView.ViewHolder(view)
{
    val binding = ItemExcipienteBinding.bind(view)

    fun render(excipiente : Excipiente)
    {
        binding.excipiente.text = excipiente.toString()
    }
}