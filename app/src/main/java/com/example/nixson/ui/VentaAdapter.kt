package com.example.nixson.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nixson.modulos.Venta
import com.example.nixson.R

class VentaAdapter(private var ventas: List<Venta>) : RecyclerView.Adapter<VentaAdapter.VentaViewHolder>() {

    class VentaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productoTextView: TextView = itemView.findViewById(R.id.productoTextView)
        val cantidadTextView: TextView = itemView.findViewById(R.id.cantidadTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_venta, parent, false)
        return VentaViewHolder(view)
    }

    override fun onBindViewHolder(holder: VentaViewHolder, position: Int) {
        val venta = ventas[position]
        holder.productoTextView.text = "Producto: ${venta.producto}"
        holder.cantidadTextView.text = "Cantidad: ${venta.cantidad}"
    }

    override fun getItemCount(): Int {
        return ventas.size
    }

    fun updateVentas(nuevasVentas: List<Venta>) {
        ventas = nuevasVentas
        notifyDataSetChanged()
    }
}