package com.example.nixson.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.controller.CategoriaController
import com.example.nixson.R

class CategoriaActivity : AppCompatActivity() {
    private val categoriaController = CategoriaController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)
    }

    fun agregarCategoria(v: View) {
        val nombre = findViewById<EditText>(R.id.nombre_categoria)

        if (!nombre.text.isNullOrEmpty()) {
            categoriaController.agregarCategoria(nombre.text.toString())

            val resultado = findViewById<TextView>(R.id.resultado_categoria)
            resultado.text = "Categoría agregada: ${nombre.text}"
            nombre.text.clear()
        } else {
            val resultado = findViewById<TextView>(R.id.resultado_categoria)
            resultado.text = "Por favor, ingresa un nombre para la categoría."
        }
    }

    fun verCategorias(v: View) {
        val listaCategoriasTextView = findViewById<TextView>(R.id.lista_categorias)

        val categorias = categoriaController.obtenerCategorias()

        if (categorias.isNotEmpty()) {
            val listaDetalles = categorias.joinToString(separator = "\n") { it.nombre }
            listaCategoriasTextView.text = "Categorías:\n$listaDetalles"
        } else {
            listaCategoriasTextView.text = "No hay categorías registradas."
        }
    }

    fun irAMain(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}