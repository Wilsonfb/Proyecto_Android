package com.example.nixson.ui
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.R
import com.example.nixson.controller.VentaController
import com.example.nixson.modulos.Venta
import android.widget.TextView

class VentaActivity : AppCompatActivity() {
    private val ventaController = VentaController()
    private var idContador: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venta)
    }

    fun crearVenta(v: View) {
        val nombreProducto = findViewById<EditText>(R.id.nombreProducto)
        val cantidad = findViewById<EditText>(R.id.cantidad)

        if (nombreProducto.text.isEmpty() || cantidad.text.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        idContador++
        val venta = Venta(id = idContador, producto = nombreProducto.text.toString(), cantidad = cantidad.text.toString().toInt())

        ventaController.agregarVenta(venta)

        Toast.makeText(this, "Venta creada con éxito", Toast.LENGTH_SHORT).show()

        nombreProducto.text.clear()
        cantidad.text.clear()
    }

    fun verVentas(v: View) {
        val listaVentasTextView = findViewById<TextView>(R.id.lista_ventas)

        val ventas = ventaController.obtenerVentas()

        if (ventas.isNotEmpty()) {
            val listaDetalles = ventas.joinToString(separator = "\n") {
                "ID: ${it.id}, Producto: ${it.producto}, Cantidad: ${it.cantidad}"
            }
            listaVentasTextView.text = "Ventas Realizadas:\n$listaDetalles"
        } else {
            listaVentasTextView.text = "No hay ventas registradas."
        }
    }

    fun irAMain(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}