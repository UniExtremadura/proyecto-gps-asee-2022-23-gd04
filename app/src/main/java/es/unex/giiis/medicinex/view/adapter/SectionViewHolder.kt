package es.unex.giiis.medicinex.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.databinding.ItemCatalogueBinding

class SectionViewHolder(view : View) : RecyclerView.ViewHolder(view)
{
    val binding = ItemCatalogueBinding.bind(view)

    fun render(section : String, onClickListener:(String) -> Unit )
    {
        binding.section.text = section

        itemView.setOnClickListener{
            onClickListener(section) // Listener accionado tras la selección de una sección.
        }
    }
}