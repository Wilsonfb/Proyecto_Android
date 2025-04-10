package com.example.nixson.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nixson.R
import com.example.nixson.controller.VentaController
import android.widget.Toast
import android.view.View

class HistorialVentasActivity : AppCompatActivity() {
    private val ventaController = VentaController()
    private lateinit var recyclerViewVentas: RecyclerView
    private lateinit var adapter: VentaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_ventas)

        recyclerViewVentas = findViewById(R.id.recyclerViewVentas)
        recyclerViewVentas.layoutManager = LinearLayoutManager(this)
        adapter = VentaAdapter(emptyList())
        recyclerViewVentas.adapter = adapter

        cargarVentas()
    }

    private fun cargarVentas() {
        val ventas = ventaController.obtenerVentas()

        if (ventas.isNotEmpty()) {
            adapter.updateVentas(ventas)
        } else {
            Toast.makeText(this, "No hay ventas registradas.", Toast.LENGTH_SHORT).show()
        }
    }

    fun verVentas(v: View) {
        cargarVentas()
    }

    fun irAMain(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}