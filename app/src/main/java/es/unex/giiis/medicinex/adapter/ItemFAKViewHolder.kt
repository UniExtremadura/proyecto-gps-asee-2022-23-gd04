package es.unex.giiis.medicinex.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.databinding.ItemFirstAidKitBinding

class ItemFAKViewHolder(view : View) : RecyclerView.ViewHolder(view)
{
    val binding = ItemFirstAidKitBinding.bind(view)

    fun render(medicamento : String, onClickListener:(String) -> Unit, onClickDelete:(Int) -> Unit)
    {
        binding.medicamento.text = medicamento

        itemView.setOnClickListener{
            onClickListener(medicamento) // Acción cuando se pulse sobre la vista del medicamento en el botiquín.
        }

        binding.trash.setOnClickListener {

            if(MedicinexApp.isThereInternet)
            {
                binding.trash.isEnabled = false
                onClickDelete(adapterPosition) // Acción cuando se pulse sobre la imagen de la papelera.
            }
        }
    }
}