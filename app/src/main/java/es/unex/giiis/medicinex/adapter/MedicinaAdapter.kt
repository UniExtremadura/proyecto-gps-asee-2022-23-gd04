package es.unex.giiis.medicinex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.database.Medicina

class MedicinaAdapter(private var medicinas : List<Medicina>, private val onClickListener:(Medicina) -> Unit) : RecyclerView.Adapter<MedicinaViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicinaViewHolder
    {
        /* Este método se llamará tantas veces como elementos Medicina existan en medicinas, y lo que hará
         será coger los atributos de la medicina en cuestión y pintarlos en la IU. */
        val layoutInflater = LayoutInflater.from(parent.context)
        return MedicinaViewHolder(layoutInflater.inflate(R.layout.item_medicina, parent, false))
    }

    override fun onBindViewHolder(holder: MedicinaViewHolder, position: Int)
    {
        val item = medicinas[position]
        holder.render(item, onClickListener) // Renderización de la medicina en la IU.
    }

    override fun getItemCount() : Int = medicinas.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateMedicines(medicinas : List<Medicina>)
    {// Actualización en el adaptador (empleado para los filtrados del recycler view del menú principal - búsqueda -).
        this.medicinas = medicinas
        notifyDataSetChanged()
    }
}