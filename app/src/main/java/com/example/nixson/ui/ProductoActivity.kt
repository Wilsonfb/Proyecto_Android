package com.example.nixson.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.controller.ProductoController
import com.example.nixson.modulos.Producto
import com.example.nixson.R

class ProductoActivity : AppCompatActivity() {
    private val productoController = ProductoController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)
    }

    fun agregarProducto(v: View) {
        val nombre = findViewById<EditText>(R.id.nombre_producto)
        val cantidad = findViewById<EditText>(R.id.cantidad_producto)
        val fechaEntrada = findViewById<EditText>(R.id.fecha_entrada)
        val fechaVencimiento = findViewById<EditText>(R.id.fecha_vencimiento)
        val proveedor = findViewById<EditText>(R.id.proveedor_producto)
        val categoria = findViewById<EditText>(R.id.categoria_producto)
        val resultado = findViewById<TextView>(R.id.resultado_producto)

        if (!nombre.text.isNullOrEmpty() && !cantidad.text.isNullOrEmpty() && !proveedor.text.isNullOrEmpty()) {
            val nuevoProducto = Producto(
                id = productoController.obtenerProductos().size + 1,
                nombre = nombre.text.toString(),
                cantidad = cantidad.text.toString().toInt(),
                fechaEntrada = fechaEntrada.text.toString(),
                fechaVencimiento = fechaVencimiento.text.toString(),
                proveedor = proveedor.text.toString(),
                categoria = categoria.text.toString()
            )

            productoController.agregarProducto(
                nombre = nuevoProducto.nombre,
                cantidad = nuevoProducto.cantidad,
                fechaEntrada = nuevoProducto.fechaEntrada,
                fechaVencimiento = nuevoProducto.fechaVencimiento,
                proveedor = nuevoProducto.proveedor,
                categoria = nuevoProducto.categoria
            )

            resultado.text = "Producto agregado: ${nuevoProducto.nombre}"

            nombre.text.clear()
            cantidad.text.clear()
            fechaEntrada.text.clear()
            fechaVencimiento.text.clear()
            proveedor.text.clear()
            categoria.text.clear()
        } else {
            resultado.text = "Por favor, completa todos los campos requeridos."
        }
    }

    fun verProductos(v: View) {
        val listaProductosTextView = findViewById<TextView>(R.id.lista_productos)

        val productos = productoController.obtenerProductos()

        if (productos.isNotEmpty()) {
            val listaDetalles = productos.joinToString(separator = "\n") {
                "ID: ${it.id}, Nombre: ${it.nombre}, Cantidad: ${it.cantidad}, Fecha Vence: ${it.fechaVencimiento}"
            }
            listaProductosTextView.text = "Productos:\n$listaDetalles"
        } else {
            listaProductosTextView.text = "No hay productos registrados."
        }
    }

    fun eliminarProducto(v: View) {
        val idInput = findViewById<EditText>(R.id.id_producto_eliminar)
        val resultado = findViewById<TextView>(R.id.resultado_producto)

        if (!idInput.text.isNullOrEmpty()) {
            val id = idInput.text.toString().toInt()
            if (productoController.eliminarProducto(id)) {
                resultado.text = "Producto con ID $id eliminado."
            } else {
                resultado.text = "Producto con ID $id no encontrado."
            }
            idInput.text.clear()
        } else {
            resultado.text = "Por favor, ingresa un ID de producto."
        }
    }

    fun actualizarProducto(v: View) {
        val idInput = findViewById<EditText>(R.id.id_producto_actualizar)
        val nombre = findViewById<EditText>(R.id.nombre_producto)
        val cantidad = findViewById<EditText>(R.id.cantidad_producto)
        val fechaEntrada = findViewById<EditText>(R.id.fecha_entrada)
        val fechaVencimiento = findViewById<EditText>(R.id.fecha_vencimiento)
        val proveedor = findViewById<EditText>(R.id.proveedor_producto)
        val categoria = findViewById<EditText>(R.id.categoria_producto)
        val resultado = findViewById<TextView>(R.id.resultado_producto)

        if (!idInput.text.isNullOrEmpty() && !nombre.text.isNullOrEmpty() && !cantidad.text.isNullOrEmpty() && !proveedor.text.isNullOrEmpty()) {
            val id = idInput.text.toString().toInt()
            val actualizado = productoController.actualizarProducto(
                id = id,
                nombre = nombre.text.toString(),
                cantidad = cantidad.text.toString().toInt(),
                fechaEntrada = fechaEntrada.text.toString(),
                fechaVencimiento = fechaVencimiento.text.toString(),
                proveedor = proveedor.text.toString(),
                categoria = categoria.text.toString()
            )

            if (actualizado) {
                resultado.text = "Producto con ID $id actualizado."
            } else {
                resultado.text = "Producto con ID $id no encontrado."
            }

            idInput.text.clear()
            nombre.text.clear()
            cantidad.text.clear()
            fechaEntrada.text.clear()
            fechaVencimiento.text.clear()
            proveedor.text.clear()
            categoria.text.clear()
        } else {
            resultado.text = "Por favor, completa todos los campos requeridos."
        }
    }
}