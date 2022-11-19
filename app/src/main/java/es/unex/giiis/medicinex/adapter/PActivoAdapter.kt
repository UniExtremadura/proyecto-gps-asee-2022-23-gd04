package es.unex.giiis.medicinex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.database.PrincipioActivo

class PActivoAdapter (private val pActivos : List<PrincipioActivo>) : RecyclerView.Adapter<PActivoViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PActivoViewHolder
    {
        /* Este método se llamará tantas veces como elementos PrincipioActivo existan en el medicamento en cuestión, y lo que hará
         será coger el nombre y datos del principio activo y pintarlos en la IU. */
        val layoutInflater = LayoutInflater.from(parent.context)
        return PActivoViewHolder(layoutInflater.inflate(R.layout.item_pactivo, parent, false))
    }

    override fun onBindViewHolder(holder: PActivoViewHolder, position: Int)
    {
        val item = pActivos[position]
        holder.render(item)
    }

    override fun getItemCount() : Int = pActivos.size
}