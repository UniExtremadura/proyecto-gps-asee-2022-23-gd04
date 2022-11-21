package es.unex.giiis.medicinex.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.database.PrincipioActivo
import es.unex.giiis.medicinex.databinding.ItemPactivoBinding

class PActivoViewHolder(view : View) : RecyclerView.ViewHolder(view)
{
    val binding = ItemPactivoBinding.bind(view)

    fun render(pActivo : PrincipioActivo)
    {
        binding.pactivo.text = pActivo.toString()
    }
}