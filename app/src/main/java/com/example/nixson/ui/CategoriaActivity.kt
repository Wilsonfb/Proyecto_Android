package com.example.nixson.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.controller.CategoriaController
import com.example.nixson.R
import com.example.nixson.api.ApiClient
import com.example.nixson.modulos.Categoria
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaActivity : AppCompatActivity() {
    private val categoriaController = CategoriaController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)

        obtenerCategorias()
    }

    private fun obtenerCategorias() {
        val call = ApiClient.kotlinApiService.obtenerCategorias()
        call.enqueue(object : Callback<List<Categoria>> {
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                if (response.isSuccessful) {
                    val categorias = response.body()
                    mostrarCategorias(categorias ?: emptyList())
                } else {
                    Log.e("Error", "Error en la respuesta: ${response.errorBody()?.string()}")
                    val listaCategoriasTextView = findViewById<TextView>(R.id.lista_categorias)
                    listaCategoriasTextView.text = "Error al obtener categorías."
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                Log.e("Error", t.message ?: "Error desconocido")
                val listaCategoriasTextView = findViewById<TextView>(R.id.lista_categorias)
                listaCategoriasTextView.text = "Error al obtener categorías."
            }
        })
    }

    fun agregarCategoria(v: View) {
        val nombre = findViewById<EditText>(R.id.nombre_categoria)

        if (!nombre.text.isNullOrEmpty()) {
            val nuevaCategoria = Categoria(id = 0, nombre = nombre.text.toString())

            val call = ApiClient.kotlinApiService.agregarCategoria(nuevaCategoria)

            call.enqueue(object : Callback<Categoria> {
                override fun onResponse(call: Call<Categoria>, response: Response<Categoria>) {
                    if (response.isSuccessful) {
                        val categoriaCreada = response.body()
                        val resultado = findViewById<TextView>(R.id.resultado_categoria)
                        resultado.text = "Categoría agregada: ${categoriaCreada?.nombre}"
                        nombre.text.clear()
                        obtenerCategorias()
                    } else {
                        Log.e("Error", "Error en la respuesta: ${response.errorBody()?.string()}")
                        val resultado = findViewById<TextView>(R.id.resultado_categoria)
                        resultado.text = "Error al agregar categoría."
                    }
                }

                override fun onFailure(call: Call<Categoria>, t: Throwable) {
                    Log.e("Error", t.message ?: "Error desconocido")
                    val resultado = findViewById<TextView>(R.id.resultado_categoria)
                    resultado.text = "Error al agregar categoría."
                }
            })
        } else {
            val resultado = findViewById<TextView>(R.id.resultado_categoria)
            resultado.text = "Por favor, ingresa un nombre para la categoría."
        }
    }

    private fun mostrarCategorias(categorias: List<Categoria>) {
        val listaCategoriasTextView = findViewById<TextView>(R.id.lista_categorias)

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