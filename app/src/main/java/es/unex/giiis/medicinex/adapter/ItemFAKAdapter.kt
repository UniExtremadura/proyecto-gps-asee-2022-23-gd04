package es.unex.giiis.medicinex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.R

class ItemFAKAdapter(private val fak_items : List<String>, private val onClickListener:(String) -> Unit, private val onClickDelete:(Int) -> Unit) : RecyclerView.Adapter<ItemFAKViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFAKViewHolder
    {
        /* Este método se llamará tantas veces como elementos Medicina existan en el botiquín (first aid kit), y lo que hará
         será coger los atributos de la medicina en cuestión y pintarlos en la IU. */
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemFAKViewHolder(layoutInflater.inflate(R.layout.item_first_aid_kit, parent, false))
    }

    override fun onBindViewHolder(holder: ItemFAKViewHolder, position: Int)
    {
        val item = fak_items[position]
        holder.render(item, onClickListener, onClickDelete) // Renderización de la medicina del botiquín.
    }

    override fun getItemCount() : Int = fak_items.size
}