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
import com.example.myapplication.database.Producto

class AdaptadorProductos : ListAdapter<Producto, AdaptadorProductos.ViewHolder>(ProductosDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNombre: TextView = itemView.findViewById(R.id.textNombre)
        val imageProducto: ImageView = itemView.findViewById(R.id.imageProducto)
        val textPrecio: TextView = itemView.findViewById(R.id.textPrecio)
        val textDescripcion: TextView = itemView.findViewById(R.id.textDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = getItem(position)
        holder.textNombre.text = producto.nombre
        holder.textPrecio.text = "Precio: ${producto.precio}"
        holder.textDescripcion.text = producto.descripcion

        // Cargar imagen en ImageView
        if (producto.imagen != null && producto.imagen.isNotEmpty()) {
            val bitmap = BitmapFactory.decodeByteArray(producto.imagen, 0, producto.imagen.size)
            holder.imageProducto.setImageBitmap(bitmap)
        } else {
            // Si no hay imagen, puedes establecer una imagen predeterminada o dejarlo en blanco
            holder.imageProducto.setImageResource(R.drawable.ic_imagen_predeterminada_producto) // Cambia por la imagen predeterminada
        }
    }

    fun actualizarProductos(nuevaLista: List<Producto>) {
        submitList(nuevaLista)
    }

    private class ProductosDiffCallback : DiffUtil.ItemCallback<Producto>() {
        override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem == newItem
        }
    }
}
