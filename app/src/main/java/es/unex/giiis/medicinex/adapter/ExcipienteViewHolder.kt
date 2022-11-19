package es.unex.giiis.medicinex.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.database.Excipiente

import es.unex.giiis.medicinex.databinding.ItemExcipienteBinding

class ExcipienteViewHolder(view : View) : RecyclerView.ViewHolder(view)
{
    val binding = ItemExcipienteBinding.bind(view)

    fun render(excipiente : Excipiente)
    {
        binding.excipiente.text = excipiente.toString()
    }
}