package es.unex.giiis.medicinex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.R

class SectionAdapter (private val sections : List<String>, private val onClickListener:(String) -> Unit) : RecyclerView.Adapter<SectionViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder
    {
        /* Este método se llamará tantas veces como secciones existan en sections, y lo que hará
         será coger el nombre de la sección y pintarlo en la IU. */
        val layoutInflater = LayoutInflater.from(parent.context)
        return SectionViewHolder(layoutInflater.inflate(R.layout.item_catalogue, parent, false))
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int)
    {
        val item = sections[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount() : Int = sections.size
}