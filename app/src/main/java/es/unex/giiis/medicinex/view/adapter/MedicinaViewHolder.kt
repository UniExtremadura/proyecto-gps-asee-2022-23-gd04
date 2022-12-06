package es.unex.giiis.medicinex.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import es.unex.giiis.medicinex.R
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.databinding.ItemMedicinaBinding

class MedicinaViewHolder(view : View) : ViewHolder(view)
{
    val binding = ItemMedicinaBinding.bind(view)

    fun render(medicinaEntity : MedicinaEntity, onClickListener:(MedicinaEntity) -> Unit)
    {// Se mostrará el nombre del medicamento y los datos rápidos en función de sus atributos.
        binding.txtMedicinaName.text = medicinaEntity.nombre
        if(medicinaEntity.conduc == false)
        {
            binding.ivConduc.setImageResource(R.drawable.save_driving)
        }
        else
        {
            binding.ivConduc.setImageResource(R.drawable.unsafe_driving)
        }

        if(medicinaEntity.comerc == true)
        {
            binding.ivBuy.setImageResource(R.drawable.merchantable)
        }
        else
        {
            binding.ivBuy.setImageResource(R.drawable.not_merchantable)
        }

        if(medicinaEntity.receta == true)
        {
            binding.ivReceipt.setImageResource(R.drawable.prescription)
        }
        else
        {
            binding.ivReceipt.setImageResource(R.drawable.without_prescription)
        }

        itemView.setOnClickListener{
            onClickListener(medicinaEntity) // Listener accionado tras la pulsación del elemento medicina en la IU.
        }
    }
}