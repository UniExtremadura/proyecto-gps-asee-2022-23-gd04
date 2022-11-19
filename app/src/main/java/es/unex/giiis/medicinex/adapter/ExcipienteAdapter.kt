package es.unex.giiis.medicinex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.database.Excipiente

class ExcipienteAdapter (private val excipientes : List<Excipiente>) : RecyclerView.Adapter<ExcipienteViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExcipienteViewHolder
    {
        /* Este método se llamará tantas veces como elementos Excipiente existan en el medicamento que sea, y lo que hará
         será coger el nombre del excipiente y pintarlo en la IU. */
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExcipienteViewHolder(layoutInflater.inflate(R.layout.item_excipiente, parent, false))
    }

    override fun onBindViewHolder(holder: ExcipienteViewHolder, position: Int)
    {
        val item = excipientes[position]
        holder.render(item) // Renderización del Excipiente en la IU.
    }

    override fun getItemCount() : Int = excipientes.size
}