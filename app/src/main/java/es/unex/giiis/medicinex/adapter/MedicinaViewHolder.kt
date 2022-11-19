package es.unex.giiis.medicinex.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.database.Medicina
import es.unex.giiis.medicinex.databinding.ItemMedicinaBinding

class MedicinaViewHolder(view : View) : ViewHolder(view)
{
    val binding = ItemMedicinaBinding.bind(view)

    fun render(medicina : Medicina, onClickListener:(Medicina) -> Unit)
    {// Se mostrará el nombre del medicamento y los datos rápidos en función de sus atributos.
        binding.txtMedicinaName.text = medicina.nombre
        if(medicina.conduc == true)
        {
            binding.ivConduc.setImageResource(R.drawable.save_driving)
        }
        else
        {
            binding.ivConduc.setImageResource(R.drawable.unsafe_driving)
        }

        if(medicina.comerc == true)
        {
            binding.ivBuy.setImageResource(R.drawable.merchantable)
        }
        else
        {
            binding.ivBuy.setImageResource(R.drawable.not_merchantable)
        }

        if(medicina.receta == true)
        {
            binding.ivReceipt.setImageResource(R.drawable.prescription)
        }
        else
        {
            binding.ivReceipt.setImageResource(R.drawable.without_prescription)
        }

        itemView.setOnClickListener{
            onClickListener(medicina) // Listener accionado tras la pulsación del elemento medicina en la IU.
        }
    }
}