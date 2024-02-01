package com.example.myapplication

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.myapplication.database.Servicio // Asumo que tienes un modelo llamado Servicio

class AdaptadorServicios : ListAdapter<Servicio, AdaptadorServicios.ViewHolder>(ServiciosDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNombre: TextView = itemView.findViewById(R.id.textNombre)
        val imageServicio: ImageView = itemView.findViewById(R.id.imageServicio) // Cambié el nombre a imageServicio para reflejar que ahora es un servicio
        val textPrecio: TextView = itemView.findViewById(R.id.textPrecio)
        val textDescripcion: TextView = itemView.findViewById(R.id.textDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_servicio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val servicio = getItem(position)
        holder.textNombre.text = servicio.nombre
        holder.textPrecio.text = "Precio: ${servicio.precio}"
        holder.textDescripcion.text = servicio.descripcion

        // Cargar imagen en ImageView
        if (servicio.imagen != null && servicio.imagen.isNotEmpty()) {
            val bitmap = BitmapFactory.decodeByteArray(servicio.imagen, 0, servicio.imagen.size)
            holder.imageServicio.setImageBitmap(bitmap)
        } else {
            // Si no hay imagen, puedes establecer una imagen predeterminada o dejarlo en blanco
            holder.imageServicio.setImageResource(R.drawable.ic_imagen_predeterminada_servicio) // Cambia por la imagen predeterminada de servicio
        }
    }

    fun actualizarServicios(nuevaLista: List<Servicio>) {
        submitList(nuevaLista)
    }

    private class ServiciosDiffCallback : DiffUtil.ItemCallback<Servicio>() {
        override fun areItemsTheSame(oldItem: Servicio, newItem: Servicio): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Servicio, newItem: Servicio): Boolean {
            return oldItem == newItem
        }
    }
}
