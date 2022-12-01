package es.unex.giiis.medicinex.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity

class MedicinaAdapter(private var medicinaEntities : List<MedicinaEntity>, private val onClickListener:(MedicinaEntity) -> Unit) : RecyclerView.Adapter<MedicinaViewHolder>()
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
        val item = medicinaEntities[position]
        holder.render(item, onClickListener) // Renderización de la medicina en la IU.
    }

    override fun getItemCount() : Int = medicinaEntities.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateMedicines(medicinaEntities : List<MedicinaEntity>)
    {// Actualización en el adaptador (empleado para los filtrados del recycler view del menú principal - búsqueda -).
        this.medicinaEntities = medicinaEntities
        notifyDataSetChanged()
    }
}